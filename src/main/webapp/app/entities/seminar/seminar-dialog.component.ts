import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Seminar } from './seminar.model';
import { SeminarPopupService } from './seminar-popup.service';
import { SeminarService } from './seminar.service';
import { Place, PlaceService } from '../place';
import { Organizer, OrganizerService } from '../organizer';
import { People, PeopleService } from '../people';
import { Tag, TagService } from '../tag';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-seminar-dialog',
    templateUrl: './seminar-dialog.component.html'
})
export class SeminarDialogComponent implements OnInit {

    seminar: Seminar;
    authorities: any[];
    isSaving: boolean;

    places: Place[];

    organizers: Organizer[];

    people: People[];

    tags: Tag[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private seminarService: SeminarService,
        private placeService: PlaceService,
        private organizerService: OrganizerService,
        private peopleService: PeopleService,
        private tagService: TagService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.placeService.query()
            .subscribe((res: ResponseWrapper) => { this.places = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.organizerService.query()
            .subscribe((res: ResponseWrapper) => { this.organizers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.peopleService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.tagService.query()
            .subscribe((res: ResponseWrapper) => { this.tags = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.seminar.id !== undefined) {
            this.subscribeToSaveResponse(
                this.seminarService.update(this.seminar), false);
        } else {
            this.subscribeToSaveResponse(
                this.seminarService.create(this.seminar), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Seminar>, isCreated: boolean) {
        result.subscribe((res: Seminar) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Seminar, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'misilApp.seminar.created'
            : 'misilApp.seminar.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'seminarListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackPlaceById(index: number, item: Place) {
        return item.id;
    }

    trackOrganizerById(index: number, item: Organizer) {
        return item.id;
    }

    trackPeopleById(index: number, item: People) {
        return item.id;
    }

    trackTagById(index: number, item: Tag) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-seminar-popup',
    template: ''
})
export class SeminarPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seminarPopupService: SeminarPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.seminarPopupService
                    .open(SeminarDialogComponent, params['id']);
            } else {
                this.modalRef = this.seminarPopupService
                    .open(SeminarDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

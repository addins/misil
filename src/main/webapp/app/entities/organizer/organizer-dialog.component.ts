import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Organizer } from './organizer.model';
import { OrganizerPopupService } from './organizer-popup.service';
import { OrganizerService } from './organizer.service';
import { People, PeopleService } from '../people';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-organizer-dialog',
    templateUrl: './organizer-dialog.component.html'
})
export class OrganizerDialogComponent implements OnInit {

    organizer: Organizer;
    authorities: any[];
    isSaving: boolean;

    people: People[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private organizerService: OrganizerService,
        private peopleService: PeopleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.peopleService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.organizer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.organizerService.update(this.organizer), false);
        } else {
            this.subscribeToSaveResponse(
                this.organizerService.create(this.organizer), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Organizer>, isCreated: boolean) {
        result.subscribe((res: Organizer) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Organizer, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'misilApp.organizer.created'
            : 'misilApp.organizer.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'organizerListModification', content: 'OK'});
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

    trackPeopleById(index: number, item: People) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-organizer-popup',
    template: ''
})
export class OrganizerPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private organizerPopupService: OrganizerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.organizerPopupService
                    .open(OrganizerDialogComponent, params['id']);
            } else {
                this.modalRef = this.organizerPopupService
                    .open(OrganizerDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

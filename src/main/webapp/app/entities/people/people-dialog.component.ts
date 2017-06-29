import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Seminar } from '../seminar/seminar.model';
import { SeminarService } from '../seminar/seminar.service';
import { People } from './people.model';
import { PeoplePopupService } from './people-popup.service';
import { PeopleService } from './people.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-people-dialog',
    templateUrl: './people-dialog.component.html'
})
export class PeopleDialogComponent implements OnInit {

    people: People;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    seminars: Seminar[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private seminarService: SeminarService,
        private peopleService: PeopleService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.seminarService.query()
            .subscribe((res: ResponseWrapper) => { this.seminars = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.people.id !== undefined) {
            this.subscribeToSaveResponse(
                this.peopleService.update(this.people), false);
        } else {
            this.subscribeToSaveResponse(
                this.peopleService.create(this.people), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<People>, isCreated: boolean) {
        result.subscribe((res: People) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: People, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'misilApp.people.created'
            : 'misilApp.people.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'peopleListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackSeminarById(index: number, item: Seminar) {
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
    selector: 'jhi-people-popup',
    template: ''
})
export class PeoplePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private peoplePopupService: PeoplePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.peoplePopupService
                    .open(PeopleDialogComponent, params['id']);
            } else {
                this.modalRef = this.peoplePopupService
                    .open(PeopleDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

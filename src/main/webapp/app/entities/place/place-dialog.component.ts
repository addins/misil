import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Place } from './place.model';
import { PlacePopupService } from './place-popup.service';
import { PlaceService } from './place.service';

@Component({
    selector: 'jhi-place-dialog',
    templateUrl: './place-dialog.component.html'
})
export class PlaceDialogComponent implements OnInit {

    place: Place;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private placeService: PlaceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.place.id !== undefined) {
            this.subscribeToSaveResponse(
                this.placeService.update(this.place), false);
        } else {
            this.subscribeToSaveResponse(
                this.placeService.create(this.place), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Place>, isCreated: boolean) {
        result.subscribe((res: Place) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Place, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'misilApp.place.created'
            : 'misilApp.place.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'placeListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-place-popup',
    template: ''
})
export class PlacePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private placePopupService: PlacePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.placePopupService
                    .open(PlaceDialogComponent, params['id']);
            } else {
                this.modalRef = this.placePopupService
                    .open(PlaceDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

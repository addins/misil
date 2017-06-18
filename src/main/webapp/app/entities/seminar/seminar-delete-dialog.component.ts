import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Seminar } from './seminar.model';
import { SeminarPopupService } from './seminar-popup.service';
import { SeminarService } from './seminar.service';

@Component({
    selector: 'jhi-seminar-delete-dialog',
    templateUrl: './seminar-delete-dialog.component.html'
})
export class SeminarDeleteDialogComponent {

    seminar: Seminar;

    constructor(
        private seminarService: SeminarService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.seminarService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'seminarListModification',
                content: 'Deleted an seminar'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('misilApp.seminar.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-seminar-delete-popup',
    template: ''
})
export class SeminarDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seminarPopupService: SeminarPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.seminarPopupService
                .open(SeminarDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Organizer } from './organizer.model';
import { OrganizerPopupService } from './organizer-popup.service';
import { OrganizerService } from './organizer.service';

@Component({
    selector: 'jhi-organizer-delete-dialog',
    templateUrl: './organizer-delete-dialog.component.html'
})
export class OrganizerDeleteDialogComponent {

    organizer: Organizer;

    constructor(
        private organizerService: OrganizerService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.organizerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'organizerListModification',
                content: 'Deleted an organizer'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('misilApp.organizer.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-organizer-delete-popup',
    template: ''
})
export class OrganizerDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private organizerPopupService: OrganizerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.organizerPopupService
                .open(OrganizerDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Seminar } from './seminar.model';
import { SeminarService } from './seminar.service';

@Injectable()
export class SeminarPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private seminarService: SeminarService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.seminarService.find(id).subscribe((seminar) => {
                seminar.startTime = this.datePipe
                    .transform(seminar.startTime, 'yyyy-MM-ddThh:mm');
                seminar.endTime = this.datePipe
                    .transform(seminar.endTime, 'yyyy-MM-ddThh:mm');
                this.seminarModalRef(component, seminar);
            });
        } else {
            return this.seminarModalRef(component, new Seminar());
        }
    }

    seminarModalRef(component: Component, seminar: Seminar): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.seminar = seminar;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}

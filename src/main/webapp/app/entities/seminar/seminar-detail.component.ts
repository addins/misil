import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Seminar } from './seminar.model';
import { SeminarService } from './seminar.service';

@Component({
    selector: 'jhi-seminar-detail',
    templateUrl: './seminar-detail.component.html'
})
export class SeminarDetailComponent implements OnInit, OnDestroy {

    seminar: Seminar;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private seminarService: SeminarService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSeminars();
    }

    load(id) {
        this.seminarService.find(id).subscribe((seminar) => {
            this.seminar = seminar;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSeminars() {
        this.eventSubscriber = this.eventManager.subscribe(
            'seminarListModification',
            (response) => this.load(this.seminar.id)
        );
    }
}

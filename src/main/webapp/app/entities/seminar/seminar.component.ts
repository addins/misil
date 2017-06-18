import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Seminar } from './seminar.model';
import { SeminarService } from './seminar.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-seminar',
    templateUrl: './seminar.component.html'
})
export class SeminarComponent implements OnInit, OnDestroy {
seminars: Seminar[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private seminarService: SeminarService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.seminarService.query().subscribe(
            (res: ResponseWrapper) => {
                this.seminars = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSeminars();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Seminar) {
        return item.id;
    }
    registerChangeInSeminars() {
        this.eventSubscriber = this.eventManager.subscribe('seminarListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

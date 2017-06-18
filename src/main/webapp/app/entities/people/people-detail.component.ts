import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { People } from './people.model';
import { PeopleService } from './people.service';

@Component({
    selector: 'jhi-people-detail',
    templateUrl: './people-detail.component.html'
})
export class PeopleDetailComponent implements OnInit, OnDestroy {

    people: People;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private peopleService: PeopleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPeople();
    }

    load(id) {
        this.peopleService.find(id).subscribe((people) => {
            this.people = people;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPeople() {
        this.eventSubscriber = this.eventManager.subscribe(
            'peopleListModification',
            (response) => this.load(this.people.id)
        );
    }
}

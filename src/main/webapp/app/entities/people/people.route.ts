import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PeopleComponent } from './people.component';
import { PeopleDetailComponent } from './people-detail.component';
import { PeoplePopupComponent } from './people-dialog.component';
import { PeopleDeletePopupComponent } from './people-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class PeopleResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const peopleRoute: Routes = [
    {
        path: 'people',
        component: PeopleComponent,
        resolve: {
            'pagingParams': PeopleResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.people.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'people/:id',
        component: PeopleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.people.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const peoplePopupRoute: Routes = [
    {
        path: 'people-new',
        component: PeoplePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.people.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'people/:id/edit',
        component: PeoplePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.people.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'people/:id/delete',
        component: PeopleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.people.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

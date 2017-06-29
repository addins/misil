import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OrganizerComponent } from './organizer.component';
import { OrganizerDetailComponent } from './organizer-detail.component';
import { OrganizerPopupComponent } from './organizer-dialog.component';
import { OrganizerDeletePopupComponent } from './organizer-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class OrganizerResolvePagingParams implements Resolve<any> {

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

export const organizerRoute: Routes = [
    {
        path: 'organizer',
        component: OrganizerComponent,
        resolve: {
            'pagingParams': OrganizerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.organizer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'organizer/:id',
        component: OrganizerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.organizer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const organizerPopupRoute: Routes = [
    {
        path: 'organizer-new',
        component: OrganizerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.organizer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'organizer/:id/edit',
        component: OrganizerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.organizer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'organizer/:id/delete',
        component: OrganizerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.organizer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

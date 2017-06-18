import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PeopleComponent } from './people.component';
import { PeopleDetailComponent } from './people-detail.component';
import { PeoplePopupComponent } from './people-dialog.component';
import { PeopleDeletePopupComponent } from './people-delete-dialog.component';

import { Principal } from '../../shared';

export const peopleRoute: Routes = [
    {
        path: 'people',
        component: PeopleComponent,
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

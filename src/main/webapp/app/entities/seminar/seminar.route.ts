import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SeminarComponent } from './seminar.component';
import { SeminarDetailComponent } from './seminar-detail.component';
import { SeminarPopupComponent } from './seminar-dialog.component';
import { SeminarDeletePopupComponent } from './seminar-delete-dialog.component';

import { Principal } from '../../shared';

export const seminarRoute: Routes = [
    {
        path: 'seminar',
        component: SeminarComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.seminar.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'seminar/:id',
        component: SeminarDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.seminar.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seminarPopupRoute: Routes = [
    {
        path: 'seminar-new',
        component: SeminarPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.seminar.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'seminar/:id/edit',
        component: SeminarPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.seminar.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'seminar/:id/delete',
        component: SeminarDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'misilApp.seminar.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

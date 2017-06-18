import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MisilSharedModule } from '../../shared';
import { MisilAdminModule } from '../../admin/admin.module';
import {
    PeopleService,
    PeoplePopupService,
    PeopleComponent,
    PeopleDetailComponent,
    PeopleDialogComponent,
    PeoplePopupComponent,
    PeopleDeletePopupComponent,
    PeopleDeleteDialogComponent,
    peopleRoute,
    peoplePopupRoute,
} from './';

const ENTITY_STATES = [
    ...peopleRoute,
    ...peoplePopupRoute,
];

@NgModule({
    imports: [
        MisilSharedModule,
        MisilAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PeopleComponent,
        PeopleDetailComponent,
        PeopleDialogComponent,
        PeopleDeleteDialogComponent,
        PeoplePopupComponent,
        PeopleDeletePopupComponent,
    ],
    entryComponents: [
        PeopleComponent,
        PeopleDialogComponent,
        PeoplePopupComponent,
        PeopleDeleteDialogComponent,
        PeopleDeletePopupComponent,
    ],
    providers: [
        PeopleService,
        PeoplePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MisilPeopleModule {}

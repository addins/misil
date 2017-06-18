import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MisilSharedModule } from '../../shared';
import {
    SeminarService,
    SeminarPopupService,
    SeminarComponent,
    SeminarDetailComponent,
    SeminarDialogComponent,
    SeminarPopupComponent,
    SeminarDeletePopupComponent,
    SeminarDeleteDialogComponent,
    seminarRoute,
    seminarPopupRoute,
} from './';

const ENTITY_STATES = [
    ...seminarRoute,
    ...seminarPopupRoute,
];

@NgModule({
    imports: [
        MisilSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SeminarComponent,
        SeminarDetailComponent,
        SeminarDialogComponent,
        SeminarDeleteDialogComponent,
        SeminarPopupComponent,
        SeminarDeletePopupComponent,
    ],
    entryComponents: [
        SeminarComponent,
        SeminarDialogComponent,
        SeminarPopupComponent,
        SeminarDeleteDialogComponent,
        SeminarDeletePopupComponent,
    ],
    providers: [
        SeminarService,
        SeminarPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MisilSeminarModule {}

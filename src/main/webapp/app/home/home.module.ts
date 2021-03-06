import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MisilSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import { SeminarListComponent } from '../entities/seminar/index';

@NgModule({
    imports: [
        MisilSharedModule,
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent,
        SeminarListComponent
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MisilHomeModule {}

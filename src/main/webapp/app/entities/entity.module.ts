import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MisilPeopleModule } from './people/people.module';
import { MisilPlaceModule } from './place/place.module';
import { MisilOrganizerModule } from './organizer/organizer.module';
import { MisilTagModule } from './tag/tag.module';
import { MisilSeminarModule } from './seminar/seminar.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MisilPeopleModule,
        MisilPlaceModule,
        MisilOrganizerModule,
        MisilTagModule,
        MisilSeminarModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MisilEntityModule {}

import { Place } from '../place';
import { Organizer } from '../organizer';
import { People } from '../people';
import { Tag } from '../tag';
export class Seminar {
    constructor(
        public id?: number,
        public title?: string,
        public startTime?: any,
        public endTime?: any,
        public canceled?: boolean,
        public published?: boolean,
        public place?: Place,
        public organizedBy?: Organizer,
        public presenter?: People,
        public attendees?: People,
        public specialGuests?: People,
        public tags?: Tag,
    ) {
        this.canceled = false;
        this.published = false;
    }
}

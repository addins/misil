import { People } from '../people';
import { Seminar } from '../seminar';
export class Organizer {
    constructor(
        public id?: number,
        public name?: string,
        public pic?: People,
        public seminarsOrganized?: Seminar,
    ) {
    }
}

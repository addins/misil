import { User } from '../../shared';
import { Seminar } from '../seminar';
export class People {
    constructor(
        public id?: number,
        public externalId?: string,
        public phone?: string,
        public user?: User,
        public seminarsPresented?: Seminar,
        public seminarsAttended?: Seminar,
        public specialGuestAt?: Seminar,
    ) {
    }
}

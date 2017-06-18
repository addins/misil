import { Seminar } from '../seminar';
export class Place {
    constructor(
        public id?: number,
        public name?: string,
        public address?: string,
        public seminars?: Seminar,
    ) {
    }
}

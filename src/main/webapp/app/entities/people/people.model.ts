export class People {
    constructor(
        public id?: number,
        public externalId?: string,
        public phone?: string,
        public userId?: number,
        public seminarsPresentedId?: number,
        public seminarsAttendedId?: number,
        public specialGuestAtId?: number,
    ) {
    }
}

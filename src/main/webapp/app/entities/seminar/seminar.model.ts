export class Seminar {
    constructor(
        public id?: number,
        public title?: string,
        public startTime?: any,
        public endTime?: any,
        public canceled?: boolean,
        public published?: boolean,
        public placeId?: number,
        public organizedById?: number,
        public presenterId?: number,
        public attendeesId?: number,
        public specialGuestsId?: number,
        public tagsId?: number,
        public lastModifiedDate?: any,
    ) {
        this.canceled = false;
        this.published = false;
    }
}

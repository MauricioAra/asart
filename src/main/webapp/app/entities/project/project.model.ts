import { BaseEntity } from './../../shared';

export class Project implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public totalHours?: string,
        public startDate?: string,
        public endDate?: string,
        public status?: string,
        public logWorks?: BaseEntity[],
        public collaboratorId?: number,
    ) {
    }
}

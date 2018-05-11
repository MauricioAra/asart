import { BaseEntity } from './../../shared';

export class LogWork implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public hour?: number,
        public minute?: number,
        public date?: string,
        public status?: string,
        public projectId?: number,
    ) {
    }
}

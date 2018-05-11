import { BaseEntity } from './../../shared';

export class LinkAuth implements BaseEntity {
    constructor(
        public id?: number,
        public idSession?: number,
        public idCollaborator?: number,
    ) {
    }
}

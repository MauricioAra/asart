import { BaseEntity } from './../../shared';

export class Collaborator implements BaseEntity {
    constructor(
        public id?: number,
        public identification?: string,
        public name?: string,
        public firstName?: string,
        public lastName?: string,
        public birthDate?: string,
        public gender?: string,
        public cellPhone?: string,
        public address?: string,
        public status?: string,
        public projects?: BaseEntity[],
    ) {
    }
}

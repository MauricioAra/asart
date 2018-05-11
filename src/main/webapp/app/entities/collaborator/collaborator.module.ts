import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AsartSharedModule } from '../../shared';
import {
    CollaboratorService,
    CollaboratorPopupService,
    CollaboratorComponent,
    CollaboratorDetailComponent,
    CollaboratorDialogComponent,
    CollaboratorPopupComponent,
    CollaboratorDeletePopupComponent,
    CollaboratorDeleteDialogComponent,
    collaboratorRoute,
    collaboratorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...collaboratorRoute,
    ...collaboratorPopupRoute,
];

@NgModule({
    imports: [
        AsartSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CollaboratorComponent,
        CollaboratorDetailComponent,
        CollaboratorDialogComponent,
        CollaboratorDeleteDialogComponent,
        CollaboratorPopupComponent,
        CollaboratorDeletePopupComponent,
    ],
    entryComponents: [
        CollaboratorComponent,
        CollaboratorDialogComponent,
        CollaboratorPopupComponent,
        CollaboratorDeleteDialogComponent,
        CollaboratorDeletePopupComponent,
    ],
    providers: [
        CollaboratorService,
        CollaboratorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AsartCollaboratorModule {}

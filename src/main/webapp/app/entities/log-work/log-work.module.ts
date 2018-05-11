import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AsartSharedModule } from '../../shared';
import {
    LogWorkService,
    LogWorkPopupService,
    LogWorkComponent,
    LogWorkDetailComponent,
    LogWorkDialogComponent,
    LogWorkPopupComponent,
    LogWorkDeletePopupComponent,
    LogWorkDeleteDialogComponent,
    logWorkRoute,
    logWorkPopupRoute,
} from './';

const ENTITY_STATES = [
    ...logWorkRoute,
    ...logWorkPopupRoute,
];

@NgModule({
    imports: [
        AsartSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LogWorkComponent,
        LogWorkDetailComponent,
        LogWorkDialogComponent,
        LogWorkDeleteDialogComponent,
        LogWorkPopupComponent,
        LogWorkDeletePopupComponent,
    ],
    entryComponents: [
        LogWorkComponent,
        LogWorkDialogComponent,
        LogWorkPopupComponent,
        LogWorkDeleteDialogComponent,
        LogWorkDeletePopupComponent,
    ],
    providers: [
        LogWorkService,
        LogWorkPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AsartLogWorkModule {}

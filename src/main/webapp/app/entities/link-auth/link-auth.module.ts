import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AsartSharedModule } from '../../shared';
import {
    LinkAuthService,
    LinkAuthPopupService,
    LinkAuthComponent,
    LinkAuthDetailComponent,
    LinkAuthDialogComponent,
    LinkAuthPopupComponent,
    LinkAuthDeletePopupComponent,
    LinkAuthDeleteDialogComponent,
    linkAuthRoute,
    linkAuthPopupRoute,
} from './';

const ENTITY_STATES = [
    ...linkAuthRoute,
    ...linkAuthPopupRoute,
];

@NgModule({
    imports: [
        AsartSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LinkAuthComponent,
        LinkAuthDetailComponent,
        LinkAuthDialogComponent,
        LinkAuthDeleteDialogComponent,
        LinkAuthPopupComponent,
        LinkAuthDeletePopupComponent,
    ],
    entryComponents: [
        LinkAuthComponent,
        LinkAuthDialogComponent,
        LinkAuthPopupComponent,
        LinkAuthDeleteDialogComponent,
        LinkAuthDeletePopupComponent,
    ],
    providers: [
        LinkAuthService,
        LinkAuthPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AsartLinkAuthModule {}

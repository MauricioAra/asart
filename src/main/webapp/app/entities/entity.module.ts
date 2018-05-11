import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AsartLinkAuthModule } from './link-auth/link-auth.module';
import { AsartCollaboratorModule } from './collaborator/collaborator.module';
import { AsartProjectModule } from './project/project.module';
import { AsartLogWorkModule } from './log-work/log-work.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AsartLinkAuthModule,
        AsartCollaboratorModule,
        AsartProjectModule,
        AsartLogWorkModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AsartEntityModule {}

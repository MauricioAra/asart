import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CollaboratorComponent } from './collaborator.component';
import { CollaboratorDetailComponent } from './collaborator-detail.component';
import { CollaboratorPopupComponent } from './collaborator-dialog.component';
import { CollaboratorDeletePopupComponent } from './collaborator-delete-dialog.component';

export const collaboratorRoute: Routes = [
    {
        path: 'collaborator',
        component: CollaboratorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Collaborators'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'collaborator/:id',
        component: CollaboratorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Collaborators'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const collaboratorPopupRoute: Routes = [
    {
        path: 'collaborator-new',
        component: CollaboratorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Collaborators'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collaborator/:id/edit',
        component: CollaboratorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Collaborators'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collaborator/:id/delete',
        component: CollaboratorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Collaborators'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

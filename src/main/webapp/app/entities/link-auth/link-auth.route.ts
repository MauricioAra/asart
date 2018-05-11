import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LinkAuthComponent } from './link-auth.component';
import { LinkAuthDetailComponent } from './link-auth-detail.component';
import { LinkAuthPopupComponent } from './link-auth-dialog.component';
import { LinkAuthDeletePopupComponent } from './link-auth-delete-dialog.component';

export const linkAuthRoute: Routes = [
    {
        path: 'link-auth',
        component: LinkAuthComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LinkAuths'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'link-auth/:id',
        component: LinkAuthDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LinkAuths'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const linkAuthPopupRoute: Routes = [
    {
        path: 'link-auth-new',
        component: LinkAuthPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LinkAuths'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'link-auth/:id/edit',
        component: LinkAuthPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LinkAuths'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'link-auth/:id/delete',
        component: LinkAuthDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LinkAuths'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

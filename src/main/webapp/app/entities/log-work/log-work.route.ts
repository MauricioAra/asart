import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LogWorkComponent } from './log-work.component';
import { LogWorkDetailComponent } from './log-work-detail.component';
import { LogWorkPopupComponent } from './log-work-dialog.component';
import { LogWorkDeletePopupComponent } from './log-work-delete-dialog.component';

export const logWorkRoute: Routes = [
    {
        path: 'log-work',
        component: LogWorkComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogWorks'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'log-work/:id',
        component: LogWorkDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogWorks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const logWorkPopupRoute: Routes = [
    {
        path: 'log-work-new',
        component: LogWorkPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogWorks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'log-work/:id/edit',
        component: LogWorkPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogWorks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'log-work/:id/delete',
        component: LogWorkDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogWorks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

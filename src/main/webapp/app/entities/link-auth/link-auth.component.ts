import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LinkAuth } from './link-auth.model';
import { LinkAuthService } from './link-auth.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-link-auth',
    templateUrl: './link-auth.component.html'
})
export class LinkAuthComponent implements OnInit, OnDestroy {
linkAuths: LinkAuth[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private linkAuthService: LinkAuthService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.linkAuthService.query().subscribe(
            (res: HttpResponse<LinkAuth[]>) => {
                this.linkAuths = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLinkAuths();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LinkAuth) {
        return item.id;
    }
    registerChangeInLinkAuths() {
        this.eventSubscriber = this.eventManager.subscribe('linkAuthListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LinkAuth } from './link-auth.model';
import { LinkAuthService } from './link-auth.service';

@Component({
    selector: 'jhi-link-auth-detail',
    templateUrl: './link-auth-detail.component.html'
})
export class LinkAuthDetailComponent implements OnInit, OnDestroy {

    linkAuth: LinkAuth;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private linkAuthService: LinkAuthService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLinkAuths();
    }

    load(id) {
        this.linkAuthService.find(id)
            .subscribe((linkAuthResponse: HttpResponse<LinkAuth>) => {
                this.linkAuth = linkAuthResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLinkAuths() {
        this.eventSubscriber = this.eventManager.subscribe(
            'linkAuthListModification',
            (response) => this.load(this.linkAuth.id)
        );
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LinkAuth } from './link-auth.model';
import { LinkAuthPopupService } from './link-auth-popup.service';
import { LinkAuthService } from './link-auth.service';

@Component({
    selector: 'jhi-link-auth-dialog',
    templateUrl: './link-auth-dialog.component.html'
})
export class LinkAuthDialogComponent implements OnInit {

    linkAuth: LinkAuth;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private linkAuthService: LinkAuthService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.linkAuth.id !== undefined) {
            this.subscribeToSaveResponse(
                this.linkAuthService.update(this.linkAuth));
        } else {
            this.subscribeToSaveResponse(
                this.linkAuthService.create(this.linkAuth));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LinkAuth>>) {
        result.subscribe((res: HttpResponse<LinkAuth>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LinkAuth) {
        this.eventManager.broadcast({ name: 'linkAuthListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-link-auth-popup',
    template: ''
})
export class LinkAuthPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private linkAuthPopupService: LinkAuthPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.linkAuthPopupService
                    .open(LinkAuthDialogComponent as Component, params['id']);
            } else {
                this.linkAuthPopupService
                    .open(LinkAuthDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

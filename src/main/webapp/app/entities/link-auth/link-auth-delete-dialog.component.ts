import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LinkAuth } from './link-auth.model';
import { LinkAuthPopupService } from './link-auth-popup.service';
import { LinkAuthService } from './link-auth.service';

@Component({
    selector: 'jhi-link-auth-delete-dialog',
    templateUrl: './link-auth-delete-dialog.component.html'
})
export class LinkAuthDeleteDialogComponent {

    linkAuth: LinkAuth;

    constructor(
        private linkAuthService: LinkAuthService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.linkAuthService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'linkAuthListModification',
                content: 'Deleted an linkAuth'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-link-auth-delete-popup',
    template: ''
})
export class LinkAuthDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private linkAuthPopupService: LinkAuthPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.linkAuthPopupService
                .open(LinkAuthDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Collaborator } from './collaborator.model';
import { CollaboratorPopupService } from './collaborator-popup.service';
import { CollaboratorService } from './collaborator.service';

@Component({
    selector: 'jhi-collaborator-dialog',
    templateUrl: './collaborator-dialog.component.html'
})
export class CollaboratorDialogComponent implements OnInit {

    collaborator: Collaborator;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private collaboratorService: CollaboratorService,
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
        if (this.collaborator.id !== undefined) {
            this.subscribeToSaveResponse(
                this.collaboratorService.update(this.collaborator));
        } else {
            this.subscribeToSaveResponse(
                this.collaboratorService.create(this.collaborator));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Collaborator>>) {
        result.subscribe((res: HttpResponse<Collaborator>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Collaborator) {
        this.eventManager.broadcast({ name: 'collaboratorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-collaborator-popup',
    template: ''
})
export class CollaboratorPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collaboratorPopupService: CollaboratorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.collaboratorPopupService
                    .open(CollaboratorDialogComponent as Component, params['id']);
            } else {
                this.collaboratorPopupService
                    .open(CollaboratorDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

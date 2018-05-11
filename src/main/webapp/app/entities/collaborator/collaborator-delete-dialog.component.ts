import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Collaborator } from './collaborator.model';
import { CollaboratorPopupService } from './collaborator-popup.service';
import { CollaboratorService } from './collaborator.service';

@Component({
    selector: 'jhi-collaborator-delete-dialog',
    templateUrl: './collaborator-delete-dialog.component.html'
})
export class CollaboratorDeleteDialogComponent {

    collaborator: Collaborator;

    constructor(
        private collaboratorService: CollaboratorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.collaboratorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'collaboratorListModification',
                content: 'Deleted an collaborator'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-collaborator-delete-popup',
    template: ''
})
export class CollaboratorDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collaboratorPopupService: CollaboratorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.collaboratorPopupService
                .open(CollaboratorDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

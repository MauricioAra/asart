import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Collaborator } from './collaborator.model';
import { CollaboratorService } from './collaborator.service';

@Component({
    selector: 'jhi-collaborator-detail',
    templateUrl: './collaborator-detail.component.html'
})
export class CollaboratorDetailComponent implements OnInit, OnDestroy {

    collaborator: Collaborator;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private collaboratorService: CollaboratorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCollaborators();
    }

    load(id) {
        this.collaboratorService.find(id)
            .subscribe((collaboratorResponse: HttpResponse<Collaborator>) => {
                this.collaborator = collaboratorResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCollaborators() {
        this.eventSubscriber = this.eventManager.subscribe(
            'collaboratorListModification',
            (response) => this.load(this.collaborator.id)
        );
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Collaborator } from './collaborator.model';
import { CollaboratorService } from './collaborator.service';
import { Principal } from '../../shared';

import { ITEMS_PER_PAGE, User, UserService } from '../../shared';

@Component({
    selector: 'jhi-collaborator',
    templateUrl: './collaborator.component.html'
})
export class CollaboratorComponent implements OnInit, OnDestroy {
collaborators: Collaborator[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private collaboratorService: CollaboratorService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private userService: UserService,
    ) {
    }

    loadAll() {
        this.collaboratorService.query().subscribe(
            (res: HttpResponse<Collaborator[]>) => {
                this.collaborators = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCollaborators();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Collaborator) {
        return item.id;
    }
    registerChangeInCollaborators() {
        this.eventSubscriber = this.eventManager.subscribe('collaboratorListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    setActive(collaborator, isActivated) {
        const user = {
            id:collaborator.idUser,
            activated:isActivated,
            login: collaborator.login
        }

        console.log(user);

        this.userService.update(user).subscribe(
            (response) => {
                if (response.status === 200) {
                    this.loadAll();
                }
            });
    }
}

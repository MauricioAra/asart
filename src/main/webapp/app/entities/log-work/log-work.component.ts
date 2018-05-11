import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LogWork } from './log-work.model';
import { LogWorkService } from './log-work.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-log-work',
    templateUrl: './log-work.component.html'
})
export class LogWorkComponent implements OnInit, OnDestroy {
logWorks: LogWork[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private logWorkService: LogWorkService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.logWorkService.query().subscribe(
            (res: HttpResponse<LogWork[]>) => {
                this.logWorks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLogWorks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LogWork) {
        return item.id;
    }
    registerChangeInLogWorks() {
        this.eventSubscriber = this.eventManager.subscribe('logWorkListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

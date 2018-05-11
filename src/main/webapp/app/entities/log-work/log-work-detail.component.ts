import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LogWork } from './log-work.model';
import { LogWorkService } from './log-work.service';

@Component({
    selector: 'jhi-log-work-detail',
    templateUrl: './log-work-detail.component.html'
})
export class LogWorkDetailComponent implements OnInit, OnDestroy {

    logWork: LogWork;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private logWorkService: LogWorkService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLogWorks();
    }

    load(id) {
        this.logWorkService.find(id)
            .subscribe((logWorkResponse: HttpResponse<LogWork>) => {
                this.logWork = logWorkResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLogWorks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'logWorkListModification',
            (response) => this.load(this.logWork.id)
        );
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LogWork } from './log-work.model';
import { LogWorkPopupService } from './log-work-popup.service';
import { LogWorkService } from './log-work.service';
import { Project, ProjectService } from '../project';

@Component({
    selector: 'jhi-log-work-dialog',
    templateUrl: './log-work-dialog.component.html'
})
export class LogWorkDialogComponent implements OnInit {

    logWork: LogWork;
    isSaving: boolean;

    projects: Project[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private logWorkService: LogWorkService,
        private projectService: ProjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.projectService.query()
            .subscribe((res: HttpResponse<Project[]>) => { this.projects = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.logWork.id !== undefined) {
            this.subscribeToSaveResponse(
                this.logWorkService.update(this.logWork));
        } else {
            this.subscribeToSaveResponse(
                this.logWorkService.create(this.logWork));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LogWork>>) {
        result.subscribe((res: HttpResponse<LogWork>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LogWork) {
        this.eventManager.broadcast({ name: 'logWorkListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProjectById(index: number, item: Project) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-log-work-popup',
    template: ''
})
export class LogWorkPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private logWorkPopupService: LogWorkPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.logWorkPopupService
                    .open(LogWorkDialogComponent as Component, params['id']);
            } else {
                this.logWorkPopupService
                    .open(LogWorkDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

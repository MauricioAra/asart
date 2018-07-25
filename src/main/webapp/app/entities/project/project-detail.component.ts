import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { HttpErrorResponse } from '@angular/common/http';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';


import { Project } from './project.model';
import { ProjectService } from './project.service';

import { LogWork } from '../log-work/log-work.model';
import { LogWorkService } from '../log-work/log-work.service';

@Component({
    selector: 'jhi-project-detail',
    templateUrl: './project-detail.component.html'
})
export class ProjectDetailComponent implements OnInit, OnDestroy {
    logWorks: LogWork[];
    project: Project;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    idProject:any;
    constructor(
        private eventManager: JhiEventManager,
        private projectService: ProjectService,
        private logWorkService: LogWorkService,
        private jhiAlertService: JhiAlertService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.idProject = params['id'];
            this.load();
        });
        this.registerChangeInProjects();
        this.eventSubscriber = this.eventManager.subscribe('logWorkListModification', (response) => this.load());
    }

    load() {
        this.projectService.find(this.idProject)
            .subscribe((projectResponse: HttpResponse<Project>) => {
                this.project = projectResponse.body;
            });

        this.logWorkService.getByProject(this.idProject).subscribe(
            (res: HttpResponse<LogWork[]>) => {
                this.logWorks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProjects() {
        this.eventSubscriber = this.eventManager.subscribe(
            'projectListModification',
            (response) => this.load()
        );
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

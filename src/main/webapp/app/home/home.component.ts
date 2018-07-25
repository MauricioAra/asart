import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LoginService } from '../shared/login/login.service';
import { Router, ActivatedRoute } from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { StateStorageService } from '../shared/auth/state-storage.service';
import { ProjectService } from '../entities/project/project.service';
import { Project } from '../entities/project/project.model';

import { Account, LoginModalService, Principal } from '../shared';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    username: string;
    credentials: any;
    loginEmail: string;
    public retries = 0;
    projects:Project[];

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private projectService: ProjectService,
        private stateStorageService: StateStorageService,
        public jhiAlertService: JhiAlertService,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
            this.getMyProjects(account.id);
        });
        this.registerAuthenticationSuccess();

    }

    getMyProjects(id:any):void{
        this.projects = [];
        this.projectService.findAllBycollaborator(id).subscribe(
            (res: HttpResponse<Project[]>) => {
                this.projects = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {

        this.loginService.login({
            username: this.username,
            password: this.password,
            rememberMe: this.rememberMe
        }).then(() => {
            this.credentials = {
                username: null,
                password: null,
                rememberMe: true
            };
            this.authenticationError = false;
            if (this.router.url === '/register' || (/^\/activate\//.test(this.router.url)) ||
                (/^\/reset\//.test(this.router.url))) {
                this.router.navigate(['']);
            }
            this.eventManager.broadcast({
                name: 'authenticationSuccess',
                content: 'Sending Authentication Success'
            });
            const redirect = this.stateStorageService.getUrl();
            if (redirect) {
                this.stateStorageService.storeUrl(null);
                this.router.navigate([redirect]);
            }
            this.ngOnInit();
        }).catch(() => {
            this.retries +=1;
            if(this.retries >= 2) {
                this.loginEmail = '';
            }
            this.authenticationError = true;
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

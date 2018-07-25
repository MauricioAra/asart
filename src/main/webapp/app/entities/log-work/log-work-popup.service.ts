import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { LogWork } from './log-work.model';
import { LogWorkService } from './log-work.service';

@Injectable()
export class LogWorkPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private logWorkService: LogWorkService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.logWorkService.find(id)
                    .subscribe((logWorkResponse: HttpResponse<LogWork>) => {
                        const logWork: LogWork = logWorkResponse.body;
                        this.ngbModalRef = this.logWorkModalRef(component, logWork);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.logWorkModalRef(component, new LogWork());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    openNew(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                const logWork: LogWork = new LogWork;
                logWork.projectId = id;
                this.ngbModalRef = this.logWorkModalRef(component, logWork);
                resolve(this.ngbModalRef);
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.logWorkModalRef(component, new LogWork());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    logWorkModalRef(component: Component, logWork: LogWork): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.logWork = logWork;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}

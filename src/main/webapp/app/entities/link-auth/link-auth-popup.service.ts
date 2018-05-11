import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { LinkAuth } from './link-auth.model';
import { LinkAuthService } from './link-auth.service';

@Injectable()
export class LinkAuthPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private linkAuthService: LinkAuthService

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
                this.linkAuthService.find(id)
                    .subscribe((linkAuthResponse: HttpResponse<LinkAuth>) => {
                        const linkAuth: LinkAuth = linkAuthResponse.body;
                        this.ngbModalRef = this.linkAuthModalRef(component, linkAuth);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.linkAuthModalRef(component, new LinkAuth());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    linkAuthModalRef(component: Component, linkAuth: LinkAuth): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.linkAuth = linkAuth;
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

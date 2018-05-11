import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LogWork } from './log-work.model';
import { LogWorkPopupService } from './log-work-popup.service';
import { LogWorkService } from './log-work.service';

@Component({
    selector: 'jhi-log-work-delete-dialog',
    templateUrl: './log-work-delete-dialog.component.html'
})
export class LogWorkDeleteDialogComponent {

    logWork: LogWork;

    constructor(
        private logWorkService: LogWorkService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.logWorkService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'logWorkListModification',
                content: 'Deleted an logWork'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-log-work-delete-popup',
    template: ''
})
export class LogWorkDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private logWorkPopupService: LogWorkPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.logWorkPopupService
                .open(LogWorkDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

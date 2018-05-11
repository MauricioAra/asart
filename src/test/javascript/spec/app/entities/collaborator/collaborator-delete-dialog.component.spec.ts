/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AsartTestModule } from '../../../test.module';
import { CollaboratorDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/collaborator/collaborator-delete-dialog.component';
import { CollaboratorService } from '../../../../../../main/webapp/app/entities/collaborator/collaborator.service';

describe('Component Tests', () => {

    describe('Collaborator Management Delete Component', () => {
        let comp: CollaboratorDeleteDialogComponent;
        let fixture: ComponentFixture<CollaboratorDeleteDialogComponent>;
        let service: CollaboratorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AsartTestModule],
                declarations: [CollaboratorDeleteDialogComponent],
                providers: [
                    CollaboratorService
                ]
            })
            .overrideTemplate(CollaboratorDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CollaboratorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CollaboratorService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});

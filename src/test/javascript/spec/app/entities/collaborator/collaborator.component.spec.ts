/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AsartTestModule } from '../../../test.module';
import { CollaboratorComponent } from '../../../../../../main/webapp/app/entities/collaborator/collaborator.component';
import { CollaboratorService } from '../../../../../../main/webapp/app/entities/collaborator/collaborator.service';
import { Collaborator } from '../../../../../../main/webapp/app/entities/collaborator/collaborator.model';

describe('Component Tests', () => {

    describe('Collaborator Management Component', () => {
        let comp: CollaboratorComponent;
        let fixture: ComponentFixture<CollaboratorComponent>;
        let service: CollaboratorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AsartTestModule],
                declarations: [CollaboratorComponent],
                providers: [
                    CollaboratorService
                ]
            })
            .overrideTemplate(CollaboratorComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CollaboratorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CollaboratorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Collaborator(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.collaborators[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

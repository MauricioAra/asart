/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AsartTestModule } from '../../../test.module';
import { LinkAuthDetailComponent } from '../../../../../../main/webapp/app/entities/link-auth/link-auth-detail.component';
import { LinkAuthService } from '../../../../../../main/webapp/app/entities/link-auth/link-auth.service';
import { LinkAuth } from '../../../../../../main/webapp/app/entities/link-auth/link-auth.model';

describe('Component Tests', () => {

    describe('LinkAuth Management Detail Component', () => {
        let comp: LinkAuthDetailComponent;
        let fixture: ComponentFixture<LinkAuthDetailComponent>;
        let service: LinkAuthService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AsartTestModule],
                declarations: [LinkAuthDetailComponent],
                providers: [
                    LinkAuthService
                ]
            })
            .overrideTemplate(LinkAuthDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LinkAuthDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LinkAuthService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LinkAuth(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.linkAuth).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

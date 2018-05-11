/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AsartTestModule } from '../../../test.module';
import { LinkAuthComponent } from '../../../../../../main/webapp/app/entities/link-auth/link-auth.component';
import { LinkAuthService } from '../../../../../../main/webapp/app/entities/link-auth/link-auth.service';
import { LinkAuth } from '../../../../../../main/webapp/app/entities/link-auth/link-auth.model';

describe('Component Tests', () => {

    describe('LinkAuth Management Component', () => {
        let comp: LinkAuthComponent;
        let fixture: ComponentFixture<LinkAuthComponent>;
        let service: LinkAuthService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AsartTestModule],
                declarations: [LinkAuthComponent],
                providers: [
                    LinkAuthService
                ]
            })
            .overrideTemplate(LinkAuthComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LinkAuthComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LinkAuthService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LinkAuth(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.linkAuths[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

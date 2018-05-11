/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AsartTestModule } from '../../../test.module';
import { LogWorkComponent } from '../../../../../../main/webapp/app/entities/log-work/log-work.component';
import { LogWorkService } from '../../../../../../main/webapp/app/entities/log-work/log-work.service';
import { LogWork } from '../../../../../../main/webapp/app/entities/log-work/log-work.model';

describe('Component Tests', () => {

    describe('LogWork Management Component', () => {
        let comp: LogWorkComponent;
        let fixture: ComponentFixture<LogWorkComponent>;
        let service: LogWorkService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AsartTestModule],
                declarations: [LogWorkComponent],
                providers: [
                    LogWorkService
                ]
            })
            .overrideTemplate(LogWorkComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogWorkComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogWorkService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LogWork(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.logWorks[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

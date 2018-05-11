/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AsartTestModule } from '../../../test.module';
import { LogWorkDetailComponent } from '../../../../../../main/webapp/app/entities/log-work/log-work-detail.component';
import { LogWorkService } from '../../../../../../main/webapp/app/entities/log-work/log-work.service';
import { LogWork } from '../../../../../../main/webapp/app/entities/log-work/log-work.model';

describe('Component Tests', () => {

    describe('LogWork Management Detail Component', () => {
        let comp: LogWorkDetailComponent;
        let fixture: ComponentFixture<LogWorkDetailComponent>;
        let service: LogWorkService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AsartTestModule],
                declarations: [LogWorkDetailComponent],
                providers: [
                    LogWorkService
                ]
            })
            .overrideTemplate(LogWorkDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogWorkDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogWorkService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LogWork(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.logWork).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

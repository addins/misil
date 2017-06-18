import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MisilTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SeminarDetailComponent } from '../../../../../../main/webapp/app/entities/seminar/seminar-detail.component';
import { SeminarService } from '../../../../../../main/webapp/app/entities/seminar/seminar.service';
import { Seminar } from '../../../../../../main/webapp/app/entities/seminar/seminar.model';

describe('Component Tests', () => {

    describe('Seminar Management Detail Component', () => {
        let comp: SeminarDetailComponent;
        let fixture: ComponentFixture<SeminarDetailComponent>;
        let service: SeminarService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MisilTestModule],
                declarations: [SeminarDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SeminarService,
                    JhiEventManager
                ]
            }).overrideTemplate(SeminarDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeminarDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeminarService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Seminar(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.seminar).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

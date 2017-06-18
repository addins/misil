import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MisilTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PeopleDetailComponent } from '../../../../../../main/webapp/app/entities/people/people-detail.component';
import { PeopleService } from '../../../../../../main/webapp/app/entities/people/people.service';
import { People } from '../../../../../../main/webapp/app/entities/people/people.model';

describe('Component Tests', () => {

    describe('People Management Detail Component', () => {
        let comp: PeopleDetailComponent;
        let fixture: ComponentFixture<PeopleDetailComponent>;
        let service: PeopleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MisilTestModule],
                declarations: [PeopleDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PeopleService,
                    JhiEventManager
                ]
            }).overrideTemplate(PeopleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PeopleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeopleService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new People(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.people).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

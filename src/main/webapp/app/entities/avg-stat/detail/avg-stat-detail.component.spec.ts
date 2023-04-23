import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AvgStatDetailComponent } from './avg-stat-detail.component';

describe('AvgStat Management Detail Component', () => {
  let comp: AvgStatDetailComponent;
  let fixture: ComponentFixture<AvgStatDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AvgStatDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ avgStat: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AvgStatDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AvgStatDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load avgStat on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.avgStat).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

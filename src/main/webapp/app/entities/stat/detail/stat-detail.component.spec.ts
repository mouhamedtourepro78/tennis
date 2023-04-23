import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StatDetailComponent } from './stat-detail.component';

describe('Stat Management Detail Component', () => {
  let comp: StatDetailComponent;
  let fixture: ComponentFixture<StatDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StatDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ stat: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StatDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StatDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load stat on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.stat).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

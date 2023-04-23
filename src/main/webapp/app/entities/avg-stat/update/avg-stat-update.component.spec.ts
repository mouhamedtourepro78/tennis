import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AvgStatFormService } from './avg-stat-form.service';
import { AvgStatService } from '../service/avg-stat.service';
import { IAvgStat } from '../avg-stat.model';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';

import { AvgStatUpdateComponent } from './avg-stat-update.component';

describe('AvgStat Management Update Component', () => {
  let comp: AvgStatUpdateComponent;
  let fixture: ComponentFixture<AvgStatUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let avgStatFormService: AvgStatFormService;
  let avgStatService: AvgStatService;
  let playerService: PlayerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AvgStatUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AvgStatUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AvgStatUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    avgStatFormService = TestBed.inject(AvgStatFormService);
    avgStatService = TestBed.inject(AvgStatService);
    playerService = TestBed.inject(PlayerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call player query and add missing value', () => {
      const avgStat: IAvgStat = { id: 456 };
      const player: IPlayer = { id: 29234 };
      avgStat.player = player;

      const playerCollection: IPlayer[] = [{ id: 79094 }];
      jest.spyOn(playerService, 'query').mockReturnValue(of(new HttpResponse({ body: playerCollection })));
      const expectedCollection: IPlayer[] = [player, ...playerCollection];
      jest.spyOn(playerService, 'addPlayerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ avgStat });
      comp.ngOnInit();

      expect(playerService.query).toHaveBeenCalled();
      expect(playerService.addPlayerToCollectionIfMissing).toHaveBeenCalledWith(playerCollection, player);
      expect(comp.playersCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const avgStat: IAvgStat = { id: 456 };
      const player: IPlayer = { id: 61342 };
      avgStat.player = player;

      activatedRoute.data = of({ avgStat });
      comp.ngOnInit();

      expect(comp.playersCollection).toContain(player);
      expect(comp.avgStat).toEqual(avgStat);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvgStat>>();
      const avgStat = { id: 123 };
      jest.spyOn(avgStatFormService, 'getAvgStat').mockReturnValue(avgStat);
      jest.spyOn(avgStatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avgStat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avgStat }));
      saveSubject.complete();

      // THEN
      expect(avgStatFormService.getAvgStat).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(avgStatService.update).toHaveBeenCalledWith(expect.objectContaining(avgStat));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvgStat>>();
      const avgStat = { id: 123 };
      jest.spyOn(avgStatFormService, 'getAvgStat').mockReturnValue({ id: null });
      jest.spyOn(avgStatService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avgStat: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avgStat }));
      saveSubject.complete();

      // THEN
      expect(avgStatFormService.getAvgStat).toHaveBeenCalled();
      expect(avgStatService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvgStat>>();
      const avgStat = { id: 123 };
      jest.spyOn(avgStatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avgStat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(avgStatService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePlayer', () => {
      it('Should forward to playerService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(playerService, 'comparePlayer');
        comp.comparePlayer(entity, entity2);
        expect(playerService.comparePlayer).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

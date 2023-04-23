import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StatFormService } from './stat-form.service';
import { StatService } from '../service/stat.service';
import { IStat } from '../stat.model';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';

import { StatUpdateComponent } from './stat-update.component';

describe('Stat Management Update Component', () => {
  let comp: StatUpdateComponent;
  let fixture: ComponentFixture<StatUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let statFormService: StatFormService;
  let statService: StatService;
  let playerService: PlayerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StatUpdateComponent],
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
      .overrideTemplate(StatUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StatUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    statFormService = TestBed.inject(StatFormService);
    statService = TestBed.inject(StatService);
    playerService = TestBed.inject(PlayerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Player query and add missing value', () => {
      const stat: IStat = { id: 456 };
      const player: IPlayer = { id: 11677 };
      stat.player = player;

      const playerCollection: IPlayer[] = [{ id: 9997 }];
      jest.spyOn(playerService, 'query').mockReturnValue(of(new HttpResponse({ body: playerCollection })));
      const additionalPlayers = [player];
      const expectedCollection: IPlayer[] = [...additionalPlayers, ...playerCollection];
      jest.spyOn(playerService, 'addPlayerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ stat });
      comp.ngOnInit();

      expect(playerService.query).toHaveBeenCalled();
      expect(playerService.addPlayerToCollectionIfMissing).toHaveBeenCalledWith(
        playerCollection,
        ...additionalPlayers.map(expect.objectContaining)
      );
      expect(comp.playersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const stat: IStat = { id: 456 };
      const player: IPlayer = { id: 95497 };
      stat.player = player;

      activatedRoute.data = of({ stat });
      comp.ngOnInit();

      expect(comp.playersSharedCollection).toContain(player);
      expect(comp.stat).toEqual(stat);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStat>>();
      const stat = { id: 123 };
      jest.spyOn(statFormService, 'getStat').mockReturnValue(stat);
      jest.spyOn(statService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stat }));
      saveSubject.complete();

      // THEN
      expect(statFormService.getStat).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(statService.update).toHaveBeenCalledWith(expect.objectContaining(stat));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStat>>();
      const stat = { id: 123 };
      jest.spyOn(statFormService, 'getStat').mockReturnValue({ id: null });
      jest.spyOn(statService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stat: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stat }));
      saveSubject.complete();

      // THEN
      expect(statFormService.getStat).toHaveBeenCalled();
      expect(statService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStat>>();
      const stat = { id: 123 };
      jest.spyOn(statService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(statService.update).toHaveBeenCalled();
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

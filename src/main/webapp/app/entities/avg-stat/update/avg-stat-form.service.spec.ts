import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../avg-stat.test-samples';

import { AvgStatFormService } from './avg-stat-form.service';

describe('AvgStat Form Service', () => {
  let service: AvgStatFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AvgStatFormService);
  });

  describe('Service methods', () => {
    describe('createAvgStatFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAvgStatFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            avgAces: expect.any(Object),
            avgDoubleFaults: expect.any(Object),
            avgServicePoints: expect.any(Object),
            avgFirstServeIn: expect.any(Object),
            avgFirstServeWon: expect.any(Object),
            avgSecondServeWon: expect.any(Object),
            avgServiceGames: expect.any(Object),
            avgSavedBreakPoints: expect.any(Object),
            avgFacedBreakPoints: expect.any(Object),
            player: expect.any(Object),
          })
        );
      });

      it('passing IAvgStat should create a new form with FormGroup', () => {
        const formGroup = service.createAvgStatFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            avgAces: expect.any(Object),
            avgDoubleFaults: expect.any(Object),
            avgServicePoints: expect.any(Object),
            avgFirstServeIn: expect.any(Object),
            avgFirstServeWon: expect.any(Object),
            avgSecondServeWon: expect.any(Object),
            avgServiceGames: expect.any(Object),
            avgSavedBreakPoints: expect.any(Object),
            avgFacedBreakPoints: expect.any(Object),
            player: expect.any(Object),
          })
        );
      });
    });

    describe('getAvgStat', () => {
      it('should return NewAvgStat for default AvgStat initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAvgStatFormGroup(sampleWithNewData);

        const avgStat = service.getAvgStat(formGroup) as any;

        expect(avgStat).toMatchObject(sampleWithNewData);
      });

      it('should return NewAvgStat for empty AvgStat initial value', () => {
        const formGroup = service.createAvgStatFormGroup();

        const avgStat = service.getAvgStat(formGroup) as any;

        expect(avgStat).toMatchObject({});
      });

      it('should return IAvgStat', () => {
        const formGroup = service.createAvgStatFormGroup(sampleWithRequiredData);

        const avgStat = service.getAvgStat(formGroup) as any;

        expect(avgStat).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAvgStat should not enable id FormControl', () => {
        const formGroup = service.createAvgStatFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAvgStat should disable id FormControl', () => {
        const formGroup = service.createAvgStatFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

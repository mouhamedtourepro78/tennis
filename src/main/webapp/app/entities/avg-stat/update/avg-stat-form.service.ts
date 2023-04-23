import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAvgStat, NewAvgStat } from '../avg-stat.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAvgStat for edit and NewAvgStatFormGroupInput for create.
 */
type AvgStatFormGroupInput = IAvgStat | PartialWithRequiredKeyOf<NewAvgStat>;

type AvgStatFormDefaults = Pick<NewAvgStat, 'id'>;

type AvgStatFormGroupContent = {
  id: FormControl<IAvgStat['id'] | NewAvgStat['id']>;
  avgAces: FormControl<IAvgStat['avgAces']>;
  avgDoubleFaults: FormControl<IAvgStat['avgDoubleFaults']>;
  avgServicePoints: FormControl<IAvgStat['avgServicePoints']>;
  avgFirstServeIn: FormControl<IAvgStat['avgFirstServeIn']>;
  avgFirstServeWon: FormControl<IAvgStat['avgFirstServeWon']>;
  avgSecondServeWon: FormControl<IAvgStat['avgSecondServeWon']>;
  avgServiceGames: FormControl<IAvgStat['avgServiceGames']>;
  avgSavedBreakPoints: FormControl<IAvgStat['avgSavedBreakPoints']>;
  avgFacedBreakPoints: FormControl<IAvgStat['avgFacedBreakPoints']>;
  player: FormControl<IAvgStat['player']>;
};

export type AvgStatFormGroup = FormGroup<AvgStatFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AvgStatFormService {
  createAvgStatFormGroup(avgStat: AvgStatFormGroupInput = { id: null }): AvgStatFormGroup {
    const avgStatRawValue = {
      ...this.getFormDefaults(),
      ...avgStat,
    };
    return new FormGroup<AvgStatFormGroupContent>({
      id: new FormControl(
        { value: avgStatRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      avgAces: new FormControl(avgStatRawValue.avgAces),
      avgDoubleFaults: new FormControl(avgStatRawValue.avgDoubleFaults),
      avgServicePoints: new FormControl(avgStatRawValue.avgServicePoints),
      avgFirstServeIn: new FormControl(avgStatRawValue.avgFirstServeIn),
      avgFirstServeWon: new FormControl(avgStatRawValue.avgFirstServeWon),
      avgSecondServeWon: new FormControl(avgStatRawValue.avgSecondServeWon),
      avgServiceGames: new FormControl(avgStatRawValue.avgServiceGames),
      avgSavedBreakPoints: new FormControl(avgStatRawValue.avgSavedBreakPoints),
      avgFacedBreakPoints: new FormControl(avgStatRawValue.avgFacedBreakPoints),
      player: new FormControl(avgStatRawValue.player),
    });
  }

  getAvgStat(form: AvgStatFormGroup): IAvgStat | NewAvgStat {
    return form.getRawValue() as IAvgStat | NewAvgStat;
  }

  resetForm(form: AvgStatFormGroup, avgStat: AvgStatFormGroupInput): void {
    const avgStatRawValue = { ...this.getFormDefaults(), ...avgStat };
    form.reset(
      {
        ...avgStatRawValue,
        id: { value: avgStatRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AvgStatFormDefaults {
    return {
      id: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IStat, NewStat } from '../stat.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStat for edit and NewStatFormGroupInput for create.
 */
type StatFormGroupInput = IStat | PartialWithRequiredKeyOf<NewStat>;

type StatFormDefaults = Pick<NewStat, 'id'>;

type StatFormGroupContent = {
  id: FormControl<IStat['id'] | NewStat['id']>;
  aces: FormControl<IStat['aces']>;
  doubleFaults: FormControl<IStat['doubleFaults']>;
  servicePoints: FormControl<IStat['servicePoints']>;
  firstServeIn: FormControl<IStat['firstServeIn']>;
  firstServeWon: FormControl<IStat['firstServeWon']>;
  secondServeWon: FormControl<IStat['secondServeWon']>;
  serviceGames: FormControl<IStat['serviceGames']>;
  savedBreakPoints: FormControl<IStat['savedBreakPoints']>;
  facedBreakPoints: FormControl<IStat['facedBreakPoints']>;
  player: FormControl<IStat['player']>;
};

export type StatFormGroup = FormGroup<StatFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StatFormService {
  createStatFormGroup(stat: StatFormGroupInput = { id: null }): StatFormGroup {
    const statRawValue = {
      ...this.getFormDefaults(),
      ...stat,
    };
    return new FormGroup<StatFormGroupContent>({
      id: new FormControl(
        { value: statRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      aces: new FormControl(statRawValue.aces),
      doubleFaults: new FormControl(statRawValue.doubleFaults),
      servicePoints: new FormControl(statRawValue.servicePoints),
      firstServeIn: new FormControl(statRawValue.firstServeIn),
      firstServeWon: new FormControl(statRawValue.firstServeWon),
      secondServeWon: new FormControl(statRawValue.secondServeWon),
      serviceGames: new FormControl(statRawValue.serviceGames),
      savedBreakPoints: new FormControl(statRawValue.savedBreakPoints),
      facedBreakPoints: new FormControl(statRawValue.facedBreakPoints),
      player: new FormControl(statRawValue.player),
    });
  }

  getStat(form: StatFormGroup): IStat | NewStat {
    return form.getRawValue() as IStat | NewStat;
  }

  resetForm(form: StatFormGroup, stat: StatFormGroupInput): void {
    const statRawValue = { ...this.getFormDefaults(), ...stat };
    form.reset(
      {
        ...statRawValue,
        id: { value: statRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): StatFormDefaults {
    return {
      id: null,
    };
  }
}

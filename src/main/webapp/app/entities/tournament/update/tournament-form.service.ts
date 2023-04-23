import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITournament, NewTournament } from '../tournament.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITournament for edit and NewTournamentFormGroupInput for create.
 */
type TournamentFormGroupInput = ITournament | PartialWithRequiredKeyOf<NewTournament>;

type TournamentFormDefaults = Pick<NewTournament, 'id'>;

type TournamentFormGroupContent = {
  id: FormControl<ITournament['id'] | NewTournament['id']>;
  name: FormControl<ITournament['name']>;
  surface: FormControl<ITournament['surface']>;
  drawSize: FormControl<ITournament['drawSize']>;
  level: FormControl<ITournament['level']>;
  date: FormControl<ITournament['date']>;
};

export type TournamentFormGroup = FormGroup<TournamentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TournamentFormService {
  createTournamentFormGroup(tournament: TournamentFormGroupInput = { id: null }): TournamentFormGroup {
    const tournamentRawValue = {
      ...this.getFormDefaults(),
      ...tournament,
    };
    return new FormGroup<TournamentFormGroupContent>({
      id: new FormControl(
        { value: tournamentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(tournamentRawValue.name),
      surface: new FormControl(tournamentRawValue.surface),
      drawSize: new FormControl(tournamentRawValue.drawSize),
      level: new FormControl(tournamentRawValue.level),
      date: new FormControl(tournamentRawValue.date),
    });
  }

  getTournament(form: TournamentFormGroup): ITournament | NewTournament {
    return form.getRawValue() as ITournament | NewTournament;
  }

  resetForm(form: TournamentFormGroup, tournament: TournamentFormGroupInput): void {
    const tournamentRawValue = { ...this.getFormDefaults(), ...tournament };
    form.reset(
      {
        ...tournamentRawValue,
        id: { value: tournamentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TournamentFormDefaults {
    return {
      id: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMatch, NewMatch } from '../match.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMatch for edit and NewMatchFormGroupInput for create.
 */
type MatchFormGroupInput = IMatch | PartialWithRequiredKeyOf<NewMatch>;

type MatchFormDefaults = Pick<NewMatch, 'id'>;

type MatchFormGroupContent = {
  id: FormControl<IMatch['id'] | NewMatch['id']>;
  matchNumber: FormControl<IMatch['matchNumber']>;
  score: FormControl<IMatch['score']>;
  bestOf: FormControl<IMatch['bestOf']>;
  matchRound: FormControl<IMatch['matchRound']>;
  minutes: FormControl<IMatch['minutes']>;
  winner: FormControl<IMatch['winner']>;
  loser: FormControl<IMatch['loser']>;
  tournament: FormControl<IMatch['tournament']>;
};

export type MatchFormGroup = FormGroup<MatchFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MatchFormService {
  createMatchFormGroup(match: MatchFormGroupInput = { id: null }): MatchFormGroup {
    const matchRawValue = {
      ...this.getFormDefaults(),
      ...match,
    };
    return new FormGroup<MatchFormGroupContent>({
      id: new FormControl(
        { value: matchRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      matchNumber: new FormControl(matchRawValue.matchNumber),
      score: new FormControl(matchRawValue.score),
      bestOf: new FormControl(matchRawValue.bestOf),
      matchRound: new FormControl(matchRawValue.matchRound),
      minutes: new FormControl(matchRawValue.minutes),
      winner: new FormControl(matchRawValue.winner),
      loser: new FormControl(matchRawValue.loser),
      tournament: new FormControl(matchRawValue.tournament),
    });
  }

  getMatch(form: MatchFormGroup): IMatch | NewMatch {
    return form.getRawValue() as IMatch | NewMatch;
  }

  resetForm(form: MatchFormGroup, match: MatchFormGroupInput): void {
    const matchRawValue = { ...this.getFormDefaults(), ...match };
    form.reset(
      {
        ...matchRawValue,
        id: { value: matchRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MatchFormDefaults {
    return {
      id: null,
    };
  }
}

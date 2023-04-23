import dayjs from 'dayjs/esm';

import { ITournament, NewTournament } from './tournament.model';

export const sampleWithRequiredData: ITournament = {
  id: 73077,
};

export const sampleWithPartialData: ITournament = {
  id: 35695,
  name: 'Future-proofed National e-business',
  drawSize: 97917,
};

export const sampleWithFullData: ITournament = {
  id: 54956,
  name: 'protocol scale',
  surface: 'Generic Beauty',
  drawSize: 61486,
  level: 'Berkshire',
  date: dayjs('2023-04-22'),
};

export const sampleWithNewData: NewTournament = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

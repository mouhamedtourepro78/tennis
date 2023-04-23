import { IMatch, NewMatch } from './match.model';

export const sampleWithRequiredData: IMatch = {
  id: 82797,
};

export const sampleWithPartialData: IMatch = {
  id: 81765,
  matchNumber: 99021,
  bestOf: 35227,
  matchRound: 'info-mediaries Group',
};

export const sampleWithFullData: IMatch = {
  id: 24405,
  matchNumber: 95492,
  score: 'Cambridgeshire Rustic',
  bestOf: 27187,
  matchRound: 'white Granite envisioneer',
  minutes: 34696,
};

export const sampleWithNewData: NewMatch = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

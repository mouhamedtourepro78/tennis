import { IStat, NewStat } from './stat.model';

export const sampleWithRequiredData: IStat = {
  id: 93465,
};

export const sampleWithPartialData: IStat = {
  id: 86779,
  firstServeIn: 72281,
  firstServeWon: 43099,
  secondServeWon: 92595,
  savedBreakPoints: 40131,
  facedBreakPoints: 26653,
};

export const sampleWithFullData: IStat = {
  id: 98553,
  aces: 50743,
  doubleFaults: 52530,
  servicePoints: 17208,
  firstServeIn: 41657,
  firstServeWon: 10753,
  secondServeWon: 89764,
  serviceGames: 77244,
  savedBreakPoints: 36584,
  facedBreakPoints: 26918,
};

export const sampleWithNewData: NewStat = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

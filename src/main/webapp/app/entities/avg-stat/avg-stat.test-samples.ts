import { IAvgStat, NewAvgStat } from './avg-stat.model';

export const sampleWithRequiredData: IAvgStat = {
  id: 94469,
};

export const sampleWithPartialData: IAvgStat = {
  id: 67548,
  avgServicePoints: 92842,
  avgFirstServeIn: 48932,
  avgFirstServeWon: 45115,
  avgSecondServeWon: 98225,
  avgSavedBreakPoints: 65614,
};

export const sampleWithFullData: IAvgStat = {
  id: 22913,
  avgAces: 97881,
  avgDoubleFaults: 35714,
  avgServicePoints: 98300,
  avgFirstServeIn: 13457,
  avgFirstServeWon: 1150,
  avgSecondServeWon: 96104,
  avgServiceGames: 3653,
  avgSavedBreakPoints: 13054,
  avgFacedBreakPoints: 74905,
};

export const sampleWithNewData: NewAvgStat = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

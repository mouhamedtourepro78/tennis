import { IPlayer, NewPlayer } from './player.model';

export const sampleWithRequiredData: IPlayer = {
  id: 92110,
};

export const sampleWithPartialData: IPlayer = {
  id: 90690,
  name: 'Reactive',
  height: 3535,
  nationality: 'Architect',
};

export const sampleWithFullData: IPlayer = {
  id: 53917,
  name: 'neural Massachusetts Phased',
  hand: 'Parkway Licensed',
  height: 45338,
  nationality: 'implement Dalasi Avon',
  age: 33283,
};

export const sampleWithNewData: NewPlayer = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

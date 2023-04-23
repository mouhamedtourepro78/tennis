import { IPlayer } from 'app/entities/player/player.model';

export interface IStat {
  id: number;
  aces?: number | null;
  doubleFaults?: number | null;
  servicePoints?: number | null;
  firstServeIn?: number | null;
  firstServeWon?: number | null;
  secondServeWon?: number | null;
  serviceGames?: number | null;
  savedBreakPoints?: number | null;
  facedBreakPoints?: number | null;
  player?: Pick<IPlayer, 'id'> | null;
}

export type NewStat = Omit<IStat, 'id'> & { id: null };

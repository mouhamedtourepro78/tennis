import { IPlayer } from 'app/entities/player/player.model';

export interface IAvgStat {
  id: number;
  avgAces?: number | null;
  avgDoubleFaults?: number | null;
  avgServicePoints?: number | null;
  avgFirstServeIn?: number | null;
  avgFirstServeWon?: number | null;
  avgSecondServeWon?: number | null;
  avgServiceGames?: number | null;
  avgSavedBreakPoints?: number | null;
  avgFacedBreakPoints?: number | null;
  player?: Pick<IPlayer, 'id'> | null;
}

export type NewAvgStat = Omit<IAvgStat, 'id'> & { id: null };

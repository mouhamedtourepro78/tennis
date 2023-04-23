import { IPlayer } from 'app/entities/player/player.model';
import { ITournament } from 'app/entities/tournament/tournament.model';

export interface IMatch {
  id: number;
  matchNumber?: number | null;
  score?: string | null;
  bestOf?: number | null;
  matchRound?: string | null;
  minutes?: number | null;
  winner?: Pick<IPlayer, 'id'> | null;
  loser?: Pick<IPlayer, 'id'> | null;
  tournament?: Pick<ITournament, 'id'> | null;
}

export type NewMatch = Omit<IMatch, 'id'> & { id: null };

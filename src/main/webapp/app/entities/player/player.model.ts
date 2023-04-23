export interface IPlayer {
  id: number;
  name?: string | null;
  hand?: string | null;
  height?: number | null;
  nationality?: string | null;
  age?: number | null;
}

export type NewPlayer = Omit<IPlayer, 'id'> & { id: null };

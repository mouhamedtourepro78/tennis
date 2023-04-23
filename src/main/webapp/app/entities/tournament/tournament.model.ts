import dayjs from 'dayjs/esm';

export interface ITournament {
  id: number;
  name?: string | null;
  surface?: string | null;
  drawSize?: number | null;
  level?: string | null;
  date?: dayjs.Dayjs | null;
}

export type NewTournament = Omit<ITournament, 'id'> & { id: null };

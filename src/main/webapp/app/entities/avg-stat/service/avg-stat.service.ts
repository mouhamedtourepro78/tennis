import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAvgStat, NewAvgStat } from '../avg-stat.model';

export type PartialUpdateAvgStat = Partial<IAvgStat> & Pick<IAvgStat, 'id'>;

export type EntityResponseType = HttpResponse<IAvgStat>;
export type EntityArrayResponseType = HttpResponse<IAvgStat[]>;

@Injectable({ providedIn: 'root' })
export class AvgStatService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/avg-stats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(avgStat: NewAvgStat): Observable<EntityResponseType> {
    return this.http.post<IAvgStat>(this.resourceUrl, avgStat, { observe: 'response' });
  }

  update(avgStat: IAvgStat): Observable<EntityResponseType> {
    return this.http.put<IAvgStat>(`${this.resourceUrl}/${this.getAvgStatIdentifier(avgStat)}`, avgStat, { observe: 'response' });
  }

  partialUpdate(avgStat: PartialUpdateAvgStat): Observable<EntityResponseType> {
    return this.http.patch<IAvgStat>(`${this.resourceUrl}/${this.getAvgStatIdentifier(avgStat)}`, avgStat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvgStat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvgStat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAvgStatIdentifier(avgStat: Pick<IAvgStat, 'id'>): number {
    return avgStat.id;
  }

  compareAvgStat(o1: Pick<IAvgStat, 'id'> | null, o2: Pick<IAvgStat, 'id'> | null): boolean {
    return o1 && o2 ? this.getAvgStatIdentifier(o1) === this.getAvgStatIdentifier(o2) : o1 === o2;
  }

  addAvgStatToCollectionIfMissing<Type extends Pick<IAvgStat, 'id'>>(
    avgStatCollection: Type[],
    ...avgStatsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const avgStats: Type[] = avgStatsToCheck.filter(isPresent);
    if (avgStats.length > 0) {
      const avgStatCollectionIdentifiers = avgStatCollection.map(avgStatItem => this.getAvgStatIdentifier(avgStatItem)!);
      const avgStatsToAdd = avgStats.filter(avgStatItem => {
        const avgStatIdentifier = this.getAvgStatIdentifier(avgStatItem);
        if (avgStatCollectionIdentifiers.includes(avgStatIdentifier)) {
          return false;
        }
        avgStatCollectionIdentifiers.push(avgStatIdentifier);
        return true;
      });
      return [...avgStatsToAdd, ...avgStatCollection];
    }
    return avgStatCollection;
  }
}

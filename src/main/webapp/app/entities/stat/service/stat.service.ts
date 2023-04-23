import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStat, NewStat } from '../stat.model';

export type PartialUpdateStat = Partial<IStat> & Pick<IStat, 'id'>;

export type EntityResponseType = HttpResponse<IStat>;
export type EntityArrayResponseType = HttpResponse<IStat[]>;

@Injectable({ providedIn: 'root' })
export class StatService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/stats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(stat: NewStat): Observable<EntityResponseType> {
    return this.http.post<IStat>(this.resourceUrl, stat, { observe: 'response' });
  }

  update(stat: IStat): Observable<EntityResponseType> {
    return this.http.put<IStat>(`${this.resourceUrl}/${this.getStatIdentifier(stat)}`, stat, { observe: 'response' });
  }

  partialUpdate(stat: PartialUpdateStat): Observable<EntityResponseType> {
    return this.http.patch<IStat>(`${this.resourceUrl}/${this.getStatIdentifier(stat)}`, stat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStatIdentifier(stat: Pick<IStat, 'id'>): number {
    return stat.id;
  }

  compareStat(o1: Pick<IStat, 'id'> | null, o2: Pick<IStat, 'id'> | null): boolean {
    return o1 && o2 ? this.getStatIdentifier(o1) === this.getStatIdentifier(o2) : o1 === o2;
  }

  addStatToCollectionIfMissing<Type extends Pick<IStat, 'id'>>(
    statCollection: Type[],
    ...statsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const stats: Type[] = statsToCheck.filter(isPresent);
    if (stats.length > 0) {
      const statCollectionIdentifiers = statCollection.map(statItem => this.getStatIdentifier(statItem)!);
      const statsToAdd = stats.filter(statItem => {
        const statIdentifier = this.getStatIdentifier(statItem);
        if (statCollectionIdentifiers.includes(statIdentifier)) {
          return false;
        }
        statCollectionIdentifiers.push(statIdentifier);
        return true;
      });
      return [...statsToAdd, ...statCollection];
    }
    return statCollection;
  }
}

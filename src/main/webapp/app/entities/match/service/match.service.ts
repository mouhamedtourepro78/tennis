import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMatch, NewMatch } from '../match.model';

export type PartialUpdateMatch = Partial<IMatch> & Pick<IMatch, 'id'>;

export type EntityResponseType = HttpResponse<IMatch>;
export type EntityArrayResponseType = HttpResponse<IMatch[]>;

@Injectable({ providedIn: 'root' })
export class MatchService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/matches');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(match: NewMatch): Observable<EntityResponseType> {
    return this.http.post<IMatch>(this.resourceUrl, match, { observe: 'response' });
  }

  update(match: IMatch): Observable<EntityResponseType> {
    return this.http.put<IMatch>(`${this.resourceUrl}/${this.getMatchIdentifier(match)}`, match, { observe: 'response' });
  }

  partialUpdate(match: PartialUpdateMatch): Observable<EntityResponseType> {
    return this.http.patch<IMatch>(`${this.resourceUrl}/${this.getMatchIdentifier(match)}`, match, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMatch>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMatch[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMatchIdentifier(match: Pick<IMatch, 'id'>): number {
    return match.id;
  }

  compareMatch(o1: Pick<IMatch, 'id'> | null, o2: Pick<IMatch, 'id'> | null): boolean {
    return o1 && o2 ? this.getMatchIdentifier(o1) === this.getMatchIdentifier(o2) : o1 === o2;
  }

  addMatchToCollectionIfMissing<Type extends Pick<IMatch, 'id'>>(
    matchCollection: Type[],
    ...matchesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const matches: Type[] = matchesToCheck.filter(isPresent);
    if (matches.length > 0) {
      const matchCollectionIdentifiers = matchCollection.map(matchItem => this.getMatchIdentifier(matchItem)!);
      const matchesToAdd = matches.filter(matchItem => {
        const matchIdentifier = this.getMatchIdentifier(matchItem);
        if (matchCollectionIdentifiers.includes(matchIdentifier)) {
          return false;
        }
        matchCollectionIdentifiers.push(matchIdentifier);
        return true;
      });
      return [...matchesToAdd, ...matchCollection];
    }
    return matchCollection;
  }
}

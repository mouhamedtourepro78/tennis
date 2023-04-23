import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAvgStat } from '../avg-stat.model';
import { AvgStatService } from '../service/avg-stat.service';

@Injectable({ providedIn: 'root' })
export class AvgStatRoutingResolveService implements Resolve<IAvgStat | null> {
  constructor(protected service: AvgStatService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvgStat | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((avgStat: HttpResponse<IAvgStat>) => {
          if (avgStat.body) {
            return of(avgStat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}

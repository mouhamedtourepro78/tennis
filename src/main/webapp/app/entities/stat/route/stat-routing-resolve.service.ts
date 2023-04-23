import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStat } from '../stat.model';
import { StatService } from '../service/stat.service';

@Injectable({ providedIn: 'root' })
export class StatRoutingResolveService implements Resolve<IStat | null> {
  constructor(protected service: StatService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStat | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((stat: HttpResponse<IStat>) => {
          if (stat.body) {
            return of(stat.body);
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

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StatComponent } from '../list/stat.component';
import { StatDetailComponent } from '../detail/stat-detail.component';
import { StatUpdateComponent } from '../update/stat-update.component';
import { StatRoutingResolveService } from './stat-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const statRoute: Routes = [
  {
    path: '',
    component: StatComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatDetailComponent,
    resolve: {
      stat: StatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatUpdateComponent,
    resolve: {
      stat: StatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatUpdateComponent,
    resolve: {
      stat: StatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(statRoute)],
  exports: [RouterModule],
})
export class StatRoutingModule {}

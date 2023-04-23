import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AvgStatComponent } from '../list/avg-stat.component';
import { AvgStatDetailComponent } from '../detail/avg-stat-detail.component';
import { AvgStatUpdateComponent } from '../update/avg-stat-update.component';
import { AvgStatRoutingResolveService } from './avg-stat-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const avgStatRoute: Routes = [
  {
    path: '',
    component: AvgStatComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvgStatDetailComponent,
    resolve: {
      avgStat: AvgStatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvgStatUpdateComponent,
    resolve: {
      avgStat: AvgStatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvgStatUpdateComponent,
    resolve: {
      avgStat: AvgStatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(avgStatRoute)],
  exports: [RouterModule],
})
export class AvgStatRoutingModule {}

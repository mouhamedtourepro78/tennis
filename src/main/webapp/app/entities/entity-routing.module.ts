import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'stat',
        data: { pageTitle: 'tennisApp.stat.home.title' },
        loadChildren: () => import('./stat/stat.module').then(m => m.StatModule),
      },
      {
        path: 'player',
        data: { pageTitle: 'tennisApp.player.home.title' },
        loadChildren: () => import('./player/player.module').then(m => m.PlayerModule),
      },
      {
        path: 'match',
        data: { pageTitle: 'tennisApp.match.home.title' },
        loadChildren: () => import('./match/match.module').then(m => m.MatchModule),
      },
      {
        path: 'tournament',
        data: { pageTitle: 'tennisApp.tournament.home.title' },
        loadChildren: () => import('./tournament/tournament.module').then(m => m.TournamentModule),
      },
      {
        path: 'avg-stat',
        data: { pageTitle: 'tennisApp.avgStat.home.title' },
        loadChildren: () => import('./avg-stat/avg-stat.module').then(m => m.AvgStatModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}

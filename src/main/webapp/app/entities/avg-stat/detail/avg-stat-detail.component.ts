import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvgStat } from '../avg-stat.model';

@Component({
  selector: 'jhi-avg-stat-detail',
  templateUrl: './avg-stat-detail.component.html',
})
export class AvgStatDetailComponent implements OnInit {
  avgStat: IAvgStat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avgStat }) => {
      this.avgStat = avgStat;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

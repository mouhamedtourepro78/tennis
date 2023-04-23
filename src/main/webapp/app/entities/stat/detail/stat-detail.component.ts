import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStat } from '../stat.model';

@Component({
  selector: 'jhi-stat-detail',
  templateUrl: './stat-detail.component.html',
})
export class StatDetailComponent implements OnInit {
  stat: IStat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stat }) => {
      this.stat = stat;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AvgStatFormService, AvgStatFormGroup } from './avg-stat-form.service';
import { IAvgStat } from '../avg-stat.model';
import { AvgStatService } from '../service/avg-stat.service';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';

@Component({
  selector: 'jhi-avg-stat-update',
  templateUrl: './avg-stat-update.component.html',
})
export class AvgStatUpdateComponent implements OnInit {
  isSaving = false;
  avgStat: IAvgStat | null = null;

  playersCollection: IPlayer[] = [];

  editForm: AvgStatFormGroup = this.avgStatFormService.createAvgStatFormGroup();

  constructor(
    protected avgStatService: AvgStatService,
    protected avgStatFormService: AvgStatFormService,
    protected playerService: PlayerService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePlayer = (o1: IPlayer | null, o2: IPlayer | null): boolean => this.playerService.comparePlayer(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avgStat }) => {
      this.avgStat = avgStat;
      if (avgStat) {
        this.updateForm(avgStat);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avgStat = this.avgStatFormService.getAvgStat(this.editForm);
    if (avgStat.id !== null) {
      this.subscribeToSaveResponse(this.avgStatService.update(avgStat));
    } else {
      this.subscribeToSaveResponse(this.avgStatService.create(avgStat));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvgStat>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(avgStat: IAvgStat): void {
    this.avgStat = avgStat;
    this.avgStatFormService.resetForm(this.editForm, avgStat);

    this.playersCollection = this.playerService.addPlayerToCollectionIfMissing<IPlayer>(this.playersCollection, avgStat.player);
  }

  protected loadRelationshipsOptions(): void {
    this.playerService
      .query({ filter: 'avgstats-is-null' })
      .pipe(map((res: HttpResponse<IPlayer[]>) => res.body ?? []))
      .pipe(map((players: IPlayer[]) => this.playerService.addPlayerToCollectionIfMissing<IPlayer>(players, this.avgStat?.player)))
      .subscribe((players: IPlayer[]) => (this.playersCollection = players));
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { StatFormService, StatFormGroup } from './stat-form.service';
import { IStat } from '../stat.model';
import { StatService } from '../service/stat.service';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';

@Component({
  selector: 'jhi-stat-update',
  templateUrl: './stat-update.component.html',
})
export class StatUpdateComponent implements OnInit {
  isSaving = false;
  stat: IStat | null = null;

  playersSharedCollection: IPlayer[] = [];

  editForm: StatFormGroup = this.statFormService.createStatFormGroup();

  constructor(
    protected statService: StatService,
    protected statFormService: StatFormService,
    protected playerService: PlayerService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePlayer = (o1: IPlayer | null, o2: IPlayer | null): boolean => this.playerService.comparePlayer(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stat }) => {
      this.stat = stat;
      if (stat) {
        this.updateForm(stat);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stat = this.statFormService.getStat(this.editForm);
    if (stat.id !== null) {
      this.subscribeToSaveResponse(this.statService.update(stat));
    } else {
      this.subscribeToSaveResponse(this.statService.create(stat));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStat>>): void {
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

  protected updateForm(stat: IStat): void {
    this.stat = stat;
    this.statFormService.resetForm(this.editForm, stat);

    this.playersSharedCollection = this.playerService.addPlayerToCollectionIfMissing<IPlayer>(this.playersSharedCollection, stat.player);
  }

  protected loadRelationshipsOptions(): void {
    this.playerService
      .query()
      .pipe(map((res: HttpResponse<IPlayer[]>) => res.body ?? []))
      .pipe(map((players: IPlayer[]) => this.playerService.addPlayerToCollectionIfMissing<IPlayer>(players, this.stat?.player)))
      .subscribe((players: IPlayer[]) => (this.playersSharedCollection = players));
  }
}

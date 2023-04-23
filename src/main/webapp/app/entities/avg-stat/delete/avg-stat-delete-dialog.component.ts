import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAvgStat } from '../avg-stat.model';
import { AvgStatService } from '../service/avg-stat.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './avg-stat-delete-dialog.component.html',
})
export class AvgStatDeleteDialogComponent {
  avgStat?: IAvgStat;

  constructor(protected avgStatService: AvgStatService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avgStatService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

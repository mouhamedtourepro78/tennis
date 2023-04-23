import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AvgStatComponent } from './list/avg-stat.component';
import { AvgStatDetailComponent } from './detail/avg-stat-detail.component';
import { AvgStatUpdateComponent } from './update/avg-stat-update.component';
import { AvgStatDeleteDialogComponent } from './delete/avg-stat-delete-dialog.component';
import { AvgStatRoutingModule } from './route/avg-stat-routing.module';

@NgModule({
  imports: [SharedModule, AvgStatRoutingModule],
  declarations: [AvgStatComponent, AvgStatDetailComponent, AvgStatUpdateComponent, AvgStatDeleteDialogComponent],
})
export class AvgStatModule {}

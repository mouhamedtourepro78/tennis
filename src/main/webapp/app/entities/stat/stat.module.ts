import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StatComponent } from './list/stat.component';
import { StatDetailComponent } from './detail/stat-detail.component';
import { StatUpdateComponent } from './update/stat-update.component';
import { StatDeleteDialogComponent } from './delete/stat-delete-dialog.component';
import { StatRoutingModule } from './route/stat-routing.module';

@NgModule({
  imports: [SharedModule, StatRoutingModule],
  declarations: [StatComponent, StatDetailComponent, StatUpdateComponent, StatDeleteDialogComponent],
})
export class StatModule {}

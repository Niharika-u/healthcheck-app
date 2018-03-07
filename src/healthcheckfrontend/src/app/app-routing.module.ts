import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProjectAdminComponent } from './project-admin/project-admin.component';
import { HealthcheckDashboardComponent } from './healthcheck-dashboard/healthcheck-dashboard.component';
import { HealthcheckAdminComponent } from './healthcheck-admin/healthcheck-admin.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: HealthcheckDashboardComponent },
  { path: 'admin', component: ProjectAdminComponent },
  { path: 'admin', component: HealthcheckAdminComponent }
]

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}

import { NgModule }  from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProjectAdminComponent } from './project-admin/project-admin.component';
import { HealthcheckDashboardComponent } from './healthcheck-dashboard/healthcheck-dashboard.component';
import { HealthcheckAdminComponent } from './healthcheck-admin/healthcheck-admin.component';

const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: HealthcheckDashboardComponent },
  { path: 'projadmin', component: ProjectAdminComponent },
  { path: 'hcadmin', component: HealthcheckAdminComponent },
]

export const routing = RouterModule.forRoot(routes);

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}

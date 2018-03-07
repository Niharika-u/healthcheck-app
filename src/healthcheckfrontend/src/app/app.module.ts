import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ProjectService } from './project.service';

import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';

import { AppComponent } from './app.component';

import { ProjectAdminComponent } from './project-admin/project-admin.component';
import { HealthcheckAdminComponent } from './healthcheck-admin/healthcheck-admin.component';
import { HealthcheckDashboardComponent } from './healthcheck-dashboard/healthcheck-dashboard.component';
import { AppRoutingModule } from './/app-routing.module';


@NgModule({
  declarations: [
    AppComponent,
    ProjectAdminComponent,
    HealthcheckAdminComponent,
    HealthcheckDashboardComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  providers: [ProjectService],
  bootstrap: [AppComponent]
})
export class AppModule { }

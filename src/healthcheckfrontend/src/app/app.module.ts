import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Component, ViewChild, ViewContainerRef, ComponentFactoryResolver}  from '@angular/core';

import { ProjectService } from './app-services/project.service';
import { UtilService } from './app-services/util.service';
import { HealthcheckService } from './app-services/healthcheck.service';

import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';
import { AppComponent } from './app.component';
import { ProjectAdminComponent } from './project-admin/project-admin.component';
import { HealthcheckAdminComponent } from './healthcheck-admin/healthcheck-admin.component';
import { HealthcheckDashboardComponent } from './healthcheck-dashboard/healthcheck-dashboard.component';
import { AppRoutingModule } from './app-routing.module';


@NgModule({
  declarations: [
    AppComponent,
    ProjectAdminComponent,
    HealthcheckAdminComponent,
    HealthcheckDashboardComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  providers: [ProjectService, UtilService, HealthcheckService],
  bootstrap: [AppComponent]
})
export class AppModule { }

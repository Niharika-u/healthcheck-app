import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';  
import { BrowserModule } from '@angular/platform-browser';
import { UtilService } from '../app-services/util.service';
import { HealthcheckService } from '../app-services/healthcheck.service';
import { HealthCheck } from '../healthcheck';
import { ProjectService } from '../app-services/project.service';
import { Project } from '../project';
import { NgModule }  from '@angular/core';

@Component({
  selector: 'app-healthcheck-dashboard',
  templateUrl: './healthcheck-dashboard.component.html',
  styleUrls: ['./healthcheck-dashboard.component.css']
})

@NgModule({
  imports: [CommonModule],
})

export class HealthcheckDashboardComponent implements OnInit {
  healthChecks: HealthCheck[];
  title = 'HealthCheck DashBoard';
  envTypes: Array<string>;
  uniqueProjectNames: string[] = [];

  constructor(
  	private healthCheckService: HealthcheckService,
    private utilService: UtilService) {  }

  ngOnInit() {
  	this.envTypes = this.utilService.envTypes;
  }

  getHealthChecks(selectedEnv: string): void {
    this.healthCheckService.getHealthChecks(selectedEnv)
    .then(healthChecks => this.healthChecks = healthChecks ) 
    .then(() => {
      for(let healthCheck of this.healthChecks){
        this.uniqueProjectNames.push(healthCheck.projectName);
      }
      this.uniqueProjectNames = Array.from(new Set(this.uniqueProjectNames));
    });
  }
}

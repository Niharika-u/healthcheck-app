import { Component, OnInit } from '@angular/core';
import { HealthCheck } from '../healthcheck';
import { NgForm } from '@angular/forms';
import { HealthcheckService } from '../app-services/healthcheck.service';
import { UtilService } from '../app-services/util.service';


@Component({
  selector: 'app-healthcheck-admin',
  templateUrl: './healthcheck-admin.component.html',
  styleUrls: ['./healthcheck-admin.component.css']
})

export class HealthcheckAdminComponent implements OnInit {
  healthChecks: HealthCheck[];
  newHealthCheck: HealthCheck = new HealthCheck();
  editing: boolean = false;
  editingHealthCheck: HealthCheck = new HealthCheck();
  envHCAdminTypes: Array<string>;

  constructor(
    private healthCheckService: HealthcheckService,
    private utilService: UtilService,
    ) {  }

  ngOnInit(): void {
    this.envHCAdminTypes = this.utilService.envTypes;
  }  

  getHealthChecks(selectedEnv: string): void {
    console.log(selectedEnv);
    this.healthCheckService.getHealthChecks(selectedEnv)
       .then(healthChecks => this.healthChecks = healthChecks );
  }

 /* createHealthCheck(addHealthCheckForm: NgForm): void {
    this.healthCheckService.createHealthCheck(this.newHealthCheck)
      .then(createHealthCheck => {
        addHealthCheckForm.reset();
        this.newHealthCheck = new HealthCheck();
        this.healthChecks.unshift(createHealthCheck);
      });
  }

  deleteHealthCheck(healthCheckId: string): void {
    this.healthCheckService.deleteHealthCheck(healthCheckId)
      .then(() => {
        this.healthChecks = this.healthChecks.filter(healthcheck => healthcheck.healthCheckId != healthCheckId);
      });
  }

  updateHealthCheck(healthCheckData: HealthCheck): void {
    console.log(healthCheckData);
    this.healthCheckService.updateHealthCheck(healthCheckData)
      .then(updateHealthCheck => {
        let existingHealthCheck = this.healthChecks.find(healthcheck => healthcheck.healthCheckId === updateHealthCheck.healthCheckId);
        Object.assign(existingHealthCheck, updateHealthCheck);
        this.clearEditing();
      })
  }

  editHealthCheck(healthCheckData: HealthCheck): void {
    this.editing = true;
    Object.assign(this.editingHealthCheck, healthCheckData);
  }


  clearEditing(): void {
    this.editingHealthCheck = new HealthCheck();
    this.editing = false;
  }
  */
}


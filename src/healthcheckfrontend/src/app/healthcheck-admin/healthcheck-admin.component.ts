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
  envTypes: Array<string>;

  constructor(
    private healthCheckService: HealthcheckService,
    private utilService: UtilService,
    ) {  }

  ngOnInit(): void {
    this.envTypes = this.utilService.envTypes;
    this.getHealthCheck();
  }  

  getHealthChecks(selectedEnv: string): void {
    this.healthCheckService.getHealthChecks(selectedEnv)
       .then(healthChecks => this.healthChecks = healthChecks );
       
 }

 getHealthCheck(): void {
    this.healthCheckService.getHealthCheck()
       .then(healthChecks => this.healthChecks = healthChecks );
       
 }

 addHealthcheck(newhcdetail: NgForm): void {
    this.healthCheckService.addHealthcheck(newhcdetail.value)
      .then(addHealthcheck => {
        newhcdetail.reset();
        this.newHealthCheck = new HealthCheck();
        this.healthChecks.unshift(addHealthcheck);
      });
  }

  deleteHealthcheckData(healthCheckId: string): void {
    this.healthCheckService.deleteHealthcheckData(healthCheckId)
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
  
}


import { Component, OnInit } from '@angular/core';
import { UtilService } from '../app-services/util.service';

@Component({
  selector: 'app-healthcheck-dashboard',
  templateUrl: './healthcheck-dashboard.component.html',
  styleUrls: ['./healthcheck-dashboard.component.css']
})
export class HealthcheckDashboardComponent implements OnInit {
  title = 'APPLICATION DASHBOARD';
  envTypes: Array<string>;

  constructor(
  	private utilService: UtilService) {  }

  ngOnInit() {
  	this.envTypes = this.utilService.envTypes;
  }

}

import { Injectable } from '@angular/core';
import { HealthCheck } from '../healthcheck';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import {Observable} from 'rxjs/Rx';

@Injectable()
export class HealthcheckService {

  private baseUrl = 'http://localhost:7979';
  hcdata: HealthCheck[];
  newhcData: HealthCheck = new HealthCheck();
  

  constructor(
    private http: Http) { }

  getHealthChecksForAnEnv(envType: string): Promise<HealthCheck[]> {
    return this.http.get(this.baseUrl + '/hcheck/env/' + envType)
      .toPromise()
      .then(response => response.json() as HealthCheck[])
      .catch(this.handleError);
  }

  getAllHealthCheck(): Promise<HealthCheck[]> {
    return this.http.get(this.baseUrl + '/hcheck/all')
      .toPromise()
      .then(response => response.json() as HealthCheck[])
      .catch(this.handleError);
  }

  getHealthCheckById(hcheckId: string): Promise<HealthCheck> {
    return this.http.get(this.baseUrl + '/hcheck/id/' + hcheckId)
      .toPromise()
      .then(response => response.json() as HealthCheck)
      .catch(this.handleError);
  }

 addHealthcheck(healthcheckData: HealthCheck): Promise<HealthCheck> {
    return this.http.post(this.baseUrl + '/hcheck/add', healthcheckData)
      .toPromise().then(response => response.json() as HealthCheck)
      .catch(this.handleError);
  }

 deleteHealthcheckData(healthcheckId: string): Promise<any> {
    return this.http.delete(this.baseUrl + '/hcheck/delete/' + healthcheckId)
      .toPromise()
      .catch(this.handleError);
  }

  updateHealthCheck(healthcheckData: HealthCheck): Promise<HealthCheck> {
    console.log(healthcheckData);
    return this.http.put(this.baseUrl + '/hcheck/' + healthcheckData.healthCheckId, healthcheckData)
      .toPromise()
      .then(response => response.json() as HealthCheck)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('Some error occured', error);
    return Promise.reject(error.message || error);
  }  

}

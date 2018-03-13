import { Injectable } from '@angular/core';
import { HealthCheck } from '../healthcheck';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class HealthcheckService {

  private baseUrl = 'http://localhost:7979';
  hcdata: HealthCheck[];
  newhcData: HealthCheck = new HealthCheck();
  

  constructor(
    private http: Http) { }

  getHealthChecks(envType: string): Promise<HealthCheck[]> {
    return this.http.get(this.baseUrl + '/hcheck/' + envType)
      .toPromise()
      .then(response => response.json() as HealthCheck[])
      .catch(this.handleError);
  }

  getHealthCheck(): Promise<HealthCheck[]> {
    return this.http.get(this.baseUrl + '/hcheck/all')
      .toPromise()
      .then(response => response.json() as HealthCheck[])
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

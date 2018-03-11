import { Injectable } from '@angular/core';
import { HealthCheck } from '../healthcheck';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class HealthcheckService {

  private baseUrl = 'http://localhost:7979';
  

  constructor(
  	private http: Http) { }

  getHealthChecks(envType: string): Promise<HealthCheck[]> {
    return this.http.get(this.baseUrl + '/hcheck/' + envType)
      .toPromise()
      .then(response => response.json() as HealthCheck[])
      .catch(this.handleError);
  }



  private handleError(error: any): Promise<any> {
    console.error('Some error occured', error);
    return Promise.reject(error.message || error);
  }  

}

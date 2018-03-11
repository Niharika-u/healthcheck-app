import { Injectable } from '@angular/core';

@Injectable()
export class UtilService {

  envTypes: Array<string> = ["QA", "STAGING", "PRODUCTION"];
  constructor() { }

}

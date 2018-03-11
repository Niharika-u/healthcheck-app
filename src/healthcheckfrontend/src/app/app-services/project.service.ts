import { Injectable } from '@angular/core';
import { Project } from '../project';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class ProjectService {
  private baseUrl = 'http://localhost:7979';

  constructor(private http: Http) { }

  getProjects(): Promise<Project[]> {
    return this.http.get(this.baseUrl + '/projects/allproj')
      .toPromise()
      .then(response => response.json() as Project[])
      .catch(this.handleError);
  }

  createProject(projectData: Project): Promise<Project> {
    return this.http.post(this.baseUrl + '/projects/add', projectData)
      .toPromise().then(response => response.json() as Project)
      .catch(this.handleError);
  }

  updateProject(projectData: Project): Promise<Project> {
    return this.http.put(this.baseUrl + '/projects/' + projectData.projectId, projectData)
      .toPromise()
      .then(response => response.json() as Project)
      .catch(this.handleError);
  }

  deleteProject(projectId: string): Promise<any> {
    return this.http.delete(this.baseUrl + '/projects/' + projectId)
      .toPromise()
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('Some error occured', error);
    return Promise.reject(error.message || error);
  }
}


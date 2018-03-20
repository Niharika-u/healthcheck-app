import { Component, Input, OnInit } from '@angular/core';
import { Project } from '../project';
import { NgForm } from '@angular/forms';
import { ProjectService } from '../app-services/project.service';

@Component({
  selector: 'app-project-admin',
  templateUrl: './project-admin.component.html',
  styleUrls: ['./project-admin.component.css']
})

export class ProjectAdminComponent implements OnInit {
  projects: Project[];
  newProject: Project = new Project();
  editing: boolean = false;
  editingProject: Project = new Project();


  constructor(
    private projectService: ProjectService,
  ) { }

  ngOnInit(): void {
    this.getProjects();
  }

  getProjects(): void {
    this.projectService.getProjects()
      .then(projects => this.projects = projects );
  }

  createProject(addProjectForm: NgForm): void {
    this.projectService.createProject(this.newProject)
      .then(createProject => {
        addProjectForm.reset();
        this.newProject = new Project();
        this.projects.unshift(createProject);
      });
  }

  deleteProject(projectId: string): void {
    this.projectService.deleteProject(projectId)
      .then(() => {
        this.projects = this.projects.filter(project => project.projectId != projectId);
      });
  }

  updateProject(projectData: Project): void {
    this.projectService.updateProject(projectData)
      .then(updatedProject => {
        let existingProject = this.projects.find(project => project.projectId === updatedProject.projectId);
        Object.assign(existingProject, updatedProject);
        this.clearEditing();
      });
  }

  editProject(projectData: Project): void {
    this.editing = true;
    Object.assign(this.editingProject, projectData);
  }

  clearEditing(): void {
    this.editingProject = new Project();
    this.editing = false;
  }

}

package com.example.healthcheckapp.services;

import com.example.healthcheckapp.services.dao.models.ProjectInfo;
import com.example.healthcheckapp.services.dao.models.ServerHealthCheck;
import com.example.healthcheckapp.services.dao.repos.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created By MMT6540 on 20 Mar, 2018
 */
@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    HealthCheckService healthCheckService;

    public List<ProjectInfo> getAllProjects() {
        Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "projectCreatedAt");
        return projectRepository.findAll(sortByCreatedAtDesc);
    }

    public ProjectInfo saveNewProjects(ProjectInfo newProject){
        return projectRepository.save(newProject);
    }

    public ProjectInfo getProjectByProjectId(String projectId){
        ProjectInfo projectInfo = projectRepository.findOne(projectId);
        if(projectInfo == null){
            return null;
        }else {
            return projectInfo;
        }
    }

    public ProjectInfo updateProjectAndProjectNameInHealthChecks(String projectId, ProjectInfo updateRequest){
        ProjectInfo projectInfoData = projectRepository.findOne(projectId);
        if(projectInfoData == null){
            return null;
        }

        //  Set the updated project name in all the HealthChecks
        List<ServerHealthCheck> listOfServerHealthChecksInAProject = healthCheckService.findAllHealthChecksOfAProject(projectInfoData.getProjectName());
        if(listOfServerHealthChecksInAProject.size() > 0){
            for(ServerHealthCheck healthCheck : listOfServerHealthChecksInAProject){
                healthCheck.setProjectName(updateRequest.getProjectName());
                healthCheckService.updateHealthCheck(healthCheck.getHealthCheckId(), healthCheck);
            }
        }

        //  Updating the projects
        projectInfoData.setProjectName(updateRequest.getProjectName());
        ProjectInfo updatedProjInfo = projectRepository.save(projectInfoData);
        return updatedProjInfo;
    }

    public void deleteProjectByIdAndDeleteHealthChecks(String projectId){
        ProjectInfo projectToDelete = projectRepository.findOne(projectId);

        //  Delete all the HealthChecks created for the project
        List<ServerHealthCheck> listOfServerHealthChecksForProject = healthCheckService.findAllHealthChecksOfAProject(projectToDelete.getProjectName());
        if(listOfServerHealthChecksForProject.size() > 0){
            for(ServerHealthCheck healthCheck : listOfServerHealthChecksForProject){
                healthCheckService.deleteHealthCheckById(healthCheck.getHealthCheckId());
            }
        }

        //  Deleting the Project
        projectRepository.delete(projectId);
    }

}

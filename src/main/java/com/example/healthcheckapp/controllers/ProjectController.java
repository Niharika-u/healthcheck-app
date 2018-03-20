package com.example.healthcheckapp.controllers;

import com.example.healthcheckapp.services.ProjectService;
import com.example.healthcheckapp.services.dao.models.ProjectInfo;
import com.example.healthcheckapp.services.dao.repos.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created By MMT6540 on 03 Mar, 2018
 */
@RestController
@RequestMapping("/projects")
@CrossOrigin("*")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/allproj")
    public List<ProjectInfo> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping("/add")
    public ProjectInfo addProjectInfo(@Valid @RequestBody ProjectInfo projectInfo) {
        return projectService.saveNewProjects(projectInfo);
    }

    @GetMapping(value="/{pid}")
    public ResponseEntity<ProjectInfo> getProjectById(@PathVariable("pid") String pid) {
        ProjectInfo projectInfo = projectService.getProjectByProjectId(pid);
        if(projectInfo == null){
            return new ResponseEntity<ProjectInfo>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<ProjectInfo>(projectInfo, HttpStatus.OK);
        }
    }

    @PutMapping(value="/{pid}")
    public ResponseEntity<ProjectInfo> updateProject(@PathVariable("pid") String pid, @Valid @RequestBody ProjectInfo projectInfo){
        ProjectInfo projectInfoData = projectService.updateProjectAndProjectNameInHealthChecks(pid, projectInfo);
        if(projectInfoData == null){
            return new ResponseEntity<ProjectInfo>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<ProjectInfo>(projectInfoData, HttpStatus.OK);
        }
    }

    @DeleteMapping(value="/{pid}")
    public void deleteProjectInfo(@PathVariable("pid") String pid){
        projectService.deleteProjectByIdAndDeleteHealthChecks(pid);
    }

}

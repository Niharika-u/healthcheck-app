package com.example.healthcheckapp.controllers;

import com.example.healthcheckapp.models.ProjectInfo;
import com.example.healthcheckapp.repositories.ProjectRepository;
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
    ProjectRepository projectRepository;

    @GetMapping("/allproj")
    public List<ProjectInfo> getAllProjects() {
        Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "projectCreatedAt");
        return projectRepository.findAll(sortByCreatedAtDesc);
    }

    @PostMapping("/add")
    public ProjectInfo addProjectInfo(@Valid @RequestBody ProjectInfo projectInfo) {
        return projectRepository.save(projectInfo);
    }

    @GetMapping(value="/{pid}")
    public ResponseEntity<ProjectInfo> getProjectById(@PathVariable("pid") String pid) {
        ProjectInfo projectInfo = projectRepository.findOne(pid);
        if(projectInfo == null){
            return new ResponseEntity<ProjectInfo>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<ProjectInfo>(projectInfo, HttpStatus.OK);
        }
    }

    @PutMapping(value="/{pid}")
    public ResponseEntity<ProjectInfo> updateProject(@PathVariable("pid") String pid, @Valid @RequestBody ProjectInfo projectInfo){
        ProjectInfo projectInfoData = projectRepository.findOne(pid);
        if(projectInfoData == null){
            return new ResponseEntity<ProjectInfo>(HttpStatus.NOT_FOUND);
        }
        projectInfoData.setProjectName(projectInfo.getProjectName());
        ProjectInfo updatedProjInfo = projectRepository.save(projectInfoData);
        return new ResponseEntity<ProjectInfo>(updatedProjInfo, HttpStatus.OK);
    }

    @DeleteMapping(value="/{pid}")
    public void deleteProjectInfo(@PathVariable("pid") String pid){
        projectRepository.delete(pid);
    }

}

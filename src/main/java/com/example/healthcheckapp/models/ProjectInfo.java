package com.example.healthcheckapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created By MMT6540 on 02 Mar, 2018
 */
@Document(collection="projectnametbl")
@JsonIgnoreProperties(value = {"projectCreatedAt"}, allowGetters = true)
public class ProjectInfo {

    @Id
    private String projectId;

    @NotBlank
    @Size(max = 150)
    @Indexed(unique = true)
    private String projectName;

    @NotBlank
    private String createdBy;

    private Date projectCreatedAt = new Date();

    public ProjectInfo(){
        super();
    }

    public ProjectInfo(String projectName){
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getProjectCreatedAt() {
        return projectCreatedAt;
    }

    public void setProjectCreatedAt(Date projectCreatedAt) {
        this.projectCreatedAt = projectCreatedAt;
    }

    @Override
    public String toString(){
        return String.format("ProjectInfo[projectId=%s, projectName='%s', createdBy='%s', projectCreatedAt='%s']",
                projectId, projectName, createdBy, projectCreatedAt);
    }
}

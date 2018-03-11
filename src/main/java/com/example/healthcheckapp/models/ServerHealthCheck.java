package com.example.healthcheckapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

/**
 * Created By MMT6540 on 02 Mar, 2018
 */
@Document(collection="servicehealthchecktbl")
@JsonIgnoreProperties(value = {"healthCheckCreatedAt"}, allowGetters = true)
public class ServerHealthCheck {

    @Id
    private int healthCheckId;

    @NotBlank
    private String componentName;

    @NotBlank
    /*Get the entire Project Object when the Project/Team name is selected from the dropdown*/
    private String projectName;

    @NotBlank
    private String ipAddress;


    @NotBlank
    private String applicationPort;

    @NotBlank
    private String healthCheckUrl;

    @NotBlank
    private String createdBy;

    private Date healthCheckCreatedAt = new Date();

    @NotBlank
    public String envName;

    public ServerHealthCheck(){
        super();
    }

    public ServerHealthCheck(String projectName, String envName, String componentName, String ipAddress, String applicationPort, String healthCheckUrl){
        this.projectName = projectName;
        this.envName = envName;
        this.componentName = componentName;
        this.ipAddress = ipAddress;
        this.applicationPort = applicationPort;
        this.healthCheckUrl = healthCheckUrl;

    }

    public ServerHealthCheck(String projectName, String envName, String componentName, String ipAddress, String applicationPort, String healthCheckUrl, String createdBy){
        this.projectName = projectName;
        this.envName = envName;
        this.componentName = componentName;
        this.ipAddress = ipAddress;
        this.applicationPort = applicationPort;
        this.healthCheckUrl = healthCheckUrl;
        this.createdBy = createdBy;
    }

    public int getHealthCheckId() {
        return healthCheckId;
    }

    public void setHealthCheckId(int healthCheckId) {
        this.healthCheckId = healthCheckId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getApplicationPort() {
        return applicationPort;
    }

    public void setApplicationPort(String applicationPort) {
        this.applicationPort = applicationPort;
    }

    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getHealthCheckCreatedAt() {
        return healthCheckCreatedAt;
    }

    public void setHealthCheckCreatedAt(Date healthCheckCreatedAt) {
        this.healthCheckCreatedAt = healthCheckCreatedAt;
    }

    @Override
    public String toString() {
        return String.format("ServerHealthCheck[healthCheckId=%s, componentName='%s', envName='%s', ipAddress='%s', applicationPort='%s', healthCheckUrl='%s', createdBy='%s', healthCheckCreatedAt='%s']",
                healthCheckId, componentName, envName, ipAddress, applicationPort, healthCheckUrl, createdBy, healthCheckCreatedAt);
    }
}

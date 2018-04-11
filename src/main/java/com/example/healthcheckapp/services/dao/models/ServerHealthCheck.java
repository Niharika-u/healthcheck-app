package com.example.healthcheckapp.services.dao.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

/**
 * Created By MMT6540 on 02 Mar, 2018
 */
@Document(collection="servicehealthchecktbl")
@JsonIgnoreProperties(value = {"healthCheckCreatedAt"}, allowGetters = true)
public class ServerHealthCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String healthCheckId;

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
    private String healthCheckPort;

    @NotBlank
    private String healthCheckUrl;

    @NotBlank
    private String emailIdOfCreatedBy;

    @NotBlank
    private String groupEmailId;

    private Date healthCheckCreatedAt = new Date();

    private Date healthCheckUpdatedAt = new Date();

    private Date timeOfUpdatingDailyNotificationPreference = new Date();

    private Boolean notificationSentStatus;

    private Boolean dailyNotificationStatus;

    private String countOfDaysForBlockingNotification;

    @NotBlank
    public String envName;

    private Boolean serverStatus;

    public ServerHealthCheck(){
        super();
    }

    /*
    Creating a Server Health Check with Created by's Email ID, Group Email Id and DailyNotification Status set as FALSE
     */
    public ServerHealthCheck(String projectName, String envName, String componentName, String ipAddress, String applicationPort, String healthCheckPort, String healthCheckUrl, Boolean serverStatus, String createdBy, String groupEmailId, Boolean dailyNotificationStatus, String countOfDaysForBlockingNotification){
        this.projectName = projectName;
        this.envName = envName;
        this.componentName = componentName;
        this.ipAddress = ipAddress;
        this.applicationPort = applicationPort;
        this.healthCheckPort = healthCheckPort;
        this.healthCheckUrl = healthCheckUrl;
        this.serverStatus = serverStatus;
        this.emailIdOfCreatedBy = createdBy;
        this.groupEmailId = groupEmailId;
        this.dailyNotificationStatus = dailyNotificationStatus;
        this.countOfDaysForBlockingNotification = countOfDaysForBlockingNotification;
    }


    public String getEmailIdOfCreatedBy() {
        return emailIdOfCreatedBy;
    }

    public void setEmailIdOfCreatedBy(String emailIdOfCreatedBy) {
        this.emailIdOfCreatedBy = emailIdOfCreatedBy;
    }

    public String getGroupEmailId() {
        return groupEmailId;
    }

    public void setGroupEmailId(String groupEmailId) {
        this.groupEmailId = groupEmailId;
    }

    public Boolean getDailyNotificationStatus() {
        return dailyNotificationStatus;
    }

    public void setDailyNotificationStatus(Boolean dailyNotificationStatus) {
        this.dailyNotificationStatus = dailyNotificationStatus;
    }

    public String getCountOfDaysForBlockingNotification() {
        return countOfDaysForBlockingNotification;
    }

    public void setCountOfDaysForBlockingNotification(String countOfDaysForBlockingNotification) {
        this.countOfDaysForBlockingNotification = countOfDaysForBlockingNotification;
    }

    public String getHealthCheckId() {
        return healthCheckId;
    }

    public void setHealthCheckId(String healthCheckId) {
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

    public String getHealthCheckPort() {
        return healthCheckPort;
    }

    public void setHealthCheckPort(String healthCheckPort) {
        this.healthCheckPort = healthCheckPort;
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

    public Boolean getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(Boolean serverStatus) {
        this.serverStatus = serverStatus;
    }

    public Date getHealthCheckCreatedAt() {
        return healthCheckCreatedAt;
    }

    public void setHealthCheckCreatedAt(Date healthCheckCreatedAt) {
        this.healthCheckCreatedAt = healthCheckCreatedAt;
    }

    public Date getHealthCheckUpdatedAt() {
        return healthCheckUpdatedAt;
    }

    public void setHealthCheckUpdatedAt(Date healthCheckUpdatedAt) {
        this.healthCheckUpdatedAt = healthCheckUpdatedAt;
    }

    public Boolean getNotificationSentStatus() {
        return notificationSentStatus;
    }

    public Date getTimeOfUpdatingDailyNotificationPreference() {
        return timeOfUpdatingDailyNotificationPreference;
    }

    public void setTimeOfUpdatingDailyNotificationPreference(Date timeOfUpdatingDailyNotificationPreference) {
        this.timeOfUpdatingDailyNotificationPreference = timeOfUpdatingDailyNotificationPreference;
    }

    public void setNotificationSentStatus(Boolean notificationSentStatus) {
        this.notificationSentStatus = notificationSentStatus;
    }

    @Override
    public String toString() {
        return String.format("ServerHealthCheck[healthCheckId=%s, componentName='%s', envName='%s', ipAddress='%s', applicationPort='%s', healthCheckPort='%s', healthCheckUrl='%s', serverStatus='%s', emailIdOfCreatedBy='%s', groupEmailId='%s', healthCheckCreatedAt='%s', healthCheckUpdatedAt='%s', notificationSentStatus='%s', dailyNotificationStatus='%s', countOfDaysForBlockingNotification='%s', timeOfUpdatingDailyNotificationPreference='%s'",
                healthCheckId, componentName, envName, ipAddress, applicationPort, healthCheckPort, healthCheckUrl, serverStatus, emailIdOfCreatedBy, groupEmailId, healthCheckCreatedAt, healthCheckUpdatedAt, notificationSentStatus, dailyNotificationStatus, countOfDaysForBlockingNotification, timeOfUpdatingDailyNotificationPreference);
    }


}

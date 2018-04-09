package com.example.healthcheckapp.services;

import com.example.healthcheckapp.services.dao.models.ServerHealthCheck;
import com.example.healthcheckapp.services.dao.repos.HealthCheckRepository;
import com.example.healthcheckapp.util.DateIncrementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created By MMT6540 on 20 Mar, 2018
 */
@Service
public class HealthCheckService {


    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    HealthCheckRepository healthCheckRepository;

    Query query;

    @Value("${owner.mailing.rate}")
    private String mailingRate;

    @Autowired
    private HealthCheckStatusService healthCheckStatusService;

    private Logger logger = LoggerFactory.getLogger(HealthCheckService.class);

    public List<ServerHealthCheck> getAllHealthchecksByFilter(String env,String projectName){
        query = new Query();
        if(!projectName.equals("blank")){
            query.addCriteria(Criteria.where("projectName").is(projectName));
        }
        if(!env.equals("blank")) {
            query.addCriteria(Criteria.where("envName").is(env));
        }

        List<ServerHealthCheck> listOfServerHealthChecks = mongoTemplate.find(query,ServerHealthCheck.class);
        return listOfServerHealthChecks;

    }

    public ServerHealthCheck findHealthCheckById(String healthCheckId) {
        query = new Query();
        query.addCriteria(Criteria.where("healthCheckId").is(healthCheckId));
        ServerHealthCheck desiredHealthCheck = mongoTemplate.findOne(query, ServerHealthCheck.class);
        return desiredHealthCheck;
    }

    public List<ServerHealthCheck> findAllHealthChecksOfAProject(String projectName) {
        query = new Query();
        query.addCriteria(Criteria.where("projectName").is(projectName));
        List<ServerHealthCheck> listOfServerHealthChecksOfSameProject = mongoTemplate.find(query, ServerHealthCheck.class);
        return listOfServerHealthChecksOfSameProject;
    }

    public ServerHealthCheck saveHealthChecks(ServerHealthCheck serverHealthCheck) {
        return healthCheckRepository.save(serverHealthCheck);
    }

    public void deleteHealthCheckById(String serverHealthCheckId) {
        healthCheckRepository.delete(serverHealthCheckId);
    }

    public ServerHealthCheck updateHealthCheck(String healthCheckId, ServerHealthCheck updateRequest){
        ServerHealthCheck existingHealthCheckInfo = healthCheckRepository.findOne(healthCheckId);

        if(existingHealthCheckInfo == null){
            return null;
        }
        existingHealthCheckInfo.setApplicationPort(updateRequest.getApplicationPort());
        existingHealthCheckInfo.setHealthCheckPort(updateRequest.getHealthCheckPort());
        existingHealthCheckInfo.setServerStatus(updateRequest.getServerStatus());
        existingHealthCheckInfo.setNotificationSentStatus(updateRequest.getNotificationSentStatus());

        //  Scenario when Daily Notification Flag is toggled. Count Of Days to block notification would be set only when the flag is toggled
        if(existingHealthCheckInfo.getDailyNotificationStatus() != updateRequest.getDailyNotificationStatus()){
            existingHealthCheckInfo.setDailyNotificationStatus(updateRequest.getDailyNotificationStatus());
            if(updateRequest.getDailyNotificationStatus() != true){
                int noOfDaysForBlockingNotification = Integer.parseInt(updateRequest.getCountOfDaysForBlockingNotification());
                //  Setting the time of Daily Notification on the day till it is blocked
                existingHealthCheckInfo.setTimeOfUpdatingDailyNotificationPreference(DateIncrementor.incrementDate(new Date(), noOfDaysForBlockingNotification));
            }
            if(updateRequest.getDailyNotificationStatus() == true){
                //  Setting the time of Daily Notification as the next day of Updating the HealthCheck
                existingHealthCheckInfo.setTimeOfUpdatingDailyNotificationPreference(DateIncrementor.incrementDate(new Date(), 1));
                existingHealthCheckInfo.setCountOfDaysForBlockingNotification("0");
            }
        }

        //  Scenario when Daily Notification Flag is not toggled. Count of days to block notification would be set to remaining days which was set when the Daily notifications were bocked
        if((existingHealthCheckInfo.getDailyNotificationStatus() == updateRequest.getDailyNotificationStatus()) && (updateRequest.getDailyNotificationStatus() == false)){
            Date currDate = new Date();
            long diff = existingHealthCheckInfo.getTimeOfUpdatingDailyNotificationPreference().getTime() - currDate.getTime();
            existingHealthCheckInfo.setCountOfDaysForBlockingNotification(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
        }

        existingHealthCheckInfo.setComponentName(updateRequest.getComponentName());
        existingHealthCheckInfo.setEmailIdOfCreatedBy(updateRequest.getEmailIdOfCreatedBy());
        existingHealthCheckInfo.setGroupEmailId(updateRequest.getGroupEmailId());
        existingHealthCheckInfo.setEnvName(updateRequest.getEnvName());
        existingHealthCheckInfo.setHealthCheckUrl(updateRequest.getHealthCheckUrl());
        existingHealthCheckInfo.setProjectName(updateRequest.getProjectName());
        existingHealthCheckInfo.setIpAddress(updateRequest.getIpAddress());

        healthCheckRepository.save(existingHealthCheckInfo);
        return existingHealthCheckInfo;

    }

    public void updateLiveHealthCheckStatus(){
        List<ServerHealthCheck> listOfHealthChecks = healthCheckRepository.findAll();
        boolean preVerificationServerStatus = false, postVerificationServerStatus = false;
        for(ServerHealthCheck healthCheck : listOfHealthChecks) {
            preVerificationServerStatus = healthCheck.getServerStatus();
            postVerificationServerStatus = healthCheckStatusService.updateServerStatusForHealthChecks(healthCheck);
            if(preVerificationServerStatus != postVerificationServerStatus){
                healthCheck.setServerStatus(postVerificationServerStatus);
                healthCheckRepository.save(healthCheck);
            }
        }
    }

    public void sendDailyNotificationForServerFailures(){
        List<ServerHealthCheck> listOfHealthChecks = healthCheckRepository.findAll();
        Date dailyNotificationBlockedTillDate;
        Date currDate = new Date();
        for(ServerHealthCheck healthCheck : listOfHealthChecks) {
            if(!healthCheck.getServerStatus()){
                if(!healthCheck.getDailyNotificationStatus()){
                    //  Dont Send Daily Notification
                    dailyNotificationBlockedTillDate = healthCheck.getTimeOfUpdatingDailyNotificationPreference();
                    long diff = dailyNotificationBlockedTillDate.getTime() - currDate.getTime();
                    if(diff <= 0){
                        //  This means that the time limit for blocking the notification is expired
                        healthCheck.setCountOfDaysForBlockingNotification("0");
                        healthCheck.setDailyNotificationStatus(true);
                        healthCheckStatusService.sendNotificationForApplicationDowntime(healthCheck);
                    }else{
                        healthCheck.setCountOfDaysForBlockingNotification(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                    }

                }else{
                    //  Send Daily notification
                    healthCheckStatusService.sendNotificationForApplicationDowntime(healthCheck);
                }
            }
        }
    }

    public void sendNotificationForServerFailures(){
        List<ServerHealthCheck> listOfHealthChecks = healthCheckRepository.findAll();
        boolean applicationHealthCheckStatus, ownerNotificationStatus;
        Date currentTime = new Date();
        for(ServerHealthCheck healthCheck : listOfHealthChecks) {
            applicationHealthCheckStatus = healthCheck.getServerStatus();
            ownerNotificationStatus = healthCheck.getNotificationSentStatus();

            //  Validating the time of updating the HealthCheck as False has crossed time mentioned in the PROPERTIES file in m
            if(((currentTime.getTime() - healthCheck.getHealthCheckUpdatedAt().getTime())) > Integer.parseInt(mailingRate)){
                if((!applicationHealthCheckStatus) && (!ownerNotificationStatus)){
                    if(healthCheckStatusService.sendNotificationForApplicationDowntime(healthCheck)){
                        logger.info("Mail Sent to HealthCheck Owner");
                        healthCheck.setNotificationSentStatus(true);
                        updateHealthCheck(healthCheck.getHealthCheckId(), healthCheck);
                    }else{
                        logger.error("Mail Notiication FAILED");
                    }

                }
            }

        }

    }
}

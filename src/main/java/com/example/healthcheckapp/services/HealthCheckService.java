package com.example.healthcheckapp.services;

import com.example.healthcheckapp.services.dao.models.ServerHealthCheck;
import com.example.healthcheckapp.services.dao.repos.HealthCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private HealthCheckStatusService healthCheckStatusService;

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
        existingHealthCheckInfo.setComponentName(updateRequest.getComponentName());
        existingHealthCheckInfo.setCreatedBy(updateRequest.getCreatedBy());
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
}

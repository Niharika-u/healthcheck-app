package com.example.healthcheckapp.controllers;

import com.example.healthcheckapp.models.ServerHealthCheck;
import com.example.healthcheckapp.repositories.HealthCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 212583997 on 3/3/2018.
 */
@RestController
@RequestMapping("/hcurl")
@CrossOrigin("*")
public class HealthCheckController {

    @Autowired
    HealthCheckRepository healthCheckRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping(value="/{env}")
    public List<ServerHealthCheck> getAllHealthchksByEnv(@PathVariable("env") String env) {
        Query query = new Query();
        query.addCriteria(Criteria.where("envName").is(env));
        List<ServerHealthCheck> listOfServerHealthChecks = mongoTemplate.find(query, ServerHealthCheck.class);
        return listOfServerHealthChecks;
        //return healthCheckRepository.findAll();
    }

    @DeleteMapping(value="/{hcid}")
    public void deleteHealthcheckInfo(@PathVariable("hcid") String healthCheckId) {
        healthCheckRepository.delete(healthCheckId);
    }

    @PutMapping(value="/{hcid}")
    public ResponseEntity<ServerHealthCheck> updateHealthcheckData(@PathVariable("hcid") String healthCheckId) {
        ServerHealthCheck
    }
}

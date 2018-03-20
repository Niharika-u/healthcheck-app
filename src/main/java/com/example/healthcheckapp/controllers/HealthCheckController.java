package com.example.healthcheckapp.controllers;

import com.example.healthcheckapp.services.HealthCheckService;
import com.example.healthcheckapp.services.HealthCheckStatusService;
import com.example.healthcheckapp.services.dao.models.ServerHealthCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by 212583997 on 3/3/2018.
 */
@RestController
@EnableScheduling
@RequestMapping("/hcheck")
@CrossOrigin("*")
public class HealthCheckController {


    @Autowired
    private HealthCheckService healthCheckService;

    @GetMapping(value="/env/{env}")
    public List<ServerHealthCheck> getAllHealthchecksByEnv(@PathVariable("env") String env) {
        return healthCheckService.getAllHealthChecksByEnv(env);
    }

    @GetMapping(value="/id/{hcheckId}")
    public ServerHealthCheck getHealthCheckForAnHealthCheckId(@PathVariable("hcheckId") String hcheckId) {
        return healthCheckService.findHealthCheckById(hcheckId);
    }

    @GetMapping(value="/all")
    public List<ServerHealthCheck> getAllHealthchecks() {
        return healthCheckService.findAllHealthChecks();
    }


    @Scheduled(fixedRate = 6000)
    public void updateTableForHealthcheckStatus() {
        healthCheckService.updateLiveHealthCheckStatus();
    }

    @PostMapping(value="/add")
    public ServerHealthCheck addServerHealthCheck(@Valid @RequestBody ServerHealthCheck serverHealthCheck) {
        //  Setting the server status as false at the time of addtion as a default mechanism
        if(serverHealthCheck.getServerStatus() == null)
            serverHealthCheck.setServerStatus(false);
        return healthCheckService.saveHealthChecks(serverHealthCheck);
    }

    @DeleteMapping(value="/delete/{healthCheckId}")
    public void deleteHealthcheck(@PathVariable("healthCheckId") String healthCheckId) {
        healthCheckService.deleteHealthCheckById(healthCheckId);
    }

    @PutMapping(value="/{hcid}")
    public ResponseEntity<ServerHealthCheck> updateHealthcheckData(@PathVariable("hcid") String hcid, @Valid @RequestBody ServerHealthCheck serverHealthCheckReq) {

        ServerHealthCheck updatedHealthCheckInfo = healthCheckService.updateHealthCheck(hcid, serverHealthCheckReq);
        if(updatedHealthCheckInfo == null){
            return new ResponseEntity<ServerHealthCheck>(updatedHealthCheckInfo, HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<ServerHealthCheck>(updatedHealthCheckInfo, HttpStatus.OK);
        }
    }


}

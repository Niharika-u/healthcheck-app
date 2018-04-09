package com.example.healthcheckapp.controllers;

import com.example.healthcheckapp.services.HealthCheckService;
import com.example.healthcheckapp.services.HealthCheckStatusService;
import com.example.healthcheckapp.services.LdapLoginService;
import com.example.healthcheckapp.services.dao.models.ServerHealthCheck;
import com.example.healthcheckapp.util.DateIncrementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Created by 212583997 on 3/3/2018.
 */
@RestController
@EnableScheduling
@RequestMapping("/hcheck")
@CrossOrigin("*")
public class HealthCheckController {

    private Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

    @Autowired
    private HealthCheckService healthCheckService;

    @Autowired
    private HealthCheckStatusService healthCheckStatusService;

    @Autowired
    private LdapLoginService ldapLoginService;

    @GetMapping(value="/filterhc/{env}/{projectName}")
    public List<ServerHealthCheck> getAllHealthchecksByEnv(@PathVariable("env") String env, @PathVariable("projectName") String projectFilter) {
        return healthCheckService.getAllHealthchecksByFilter(env,projectFilter);
    }

    @GetMapping(value="/id/{hcheckId}")
    public ServerHealthCheck getHealthCheckForAnHealthCheckId(@PathVariable("hcheckId") String hcheckId) {
        return healthCheckService.findHealthCheckById(hcheckId);
    }

    @Scheduled(fixedDelayString = "${server.pollingrate}")
    public void updateTableForHealthcheckStatus() {
        healthCheckService.updateLiveHealthCheckStatus();
    }

    @Scheduled(fixedDelayString = "${owner.mailing.rate}")
    public void sendMailToHealthCheckOwner() {
        healthCheckService.sendNotificationForServerFailures();
    }

    @Scheduled(cron = "${dailing.mailing.cron}")
    public void sendDailyMailNotificationToHealthCheckOwners() {
        healthCheckService.sendDailyNotificationForServerFailures();
    }

    @PostMapping(value="/add")
    public ServerHealthCheck addServerHealthCheck(@Valid @RequestBody ServerHealthCheck serverHealthCheck) {
        //  Setting the server status as false at the time of addition as a default mechanism
        logger.info("HealthCheck Addition Request: "+serverHealthCheck.toString());
        //if(serverHealthCheck.getServerStatus() == null)
            serverHealthCheck.setServerStatus(false);
        //if(serverHealthCheck.getNotificationSentStatus() == null)
            serverHealthCheck.setNotificationSentStatus(false);
        //  Setting the value of CountOfDaysForBlockingNotification based on if user has opted for Daily Alerts
        if(serverHealthCheck.getDailyNotificationStatus() == true){
            serverHealthCheck.setCountOfDaysForBlockingNotification("0");
            serverHealthCheck.setTimeOfUpdatingDailyNotificationPreference(DateIncrementor.incrementDate(new Date(), 1));
        }
        if(serverHealthCheck.getDailyNotificationStatus() == false){
            serverHealthCheck.setTimeOfUpdatingDailyNotificationPreference(DateIncrementor.incrementDate(new Date(), Integer.parseInt(serverHealthCheck.getCountOfDaysForBlockingNotification())));
        }

        return healthCheckService.saveHealthChecks(serverHealthCheck);
    }

    @DeleteMapping(value="/delete/{healthCheckId}")
    public void deleteHealthcheck(@PathVariable("healthCheckId") String healthCheckId) {
        healthCheckService.deleteHealthCheckById(healthCheckId);
    }

    @PutMapping(value="/{hcid}")
    public ResponseEntity<ServerHealthCheck> updateHealthcheckData(@PathVariable("hcid") String hcid, @Valid @RequestBody ServerHealthCheck serverHealthCheckReq) {
        logger.info("Update HealthCheck Request" + serverHealthCheckReq.toString());
        ServerHealthCheck updatedHealthCheckInfo = healthCheckService.updateHealthCheck(hcid, serverHealthCheckReq);
        if(updatedHealthCheckInfo == null){
            return new ResponseEntity<ServerHealthCheck>(updatedHealthCheckInfo, HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<ServerHealthCheck>(updatedHealthCheckInfo, HttpStatus.OK);
        }
    }
}

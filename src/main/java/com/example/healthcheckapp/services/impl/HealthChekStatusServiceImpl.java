package com.example.healthcheckapp.services.impl;

import com.example.healthcheckapp.models.ServerHealthCheck;
import com.example.healthcheckapp.services.HealthCheckStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.healthcheckapp.util.RESTHandler;

/**
 * Created By MMT6540 on 14 Mar, 2018
 */
@Service
@Transactional
public class HealthChekStatusServiceImpl extends RESTHandler implements HealthCheckStatusService {

    @Override
    public Boolean updateServerStatusForHealthChecks(ServerHealthCheck healthCheck) {
        String healthCheckIP = healthCheck.getIpAddress();
        String healthCheckPort = healthCheck.getHealthCheckPort();
        String healthCheckURL = healthCheck.getHealthCheckUrl();
        if(healthCheckURL.substring(0,1).contains("/"))
            healthCheckURL = healthCheckURL.substring(1,healthCheckURL.length()-1);
        String apiUrl = "http://" + healthCheckIP + ":" + healthCheckPort + "/" + healthCheckURL;
        String response = getResponse(RequestType.GET, getHeader(), apiUrl, "", 200);
        //String request
        if(response == null)
            return false;
        else if (response.toLowerCase().contains("true") || response.toLowerCase().contains("ok"))
            return true;

        return false;
    }
}

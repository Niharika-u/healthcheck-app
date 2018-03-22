package com.example.healthcheckapp.services;

import com.example.healthcheckapp.services.dao.models.ServerHealthCheck;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.healthcheckapp.util.RESTHandler;

/**
 * Created By MMT6540 on 14 Mar, 2018
 */
@Service
public class HealthCheckStatusService extends RESTHandler {

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
        else if (response.toLowerCase().contains("true") || response.toLowerCase().contains("ok") || response.toLowerCase().contains("up"))
            return true;

        return false;
    }
}

package com.example.healthcheckapp.services;

import com.example.healthcheckapp.services.dao.models.ServerHealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.healthcheckapp.util.RESTHandler;

import java.util.Map;

/**
 * Created By MMT6540 on 14 Mar, 2018
 */
@Service
public class HealthCheckStatusService extends RESTHandler {

    Map<Integer, String> responseCodeAndContentMap;
    int responseCode;
    String responseContent;
    private Logger logger = LoggerFactory.getLogger(HealthCheckStatusService.class);

    public Boolean updateServerStatusForHealthChecks(ServerHealthCheck healthCheck) {
        String healthCheckIP = healthCheck.getIpAddress();
        String healthCheckPort = healthCheck.getHealthCheckPort();
        String healthCheckURL = healthCheck.getHealthCheckUrl();
        if (healthCheckURL.substring(0, 1).contains("/"))
            healthCheckURL = healthCheckURL.substring(1, healthCheckURL.length() - 1);
        String apiUrl = "http://" + healthCheckIP + ":" + healthCheckPort + "/" + healthCheckURL;

        responseCodeAndContentMap = getResponse(RequestType.GET, getHeader(), apiUrl, "", 200);
        logger.info("RequestURL: " + apiUrl);
        Map.Entry<Integer, String> entrySet = responseCodeAndContentMap.entrySet().iterator().next();
        responseCode = entrySet.getKey();
        responseContent = entrySet.getValue();

        //  Lowering the case of the response
        responseContent = responseContent.toLowerCase();

        if (responseCode != 200)
            return false;
        else
            return true;
            /*if (responseCode == 200 || responseContent.contains("true") || responseContent.contains("ok") || responseContent.contains("up") || responseContent.contains("personal"))*/

    }
}

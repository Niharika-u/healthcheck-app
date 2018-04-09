package com.example.healthcheckapp.services;

import com.example.healthcheckapp.pojo.EmailContentPojo;
import com.example.healthcheckapp.pojo.SendEmailToHealthCheckOwnerPojo;
import com.example.healthcheckapp.services.dao.models.ServerHealthCheck;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${owner.mailing.server}")
    private String mailingIp;

    @Value("${owner.mailing.endpoint}")
    private String mailingEndPoint;

    @Value("${owner.mailing.cc}")
    private String mailingCcList;

    @Value("${owner.mailing.from}")
    private String mailingFromId;

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
        if(responseCodeAndContentMap == null)
            return false;
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

    public boolean sendNotificationForApplicationDowntime(ServerHealthCheck healthCheck){

        String apiUrl = "http://" + mailingIp + "/" + mailingEndPoint;
        SendEmailToHealthCheckOwnerPojo sendEmailToHealthCheckOwnerPojo = new SendEmailToHealthCheckOwnerPojo();
        sendEmailToHealthCheckOwnerPojo.setTo(healthCheck.getEmailIdOfCreatedBy());
        sendEmailToHealthCheckOwnerPojo.setFrom(mailingFromId);
        sendEmailToHealthCheckOwnerPojo.setCc(mailingCcList);
        sendEmailToHealthCheckOwnerPojo.setSubject("Health Check @"+healthCheck.getIpAddress()+": "+healthCheck.getComponentName()+" is down!");
        sendEmailToHealthCheckOwnerPojo.setTriggerName("simple_email");
        EmailContentPojo emailContent = new EmailContentPojo();
        emailContent.setText("<html>HI </br>" +
                "\t The Application Server needs your attention </br></br>" +
                "\t APPLICATION NAME: "+healthCheck.getComponentName()+"</br>" +
                "\t APPLICATION IP: "+healthCheck.getIpAddress()+"</br>" +
                "\t APPLICATION PORT: "+healthCheck.getApplicationPort()+"</br>" +
                "\t TEST ENV: "+healthCheck.getEnvName()+"</br>" +
                "\t DOWNTIME SINCE: "+healthCheck.getHealthCheckUpdatedAt()+"</br></br>" +
                "Thank You!</html>");
        sendEmailToHealthCheckOwnerPojo.setContent(emailContent);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String requestBody = gson.toJson(sendEmailToHealthCheckOwnerPojo).toString();
        setHeader();
        responseCodeAndContentMap = getResponse(RequestType.POST, getHeader(), apiUrl, requestBody, 200);
        logger.info("RequestURL: " + apiUrl);
        if(responseCodeAndContentMap == null)
            return false;
        Map.Entry<Integer, String> entrySet = responseCodeAndContentMap.entrySet().iterator().next();
        responseCode = entrySet.getKey();
        responseContent = entrySet.getValue();

        //  Lowering the case of the response
        responseContent = responseContent.toLowerCase();

        if (responseCode != 200)
            return false;
        else
            return true;
    }
}

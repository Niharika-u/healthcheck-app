package com.example.healthcheckapp.services;

import com.example.healthcheckapp.services.dao.models.ServerHealthCheck;
import com.example.healthcheckapp.services.dao.repos.HealthCheckRepository;
import com.example.healthcheckapp.util.RESTHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created By MMT6540 on 03 Apr, 2018
 */
@Service
public class LdapLoginService extends RESTHandler {

    @Autowired
    HealthCheckRepository healthCheckRepository;

    private Logger logger = LoggerFactory.getLogger(LdapLoginService.class);



}
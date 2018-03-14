package com.example.healthcheckapp.services.impl;

import com.example.healthcheckapp.controllers.HealthCheckController;
import com.example.healthcheckapp.models.ServerHealthCheck;
import com.example.healthcheckapp.services.HealthCheckStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created By MMT6540 on 14 Mar, 2018
 */
@Service
@Transactional
public class HealthChekStatusServiceImpl implements HealthCheckStatusService{

    @Autowired
    private HealthCheckController hcController;

    @Override
    public void updateServerStatusForHealthChecks(List<ServerHealthCheck> healthCheckList) {

    }
}

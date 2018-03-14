package com.example.healthcheckapp.services;

import com.example.healthcheckapp.models.ServerHealthCheck;

import java.util.List;

/**
 * Created By MMT6540 on 14 Mar, 2018
 */
public interface HealthCheckStatusService {

    //  Server health-checks need to be updated
    public Boolean updateServerStatusForHealthChecks(ServerHealthCheck healthCheckList);
}

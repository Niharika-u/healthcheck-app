package com.example.healthcheckapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created By MMT6540 on 20 Mar, 2018
 */

public class HealthCheckConfig {
    @Value("${server.pollingrate}")
    private String pollingRate;
}

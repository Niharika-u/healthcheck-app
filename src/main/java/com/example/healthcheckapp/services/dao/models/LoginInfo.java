package com.example.healthcheckapp.services.dao.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

/**
 * Created By MMT6540 on 03 Apr, 2018
 */
public class LoginInfo {

    @NotBlank
    private String mmtId;

    @NotBlank
    private String mmtEmailId;

}
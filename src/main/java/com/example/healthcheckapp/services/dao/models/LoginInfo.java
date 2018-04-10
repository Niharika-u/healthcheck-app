package com.example.healthcheckapp.services.dao.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created By MMT6540 on 09 Apr, 2018
 */
public class LoginInfo {

    @NotBlank
    private String mmtId;

    @NotBlank
    private String password;

    public String getMmtId() {
        return mmtId;
    }

    public void setMmtId(String mmtId) {
        this.mmtId = mmtId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
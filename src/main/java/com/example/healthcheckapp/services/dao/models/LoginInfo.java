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
    private String mmtPassword;

    public String getMmtId() {
        return mmtId;
    }

    public void setMmtId(String mmtId) {
        this.mmtId = mmtId;
    }

   /* public String getMmtPassword() {
        return mmtPassword;
    }*/

    public void setMmtPassword(String mmtPassword) {
        this.mmtPassword = mmtPassword;
    }
}
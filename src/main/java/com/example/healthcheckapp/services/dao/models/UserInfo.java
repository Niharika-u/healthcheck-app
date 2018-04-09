package com.example.healthcheckapp.services.dao.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created By MMT6540 on 03 Apr, 2018
 */
public class UserInfo {
    @Id
    private String mmtId;

    @NotBlank
    @Indexed(unique = true)
    private String emailId;

    public String getPaswd() {
        return paswd;
    }

    public void setPaswd(String paswd) {
        this.paswd = paswd;
    }

    @NotBlank
    private String paswd;

    public String getMmtId() {
        return mmtId;
    }

    public void setMmtId(String mmtId) {
        this.mmtId = mmtId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
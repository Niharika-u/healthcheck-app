package com.example.healthcheckapp.services.dao.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * Created By MMT6540 on 09 Apr, 2018
 */
@Document(collection="userdetailstbl")
public class RegistrationOfUser {

    @Id
    @Indexed(unique=true)
    @NotBlank
    private String mmtId;

    @Indexed(unique=true)
    @NotBlank
    private String mmtEMailId;

    @Indexed(unique=true)
    @NotBlank
    private String mmtGroupEMailId;

    private String mmtPassword;

    public String getMmtId() {
        return mmtId;
    }

    public void setMmtId(String mmtId) {
        this.mmtId = mmtId;
    }

    public String getMmtEMailId() {
        return mmtEMailId;
    }

    public void setMmtEMailId(String mmtEMailId) {
        this.mmtEMailId = mmtEMailId;
    }

    public String getMmtGroupEMailId() {
        return mmtGroupEMailId;
    }

    public void setMmtGroupEMailId(String mmtGroupEMailId) {
        this.mmtGroupEMailId = mmtGroupEMailId;
    }

    /*public String getMmtPassword() {
        return mmtPassword;
    }*/

    public void setMmtPassword(String mmtPassword) {
        this.mmtPassword = mmtPassword;
    }
}
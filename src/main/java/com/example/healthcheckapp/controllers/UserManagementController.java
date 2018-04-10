package com.example.healthcheckapp.controllers;

import com.example.healthcheckapp.services.LdapLoginService;
import com.example.healthcheckapp.services.UserRegistrationService;
import com.example.healthcheckapp.services.dao.models.LoginInfo;
import com.example.healthcheckapp.services.dao.models.UserInfo;
import jdk.nashorn.internal.runtime.options.LoggingOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

/**
 * Created By MMT6540 on 09 Apr, 2018
 */
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserManagementController {

    private Logger logger = LoggerFactory.getLogger(UserManagementController.class);

    @Autowired
    UserRegistrationService userRegistrationService;

    @Autowired
    LdapLoginService ldapLoginService;

    @PostMapping("/register")
    public UserInfo addNewUser(@Valid @RequestBody UserInfo newUserInfo){
        logger.info(newUserInfo.toString());
        return userRegistrationService.registerNewUser(newUserInfo);
    }

    @PostMapping("/login")
    public UserInfo loginUser(@Valid @RequestBody LoginInfo loginInfo){
        return ldapLoginService.authenticateUserAndReturnUserInfo(loginInfo);
    }



}
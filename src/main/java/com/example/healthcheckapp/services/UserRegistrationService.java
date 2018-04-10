package com.example.healthcheckapp.services;

import com.example.healthcheckapp.services.dao.models.UserInfo;
import com.example.healthcheckapp.services.dao.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created By MMT6540 on 09 Apr, 2018
 */
@Service
public class UserRegistrationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LdapLoginService ldapLoginService;

    public UserInfo registerNewUser(UserInfo userInfo){
        String userName = userInfo.getMmtId();
        String password = userInfo.getMmtPassword();

        if(ldapLoginService.authenticateUserWhileRegistration(userName, password)) {
            return userRepository.save(userInfo);
        }
        return null;
    }
}
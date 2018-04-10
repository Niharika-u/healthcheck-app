package com.example.healthcheckapp.services;

import com.example.healthcheckapp.exception.UnauthorizedException;
import com.example.healthcheckapp.services.dao.models.LoginInfo;
import com.example.healthcheckapp.services.dao.repos.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;

import com.example.healthcheckapp.services.dao.models.UserInfo;
import com.example.healthcheckapp.services.dao.repos.HealthCheckRepository;
import com.example.healthcheckapp.util.RESTHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created By MMT6540 on 03 Apr, 2018
 */
@Service
public class LdapLoginService extends RESTHandler {

    @Value("${auth.groups.allowed}")
    private String allowedGroups;

    @Value("${error.auth.message}")
    private String unauthMessage;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    Query query;

    private Logger logger = LoggerFactory.getLogger(LdapLoginService.class);

    public UserInfo authenticateUserAndReturnUserInfo(LoginInfo loginInfo) {
        String userId = loginInfo.getMmtId();
        String password = loginInfo.getPassword();

        logger.debug("Authenticating User: "+userId);
        if(mmtAuthentication(userId, password)){
            query = new Query();
            query.addCriteria(Criteria.where("mmtId").is(userId));
            UserInfo userInfoData = mongoTemplate.findOne(query, UserInfo.class);

            if(userInfoData == null){
                return null;
            }else{
                return userInfoData;
            }
        }else{
            return null;
        }
    }

    public boolean authenticateUserWhileRegistration(String userName, String password) {
        return mmtAuthentication(userName, password);
    }

    private boolean mmtAuthentication(String userName, String password) throws UnauthorizedException {
        try{
            LdapContextSource contextSource = new LdapContextSource();
            contextSource.setUrl("ldap://mmt-srv-dc.mmt.com");
            contextSource.setBase("dc=mmt,dc=com");
            contextSource.setUserDn("mmt\\" + userName);
            contextSource.setPassword(password);
            contextSource.afterPropertiesSet();

            LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
            ldapTemplate.afterPropertiesSet();

            Filter filter = new EqualsFilter("sAMAccountName", userName);
            String[] allowedGroups = this.allowedGroups.split(",");
            boolean authed = false;
            for (String group : allowedGroups) {
                authed |= ldapTemplate.authenticate("ou=" + group, filter.encode(), password);
                if (authed) {
                    break;
                }
            }
            if (authed) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            throw new UnauthorizedException(unauthMessage, ex);
        }
    }

}
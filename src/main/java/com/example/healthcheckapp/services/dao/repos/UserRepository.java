package com.example.healthcheckapp.services.dao.repos;

import com.example.healthcheckapp.services.dao.models.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created By MMT6540 on 09 Apr, 2018
 */
@Repository
public interface UserRepository extends MongoRepository<UserInfo, String> {
}
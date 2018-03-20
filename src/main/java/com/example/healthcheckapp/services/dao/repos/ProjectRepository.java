package com.example.healthcheckapp.services.dao.repos;

import com.example.healthcheckapp.services.dao.models.ProjectInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created By MMT6540 on 02 Mar, 2018
 */
@Repository
public interface ProjectRepository extends MongoRepository<ProjectInfo, String> {
}

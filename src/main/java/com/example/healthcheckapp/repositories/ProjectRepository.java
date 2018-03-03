package com.example.healthcheckapp.repositories;

import com.example.healthcheckapp.models.ProjectInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created By MMT6540 on 02 Mar, 2018
 */
public interface ProjectRepository extends MongoRepository<ProjectInfo, String> {
}

package com.developersuraj.MyAgent.repository;

import com.developersuraj.MyAgent.model.UserProfile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepository extends MongoRepository<UserProfile, ObjectId> {

    UserProfile findByUserName(String name);

    void deleteByUserName(String name);

}

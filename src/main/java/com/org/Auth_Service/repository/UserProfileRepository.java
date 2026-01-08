package com.org.Auth_Service.repository;

import com.org.Auth_Service.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepository extends MongoRepository<UserProfile, String> {

    UserProfile findByUserId(Long userId);
}

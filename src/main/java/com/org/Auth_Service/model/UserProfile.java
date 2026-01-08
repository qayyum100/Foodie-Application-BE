package com.org.Auth_Service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user_profiles")
public class UserProfile {

    @Id
    private String id;

    private Long userId;     // matches MySQL user ID
    private String name;
    private String email;

    public UserProfile() {}

    public UserProfile(Long userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}

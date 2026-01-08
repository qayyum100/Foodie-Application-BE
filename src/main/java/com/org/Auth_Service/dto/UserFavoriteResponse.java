package com.org.Auth_Service.dto;

import com.org.Auth_Service.model.Favorite;
import lombok.Data;

import java.util.List;


@Data
public class UserFavoriteResponse {
    private Long userId;
    private String name;
    private String email;
    private List<Favorite> favorites;

    public UserFavoriteResponse(Long userId, String name, String email, List<Favorite> favorites) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.favorites = favorites;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }
}

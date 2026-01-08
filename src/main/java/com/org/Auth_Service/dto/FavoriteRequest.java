package com.org.Auth_Service.dto;

import com.org.Auth_Service.model.Favorite;

import java.util.List;

public class FavoriteRequest {
    private String restaurantId;

    public FavoriteRequest() {}

    public FavoriteRequest(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}

package com.org.Auth_Service.service;

import com.org.Auth_Service.dto.FavoriteRequest;
import com.org.Auth_Service.dto.UserFavoriteResponse;
import com.org.Auth_Service.model.Favorite;
import com.org.Auth_Service.model.User;
import com.org.Auth_Service.repository.FavoriteRepository;
import com.org.Auth_Service.repository.UserRepository;
import com.org.Auth_Service.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    FavoriteRepository repo;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepo;

    //Add Favorite
    public Favorite addFavorite(String token, FavoriteRequest req) {

        Claims claims = jwtUtil.extractClaims(token);
        Long userId = claims.get("id", Long.class);

        Favorite fav = new Favorite();
        fav.setUserId(userId);
        fav.setRestaurantId(req.getRestaurantId());

        return repo.save(fav);
    }

    //Get All Favorites for Logged in user
    public UserFavoriteResponse getUserFavorites(String token) {

        Claims claims = jwtUtil.extractClaims(token);
        Long userId = claims.get("id", Long.class);

        User user = userRepo.findById(userId).get();
        List<Favorite> favorites = repo.findByUserId(userId);

        return new UserFavoriteResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                favorites
        );
    }

    //Remove a Favorite
    public boolean removeFavorite(String token, String restaurantId){
        Claims claims = jwtUtil.extractClaims(token);
        Long userId = claims.get("id", Long.class);

        Favorite fav = repo.findByUserIdAndRestaurantId(userId, restaurantId);
        if (fav == null) return false;
        repo.delete(fav);
        return true;
    }

    //Check if Favorite Exists
    public boolean isFavorite(String token, String restaurantId) {

        Claims claims = jwtUtil.extractClaims(token);
        Long userId = claims.get("id", Long.class);

        Favorite fav = repo.findByUserIdAndRestaurantId(userId, restaurantId);
        return fav != null;
    }

    //Clear all favorites for a user
    public void clearFavorites(String token) {
        Claims claims = jwtUtil.extractClaims(token);
        Long userId = claims.get("id", Long.class);

        repo.deleteByUserId(userId);
    }

    //Count favorites of a restaurant
    public long countFavoritesOfRestaurant(String restaurantId) {
        return repo.countByRestaurantId(restaurantId);
    }

}


package com.org.Auth_Service.repository;

import com.org.Auth_Service.model.Favorite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends MongoRepository<Favorite, String> {

    List<Favorite> findByUserId(Long userId);
    Favorite findByUserIdAndRestaurantId(Long userId, String restaurantId);

    void deleteByUserId(Long userId);

    long countByRestaurantId(String restaurantId);
}


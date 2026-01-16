package com.org.Auth_Service.repository;

import com.org.Auth_Service.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating, String> {

    List<Rating> findByRestaurantId(String restaurantId);

    Rating findByUserIdAndRestaurantId(Long userId, String restaurantId);

    long countByRestaurantId(String restaurantId);
}

package com.org.Auth_Service.repository;

import com.org.Auth_Service.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findByRestaurantId(String restaurantId);

    Review findByUserIdAndRestaurantId(Long userId, String restaurantId);

    long countByRestaurantId(String restaurantId);

}

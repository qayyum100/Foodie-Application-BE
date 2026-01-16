package com.org.Auth_Service.service;

import com.org.Auth_Service.model.Rating;
import com.org.Auth_Service.repository.RatingRepository;
import com.org.Auth_Service.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepo;

    @Autowired
    private JwtUtil jwtUtil;

    public Rating submitRating(String token, String restaurantId, int rating, String comment) {

        Claims claims = jwtUtil.extractClaims(token);
        Long userId = claims.get("id", Long.class);

        // If user has already rated this restaurant, update rating
        Rating existing = ratingRepo.findByUserIdAndRestaurantId(userId, restaurantId);

        if (existing != null) {
            existing.setRating(rating);
            existing.setComment(comment);
            return ratingRepo.save(existing);
        }

        Rating newRating = new Rating(userId, restaurantId, rating, comment);
        return ratingRepo.save(newRating);
    }

    public double getAverageRating(String restaurantId) {
        List<Rating> list = ratingRepo.findByRestaurantId(restaurantId);

        if (list.isEmpty()) return 0;

        double sum = list.stream().mapToInt(Rating::getRating).sum();
        return sum / list.size();
    }

    public List<Rating> getRestaurantRatings(String restaurantId) {
        return ratingRepo.findByRestaurantId(restaurantId);
    }

    // average rating
    public double getAvg(String restId) {
        List<Rating> ratings = ratingRepo.findByRestaurantId(restId);
        if (ratings.isEmpty()) return 0.0;

        double sum = ratings.stream()
                .mapToDouble(Rating::getRating)
                .sum();

        return sum / ratings.size();
    }

    // count reviews
    public long getCount(String restId) {
        return ratingRepo.countByRestaurantId(restId);
    }
}

package com.org.Auth_Service.service;
import com.org.Auth_Service.model.Review;
import com.org.Auth_Service.repository.ReviewRepository;
import com.org.Auth_Service.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    public Review submitReview(String token, String restaurantId, int rating, String comment) {
        Claims claims = jwtUtil.extractClaims(token);
        Long userId = claims.get("id", Long.class);

        // If user already reviewed → update existing one
        Review existing = repo.findByUserIdAndRestaurantId(userId, restaurantId);

        if (existing != null) {
            existing.setRating(rating);
            existing.setComment(comment);
            return repo.save(existing);
        }

        Review r = new Review(userId, restaurantId, rating, comment);
        return repo.save(r);
    }



    public List<Review> getReviews(String restaurantId) {
        return repo.findByRestaurantId(restaurantId);
    }

    public long countReviews(String restaurantId) {
        return repo.countByRestaurantId(restaurantId);
    }

}

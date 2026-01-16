package com.org.Auth_Service.controller;

import com.org.Auth_Service.model.Rating;
import com.org.Auth_Service.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/add")
    public ResponseEntity<?> submitRating(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam String restaurantId,
            @RequestParam int rating,
            @RequestParam(required = false) String comment
    ) {
        String token = authHeader.substring(7);
        Rating r = ratingService.submitRating(token, restaurantId, rating, comment);
        return ResponseEntity.ok(r);
    }

    @GetMapping("/avg/{restaurantId}")
    public ResponseEntity<?> getAverage(@PathVariable String restaurantId) {
        double avg = ratingService.getAverageRating(restaurantId);
        return ResponseEntity.ok(avg);
    }

    @GetMapping("/all/{restaurantId}")
    public ResponseEntity<?> getRatings(@PathVariable String restaurantId) {
        return ResponseEntity.ok(ratingService.getRestaurantRatings(restaurantId));
    }



    @GetMapping("/count/{restaurantId}")
    public ResponseEntity<?> countReviews(@PathVariable String restaurantId){
        long count = ratingService.getCount(restaurantId);
        return ResponseEntity.ok(count);
    }
}

package com.org.Auth_Service.controller;

import com.org.Auth_Service.model.Review;
import com.org.Auth_Service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<?> addReview(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam String restaurantId,
            @RequestParam int rating,
            @RequestParam String comment) {

        String token = authHeader.substring(7);
        Review r = reviewService.submitReview(token, restaurantId, rating, comment);
        return ResponseEntity.ok(r);
    }

    @GetMapping("/get/{restaurantId}")
    public ResponseEntity<?> getReviews(@PathVariable String restaurantId) {
        return ResponseEntity.ok(reviewService.getReviews(restaurantId));
    }

    @GetMapping("/count/{restaurantId}")
    public ResponseEntity<?> countReviews(@PathVariable String restaurantId) {
        long count = reviewService.countReviews(restaurantId);
        return ResponseEntity.ok(count);
    }

}

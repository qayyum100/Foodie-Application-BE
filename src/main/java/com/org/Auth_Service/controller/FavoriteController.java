package com.org.Auth_Service.controller;

import com.org.Auth_Service.dto.FavoriteRequest;
import com.org.Auth_Service.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {
    @Autowired
    FavoriteService service;

    //Add Favorite
    @PostMapping("/add")
    public ResponseEntity<?> addFav(@RequestHeader("Authorization") String authHeader,
                                    @RequestBody FavoriteRequest req) {

        String token = authHeader.substring(7);
        return ResponseEntity.ok(service.addFavorite(token, req));
    }

    //Get My Favorites
    @GetMapping("/me")
    public ResponseEntity<?> getMyFavorites(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        return ResponseEntity.ok(service.getUserFavorites(token));
    }

    //Remove Favorite
    @DeleteMapping("/remove/{restaurantId}")
    public ResponseEntity<?> removeFavorite(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable String restaurantId){
        String token = authHeader.substring(7);
        boolean removed = service.removeFavorite(token,restaurantId);
        return removed
                ? ResponseEntity.ok("Favorite removed successfully!")
                : ResponseEntity.status(404).body("Favorite not found!");
    }

    //Check If Favorite Exists
    @GetMapping("/check/{restaurantId}")
    public ResponseEntity<?> isFavorite(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable String restaurantId) {

        String token = authHeader.substring(7);
        boolean exists = service.isFavorite(token, restaurantId);

        return ResponseEntity.ok(exists);
    }

    //Remove All Favorites for a User
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearFavorites(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        service.clearFavorites(token);

        return ResponseEntity.ok("All favorites removed for the user.");
    }

    //Count Favorites for a Restaurant
    @GetMapping("/count/{restaurantId}")
    public ResponseEntity<?> countFavorites(@PathVariable String restaurantId) {

        long count = service.countFavoritesOfRestaurant(restaurantId);
        return ResponseEntity.ok(count);
    }
}

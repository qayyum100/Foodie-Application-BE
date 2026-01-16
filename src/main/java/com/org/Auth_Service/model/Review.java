package com.org.Auth_Service.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    private Long userId;
    private String restaurantId;
    private int rating; // 1–5 stars
    private String comment;
    private Date timestamp = new Date();

    public Review() {}

    public Review(Long userId, String restaurantId, int rating, String comment) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = new Date();
    }
}

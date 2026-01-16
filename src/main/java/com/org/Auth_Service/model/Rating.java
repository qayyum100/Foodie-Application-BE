package com.org.Auth_Service.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ratings")
public class Rating {

    @Id
    private String id;

    private Long userId;
    private String restaurantId;
    private int rating;  // 1–5
    private String comment;

    public Rating() {}

    public Rating(Long userId, String restaurantId, int rating, String comment) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.comment = comment;
    }
}

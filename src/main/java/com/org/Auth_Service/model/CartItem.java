package com.org.Auth_Service.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("cart")
public class CartItem {

    @Id
    private String id;

    private Long userId;
    private String restaurantId;
    private String itemId;
    private String itemName;
    private int quantity;
    private double price;

    public CartItem() {}

    public CartItem(Long userId, String restaurantId, String itemId, String itemName, int quantity, double price) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}


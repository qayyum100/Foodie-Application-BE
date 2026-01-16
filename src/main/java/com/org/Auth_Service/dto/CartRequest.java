package com.org.Auth_Service.dto;


import lombok.Data;

@Data
public class CartRequest {
    private String restaurantId;
    private String itemId;
    private String itemName;
    private int quantity;
    private double price;
}

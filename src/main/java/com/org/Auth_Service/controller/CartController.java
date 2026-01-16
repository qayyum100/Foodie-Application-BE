package com.org.Auth_Service.controller;


import com.org.Auth_Service.dto.CartRequest;
import com.org.Auth_Service.model.CartItem;
import com.org.Auth_Service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;



    // Add to cart
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CartRequest req
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(cartService.addToCart(token, req));
    }

    // Get my cart
    @GetMapping("/my")
    public ResponseEntity<?> getMyCart(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(cartService.getMyCart(token));
    }

    // Remove single item
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<?> removeItem(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String itemId
    ) {
        String token = authHeader.substring(7);
        boolean removed = cartService.removeItem(token, itemId);
        return removed ? ResponseEntity.ok("Removed") :
                ResponseEntity.status(404).body("Item not found");
    }

    // Clear all cart items
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        cartService.clearCart(token);
        return ResponseEntity.ok("Cart cleared");
    }


    @PostMapping("/increase/{itemId}")
    public List<CartItem> increaseQty(
            @RequestHeader("Authorization") String auth,
            @PathVariable String itemId
    ) {
        return cartService.increaseQuantity(auth, itemId);
    }

    @PostMapping("/decrease/{itemId}")
    public List<CartItem> decreaseQty(
            @RequestHeader("Authorization") String auth,
            @PathVariable String itemId
    ) {
        return cartService.decreaseQuantity(auth, itemId);
    }


}

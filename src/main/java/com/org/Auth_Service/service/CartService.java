package com.org.Auth_Service.service;


import com.org.Auth_Service.dto.CartRequest;
import com.org.Auth_Service.model.CartItem;
import com.org.Auth_Service.repository.CartRepository;
import com.org.Auth_Service.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepo;

    @Autowired
    JwtUtil jwtUtil;

    public CartItem addToCart(String token, CartRequest req) {

        Claims claims = jwtUtil.extractClaims(token);
        Long userId = claims.get("id", Long.class);

        // If item already exists → update quantity
        CartItem existing = cartRepo.findByUserIdAndItemId(userId, req.getItemId());

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + req.getQuantity());
            return cartRepo.save(existing);
        }

        // else create new cart item
        CartItem item = new CartItem(
                userId,
                req.getRestaurantId(),
                req.getItemId(),
                req.getItemName(),
                req.getQuantity(),
                req.getPrice()
        );

        return cartRepo.save(item);
    }

    public List<CartItem> getMyCart(String token) {
        Claims claims = jwtUtil.extractClaims(token);
        Long userId = claims.get("id", Long.class);
        return cartRepo.findByUserId(userId);
    }

    public boolean removeItem(String token, String itemId) {
        Claims claims = jwtUtil.extractClaims(token);
        Long userId = claims.get("id", Long.class);

        CartItem item = cartRepo.findByUserIdAndItemId(userId, itemId);
        if (item != null) {
            cartRepo.delete(item);
            return true;
        }
        return false;
    }

    public void clearCart(String token) {
        Claims claims = jwtUtil.extractClaims(token);
        Long userId = claims.get("id", Long.class);

        cartRepo.deleteByUserId(userId);
    }



    public List<CartItem> increaseQuantity(String token, String itemId) {
        Long userId = jwtUtil.extractClaims(token.substring(7)).get("id", Long.class);

        CartItem item = cartRepo.findByUserIdAndItemId(userId, itemId);
        item.setQuantity(item.getQuantity() + 1);
        cartRepo.save(item);

        return cartRepo.findByUserId(userId);
    }

    public List<CartItem> decreaseQuantity(String token, String itemId) {
        Long userId = jwtUtil.extractClaims(token.substring(7)).get("id", Long.class);

        CartItem item = cartRepo.findByUserIdAndItemId(userId, itemId);

        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            cartRepo.save(item);
        }

        return cartRepo.findByUserId(userId);
    }




}

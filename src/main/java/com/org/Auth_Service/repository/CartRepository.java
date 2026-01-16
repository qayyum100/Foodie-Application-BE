package com.org.Auth_Service.repository;

import com.org.Auth_Service.model.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartRepository extends MongoRepository<CartItem, String> {

    List<CartItem> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    CartItem findByUserIdAndItemId(Long userId, String itemId);
}

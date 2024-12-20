package com.example.coupons.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.coupons.model.CartItem;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
		
}

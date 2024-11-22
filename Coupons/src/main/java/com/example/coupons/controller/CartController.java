package com.example.coupons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coupons.model.Cart;
import com.example.coupons.model.CartResponse;
import com.example.coupons.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
    @Autowired
    private CartService cartService;

    @PostMapping("/process")
    public ResponseEntity<Double> processCart(@RequestBody Cart cart) {
        Double totalPrice = cartService.calculateCartTotal(cart);

        return ResponseEntity.ok(totalPrice);
    }
    
    @PostMapping("/{id}")
    public ResponseEntity<CartResponse> applyCoupon(@PathVariable Long id, @RequestBody Cart cart) {
        CartResponse updatedCart = cartService.applyCouponToCart(id, cart);
        return ResponseEntity.ok(updatedCart);
    }

}

package com.example.coupons.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.coupons.dao.CouponRepository;
import com.example.coupons.model.BxGyDetails;
import com.example.coupons.model.Cart;
import com.example.coupons.model.CartItem;
import com.example.coupons.model.CartResponse;
import com.example.coupons.model.Coupon;

@Service
public class CartService {

	@Autowired
	CouponRepository couponRepository;

	public Double calculateCartTotal(Cart cart) {
		return cart.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
	}

	public boolean validateCart(Cart cart) {
		for (CartItem item : cart.getItems()) {
			if (item.getProductId() == null || item.getProductId() <= 0) {
				return false;
			}
			if (item.getQuantity() == null || item.getQuantity() <= 0) {
				return false;
			}
			if (item.getPrice() == null || item.getPrice() <= 0) {
				return false;
			}
		}
		return true;
	}

	public CartResponse applyCouponToCart(Long couponId, Cart cart) {
		Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new RuntimeException("Coupon not found"));

		// Variables to track totals
		double totalPrice = 0.0;
		double totalDiscount = 0.0;

		// Apply coupon logic based on type
		switch (coupon.getType()) {
		case CART_WISE:
			totalPrice = applyCartWiseCoupon(cart, coupon, totalDiscount);
			break;

		case PRODUCT_WISE:
			totalPrice = applyProductWiseCoupon(cart, coupon, totalDiscount);
			break;

		case BxGy:
			totalPrice = applyBxGyCoupon(cart, coupon, totalDiscount);
			break;

		default:
			throw new IllegalArgumentException("Unsupported coupon type");
		}

		// Prepare response
		CartResponse response = new CartResponse();
		response.setItems(cart.getItems());
		response.setTotalPrice(totalPrice);
		response.setTotalDiscount(totalDiscount);
		response.setFinalPrice(totalPrice - totalDiscount);
		return response;
	}

	private double applyCartWiseCoupon(Cart cart, Coupon coupon, double totalDiscount) {
		double totalPrice = cart.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();

		if (totalPrice >= coupon.getCartWiseDetails().getThreshold()) {
			double discount = totalPrice * (coupon.getCartWiseDetails().getDiscount() / 100.0);
			totalDiscount += discount;
		}

		return totalPrice;
	}

	private double applyProductWiseCoupon(Cart cart, Coupon coupon, double totalDiscount) {
		double totalPrice = 0.0;

		for (CartItem item : cart.getItems()) {
			totalPrice += item.getPrice() * item.getQuantity();
			if (item.getProductId().equals(coupon.getProductWiseDetails().getProductId())) {
				double discount = item.getPrice() * (coupon.getProductWiseDetails().getDiscount() / 100.0);
				item.setTotalDiscount(discount * item.getQuantity());
				totalDiscount += discount * item.getQuantity();
			}
		}

		return totalPrice;
	}

	private double applyBxGyCoupon(Cart cart, Coupon coupon, double totalDiscount) {
		double totalPrice = 0.0;
		BxGyDetails bxGy = coupon.getBxGyDetails();

		for (CartItem item : cart.getItems()) {
			totalPrice += item.getPrice() * item.getQuantity();
		}

		int applicableSets = Math.min(
				cart.getItems().stream()
						.filter(item -> bxGy.getBuyProducts().stream()
								.anyMatch(buy -> buy.getProductId().equals(item.getProductId())))
						.mapToInt(CartItem::getQuantity).sum() / bxGy.getBuyProducts().get(0).getQuantity(),
				bxGy.getRepetitionLimit());

		for (CartItem item : cart.getItems()) {
			if (bxGy.getGetProducts().stream().anyMatch(get -> get.getProductId().equals(item.getProductId()))) {
				int freeQuantity = applicableSets * bxGy.getGetProducts().get(0).getQuantity();
				item.setQuantity(item.getQuantity() + freeQuantity);
				item.setTotalDiscount(item.getPrice() * freeQuantity);
				totalDiscount += item.getPrice() * freeQuantity;
			}
		}

		return totalPrice;
	}
}

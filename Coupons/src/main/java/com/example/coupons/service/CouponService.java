package com.example.coupons.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.coupons.dao.CouponRepository;
import com.example.coupons.model.Coupon;

import java.util.List;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    public Coupon createCoupon(Coupon coupon) {
        switch (coupon.getType()) {
            case CART_WISE:
                if (coupon.getCartWiseDetails() == null) {
                    throw new IllegalArgumentException("Cart-wise details are required.");
                }
                break;

            case PRODUCT_WISE:
                if (coupon.getProductWiseDetails() == null) {
                    throw new IllegalArgumentException("Product-wise details are required.");
                }
                break;

            case BxGy:
                if (coupon.getBxGyDetails() == null || coupon.getBxGyDetails().getBuyProducts().isEmpty()) {
                    throw new IllegalArgumentException("BxGy details with buy products are required.");
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown coupon type.");
        }

        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Coupon getCouponById(Long id) {
        return couponRepository.findById(id).orElseThrow(() -> new RuntimeException("Coupon not found"));
    }

    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }
}
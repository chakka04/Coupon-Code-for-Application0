package com.example.coupons.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CouponType type;

    @OneToOne(cascade = CascadeType.ALL)
    private CartWiseDetails cartWiseDetails;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductWiseDetails productWiseDetails;

    @OneToOne(cascade = CascadeType.ALL)
    private BxGyDetails bxGyDetails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public CartWiseDetails getCartWiseDetails() {
		return cartWiseDetails;
	}

	public void setCartWiseDetails(CartWiseDetails cartWiseDetails) {
		this.cartWiseDetails = cartWiseDetails;
	}

	public ProductWiseDetails getProductWiseDetails() {
		return productWiseDetails;
	}

	public void setProductWiseDetails(ProductWiseDetails productWiseDetails) {
		this.productWiseDetails = productWiseDetails;
	}

	public BxGyDetails getBxGyDetails() {
		return bxGyDetails;
	}

	public void setBxGyDetails(BxGyDetails bxGyDetails) {
		this.bxGyDetails = bxGyDetails;
	}
    
}
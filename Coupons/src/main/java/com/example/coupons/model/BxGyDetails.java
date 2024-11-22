package com.example.coupons.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class BxGyDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ElementCollection
	@CollectionTable(name = "buy_products", joinColumns = @JoinColumn(name = "coupon_id"))
	private List<ProductQuantity> buyProducts;

	@ElementCollection
	@CollectionTable(name = "get_products", joinColumns = @JoinColumn(name = "coupon_id"))
	private List<ProductQuantity> getProducts;

	private Integer repetitionLimit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ProductQuantity> getBuyProducts() {
		return buyProducts;
	}

	public void setBuyProducts(List<ProductQuantity> buyProducts) {
		this.buyProducts = buyProducts;
	}

	public List<ProductQuantity> getGetProducts() {
		return getProducts;
	}

	public void setGetProducts(List<ProductQuantity> getProducts) {
		this.getProducts = getProducts;
	}

	public Integer getRepetitionLimit() {
		return repetitionLimit;
	}

	public void setRepetitionLimit(Integer repetitionLimit) {
		this.repetitionLimit = repetitionLimit;
	}
}



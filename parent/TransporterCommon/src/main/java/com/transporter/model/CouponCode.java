package com.transporter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coupon_code")
public class CouponCode implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coupon_code_id")
	private int couponCodeId;

	@Column(name = "coupon_code_name")
	private String couponCodeName;

	@Column(name = "coupon_code_discount_percent")
	private double couponCodeDiscountPercent;

	@Column(name = "coupon_code_max_discount")
	private double couponCodeMaxDiscount;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Column(name = "coupon_code_created_at")
	private String couponCodeCreatedAt;

	@Column(name = "coupon_code_updated_at")
	private String couponCodeUpdatedAt;

	@Column(name = "coupon_code")
	private String couponCode;
	
	@Column(name = "coupon_code_expire_date")
	private String couponCodeExpireDate;

	public int getCouponCodeId() {
		return couponCodeId;
	}

	public void setCouponCodeId(int couponCodeId) {
		this.couponCodeId = couponCodeId;
	}

	public String getCouponCodeName() {
		return couponCodeName;
	}

	public void setCouponCodeName(String couponCodeName) {
		this.couponCodeName = couponCodeName;
	}

	public double getCouponCodeDiscountPercent() {
		return couponCodeDiscountPercent;
	}

	public void setCouponCodeDiscountPercent(double couponCodeDiscountPercent) {
		this.couponCodeDiscountPercent = couponCodeDiscountPercent;
	}

	public double getCouponCodeMaxDiscount() {
		return couponCodeMaxDiscount;
	}

	public void setCouponCodeMaxDiscount(double couponCodeMaxDiscount) {
		this.couponCodeMaxDiscount = couponCodeMaxDiscount;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCouponCodeCreatedAt() {
		return couponCodeCreatedAt;
	}

	public void setCouponCodeCreatedAt(String couponCodeCreatedAt) {
		this.couponCodeCreatedAt = couponCodeCreatedAt;
	}

	public String getCouponCodeUpdatedAt() {
		return couponCodeUpdatedAt;
	}

	public void setCouponCodeUpdatedAt(String couponCodeUpdatedAt) {
		this.couponCodeUpdatedAt = couponCodeUpdatedAt;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getCouponCodeExpireDate() {
		return couponCodeExpireDate;
	}

	public void setCouponCodeExpireDate(String couponCodeExpireDate) {
		this.couponCodeExpireDate = couponCodeExpireDate;
	}
	
	

}

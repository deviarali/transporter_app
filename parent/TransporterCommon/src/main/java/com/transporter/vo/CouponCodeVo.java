package com.transporter.vo;

import java.io.Serializable;

public class CouponCodeVo implements Serializable {
	private int couponCodeId;
	private String couponCodeName;
	private double couponCodeDiscountPercent;
	private double couponCodeMaxDiscount;
	private boolean isActive;
	private boolean isDeleted;
	private String couponCodeCreatedAt;
	private String couponCodeUpdatedAt;
	private String couponCode;
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

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
	

	/*
	 * public CouponCodeVo() { super(); }
	 * 
	 * public int getCouponCodeId() { return couponCodeId; }
	 * 
	 * public CouponCodeVo setCouponCodeId(int couponCodeId) { this.couponCodeId =
	 * couponCodeId; return this; }
	 * 
	 * public String getCouponCodeName() { return couponCodeName; }
	 * 
	 * public CouponCodeVo setCouponCodeName(String couponCodeName) {
	 * this.couponCodeName = couponCodeName; return this; }
	 * 
	 * public double getCouponCodeDiscountPercent() { return
	 * couponCodeDiscountPercent; }
	 * 
	 * public CouponCodeVo setCouponCodeDiscountPercent(double
	 * couponCodeDiscountPercent) { this.couponCodeDiscountPercent =
	 * couponCodeDiscountPercent; return this; }
	 * 
	 * public double getCouponCodeMaxDiscount() { return couponCodeMaxDiscount; }
	 * 
	 * public CouponCodeVo setCouponCodeMaxDiscount(double couponCodeMaxDiscount) {
	 * this.couponCodeMaxDiscount = couponCodeMaxDiscount; return this; }
	 * 
	 * public boolean isActive() { return isActive; }
	 * 
	 * public CouponCodeVo setActive(boolean isActive) { this.isActive = isActive;
	 * return this; }
	 * 
	 * public boolean isDeleted() { return isDeleted; }
	 * 
	 * public CouponCodeVo setDeleted(boolean isDeleted) { this.isDeleted =
	 * isDeleted; return this; }
	 * 
	 * public String getCouponCodeCreatedAt() { return couponCodeCreatedAt; }
	 * 
	 * public CouponCodeVo setCouponCodeCreatedAt(String couponCodeCreatedAt) {
	 * this.couponCodeCreatedAt = couponCodeCreatedAt; return this; }
	 * 
	 * public String getCouponCodeUpdatedAt() { return couponCodeUpdatedAt; }
	 * 
	 * public CouponCodeVo setCouponCodeUpdatedAt(String couponCodeUpdatedAt) {
	 * this.couponCodeUpdatedAt = couponCodeUpdatedAt; return this; }
	 * 
	 * public String getCouponCode() { return couponCode; }
	 * 
	 * public CouponCodeVo setCouponCode(String couponCode) { this.couponCode =
	 * couponCode; return this; }
	 */

}

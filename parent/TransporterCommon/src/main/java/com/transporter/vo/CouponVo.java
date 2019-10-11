package com.transporter.vo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

import com.transporter.model.CouponDiscountType;

/**
 * @author SHARAN A
 */
public class CouponVo {

	private Integer id;
	private String couponCode;
	private CouponDiscountType discountType;
	private BigDecimal amountOrPercentage;
	private Boolean isActive;
	private Boolean firstRide;
	private Boolean referral;
	private Calendar startDate;
	private Calendar endDate;
	private Calendar updatedOn;
	private Calendar createdOn;
	private Integer rideNumber;

	Set<UserVo> applyUsers;

	Set<UserVo> exludeUsers;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public CouponDiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(CouponDiscountType discountType) {
		this.discountType = discountType;
	}

	public BigDecimal getAmountOrPercentage() {
		return amountOrPercentage;
	}

	public void setAmountOrPercentage(BigDecimal amountOrPercentage) {
		this.amountOrPercentage = amountOrPercentage;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getFirstRide() {
		return firstRide;
	}

	public void setFirstRide(Boolean firstRide) {
		this.firstRide = firstRide;
	}

	public Boolean getReferral() {
		return referral;
	}

	public void setReferral(Boolean referral) {
		this.referral = referral;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Calendar getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Calendar updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Calendar getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getRideNumber() {
		return rideNumber;
	}

	public void setRideNumber(Integer rideNumber) {
		this.rideNumber = rideNumber;
	}

	public Set<UserVo> getApplyUsers() {
		return applyUsers;
	}

	public void setApplyUsers(Set<UserVo> applyUsers) {
		this.applyUsers = applyUsers;
	}

	public Set<UserVo> getExludeUsers() {
		return exludeUsers;
	}

	public void setExludeUsers(Set<UserVo> exludeUsers) {
		this.exludeUsers = exludeUsers;
	}

}

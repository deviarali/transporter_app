package com.transporter.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author SHARAN A
 */
@Entity
@Table(name = "coupon")
public class Coupon implements Serializable {

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

	Set<User> applyUsers;

	Set<User> exludeUsers;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "coupon_code", nullable = false)
	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "discount_type", nullable = false)
	public CouponDiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(CouponDiscountType discountType) {
		this.discountType = discountType;
	}

	@Column(name = "amount_or_percentage", nullable = false)
	public BigDecimal getAmountOrPercentage() {
		return amountOrPercentage;
	}

	public void setAmountOrPercentage(BigDecimal amountOrPercentage) {
		this.amountOrPercentage = amountOrPercentage;
	}

	@Column(name = "first_ride")
	public Boolean getFirstRide() {
		return firstRide;
	}

	public void setFirstRide(Boolean firstRide) {
		this.firstRide = firstRide;
	}

	@Column(name = "referral")
	public Boolean getReferral() {
		return referral;
	}

	public void setReferral(Boolean referral) {
		this.referral = referral;
	}

	@Column(name = "start_date")
	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	@Column(name = "end_date")
	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	@Column(name = "is_active", nullable = false)
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "updated_on", nullable = false)
	public Calendar getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Calendar updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "created_on", nullable = false)
	public Calendar getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "ride_number")
	public Integer getRideNumber() {
		return rideNumber;
	}

	public void setRideNumber(Integer rideNumber) {
		this.rideNumber = rideNumber;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinTable(name = "coupon_apply_users", joinColumns = { @JoinColumn(name = "coupon_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	public Set<User> getApplyUsers() {
		return applyUsers;
	}

	public void setApplyUsers(Set<User> applyUsers) {
		this.applyUsers = applyUsers;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinTable(name = "coupon_exclude_users", joinColumns = { @JoinColumn(name = "coupon_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	public Set<User> getExludeUsers() {
		return exludeUsers;
	}

	public void setExludeUsers(Set<User> exludeUsers) {
		this.exludeUsers = exludeUsers;
	}

}

package com.transporter.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TripDetailsHistoryVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String amount;

	private String amountToApp;

	private String amountToDriver;

	private String canceledReason;

	private String cancelledamountFromCustomer;

	private String cancelledamountFromDriver;

	private int cancelledamountStatus;

	private String cashMode;

	private String deliverypersonMobile;

	private String deliverypersonName;

	private String destinationLocation;

	private String goodsSize;

	private String goodsType;

	private String pickupLocation;

	private String pickuppersonMobile;

	private String pickuppersonName;

	private String ratings;

	private Date tripEndtime;

	private Date tripStarttime;

	private Date tripTime;

	private String driverName;

	private String vehicleName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmountToApp() {
		return amountToApp;
	}

	public void setAmountToApp(String amountToApp) {
		this.amountToApp = amountToApp;
	}

	public String getAmountToDriver() {
		return amountToDriver;
	}

	public void setAmountToDriver(String amountToDriver) {
		this.amountToDriver = amountToDriver;
	}

	public String getCanceledReason() {
		return canceledReason;
	}

	public void setCanceledReason(String canceledReason) {
		this.canceledReason = canceledReason;
	}

	public String getCancelledamountFromCustomer() {
		return cancelledamountFromCustomer;
	}

	public void setCancelledamountFromCustomer(String cancelledamountFromCustomer) {
		this.cancelledamountFromCustomer = cancelledamountFromCustomer;
	}

	public String getCancelledamountFromDriver() {
		return cancelledamountFromDriver;
	}

	public void setCancelledamountFromDriver(String cancelledamountFromDriver) {
		this.cancelledamountFromDriver = cancelledamountFromDriver;
	}

	public int getCancelledamountStatus() {
		return cancelledamountStatus;
	}

	public void setCancelledamountStatus(int cancelledamountStatus) {
		this.cancelledamountStatus = cancelledamountStatus;
	}

	public String getCashMode() {
		return cashMode;
	}

	public void setCashMode(String cashMode) {
		this.cashMode = cashMode;
	}

	public String getDeliverypersonMobile() {
		return deliverypersonMobile;
	}

	public void setDeliverypersonMobile(String deliverypersonMobile) {
		this.deliverypersonMobile = deliverypersonMobile;
	}

	public String getDeliverypersonName() {
		return deliverypersonName;
	}

	public void setDeliverypersonName(String deliverypersonName) {
		this.deliverypersonName = deliverypersonName;
	}

	public String getDestinationLocation() {
		return destinationLocation;
	}

	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}

	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public String getPickuppersonMobile() {
		return pickuppersonMobile;
	}

	public void setPickuppersonMobile(String pickuppersonMobile) {
		this.pickuppersonMobile = pickuppersonMobile;
	}

	public String getPickuppersonName() {
		return pickuppersonName;
	}

	public void setPickuppersonName(String pickuppersonName) {
		this.pickuppersonName = pickuppersonName;
	}

	public String getRatings() {
		return ratings;
	}

	public void setRatings(String ratings) {
		this.ratings = ratings;
	}

	public Date getTripEndtime() {
		return tripEndtime;
	}

	public void setTripEndtime(Date tripEndtime) {
		this.tripEndtime = tripEndtime;
	}

	public Date getTripStarttime() {
		return tripStarttime;
	}

	public void setTripStarttime(Date tripStarttime) {
		this.tripStarttime = tripStarttime;
	}

	public Date getTripTime() {
		return tripTime;
	}

	public void setTripTime(Date tripTime) {
		this.tripTime = tripTime;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

}
package com.transporter.vo;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the tripdetails database table.
 * 
 */
public class TripDetailsVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String amount;

	private String amountToApp;

	private String amountToDriver;

	private String canceledReason;

	private String cancelledamountFromCustomer;

	private String cancelledamountFromDriver;

	private byte cancelledamountStatus;

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

	private String tripendOtp;

	private String tripstartOtp;

	private DriverDetailsVo driverDetailsVo;

	private CustomerDetailsVo customerDetailsVo;

	private DeliveryStatusVo deliveryStatusVo;

	public TripDetailsVo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmountToApp() {
		return this.amountToApp;
	}

	public void setAmountToApp(String amountToApp) {
		this.amountToApp = amountToApp;
	}

	public String getAmountToDriver() {
		return this.amountToDriver;
	}

	public void setAmountToDriver(String amountToDriver) {
		this.amountToDriver = amountToDriver;
	}

	public String getCanceledReason() {
		return this.canceledReason;
	}

	public void setCanceledReason(String canceledReason) {
		this.canceledReason = canceledReason;
	}

	public String getCancelledamountFromCustomer() {
		return this.cancelledamountFromCustomer;
	}

	public void setCancelledamountFromCustomer(String cancelledamountFromCustomer) {
		this.cancelledamountFromCustomer = cancelledamountFromCustomer;
	}

	public String getCancelledamountFromDriver() {
		return this.cancelledamountFromDriver;
	}

	public void setCancelledamountFromDriver(String cancelledamountFromDriver) {
		this.cancelledamountFromDriver = cancelledamountFromDriver;
	}

	public byte getCancelledamountStatus() {
		return this.cancelledamountStatus;
	}

	public void setCancelledamountStatus(byte cancelledamountStatus) {
		this.cancelledamountStatus = cancelledamountStatus;
	}

	public String getCashMode() {
		return this.cashMode;
	}

	public void setCashMode(String cashMode) {
		this.cashMode = cashMode;
	}

	public String getDeliverypersonMobile() {
		return this.deliverypersonMobile;
	}

	public void setDeliverypersonMobile(String deliverypersonMobile) {
		this.deliverypersonMobile = deliverypersonMobile;
	}

	public String getDeliverypersonName() {
		return this.deliverypersonName;
	}

	public void setDeliverypersonName(String deliverypersonName) {
		this.deliverypersonName = deliverypersonName;
	}

	public String getDestinationLocation() {
		return this.destinationLocation;
	}

	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}

	public String getGoodsSize() {
		return this.goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	public String getGoodsType() {
		return this.goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getPickupLocation() {
		return this.pickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public String getPickuppersonMobile() {
		return this.pickuppersonMobile;
	}

	public void setPickuppersonMobile(String pickuppersonMobile) {
		this.pickuppersonMobile = pickuppersonMobile;
	}

	public String getPickuppersonName() {
		return this.pickuppersonName;
	}

	public void setPickuppersonName(String pickuppersonName) {
		this.pickuppersonName = pickuppersonName;
	}

	public String getRatings() {
		return this.ratings;
	}

	public void setRatings(String ratings) {
		this.ratings = ratings;
	}

	public Date getTripEndtime() {
		return this.tripEndtime;
	}

	public void setTripEndtime(Date tripEndtime) {
		this.tripEndtime = tripEndtime;
	}

	public Date getTripStarttime() {
		return this.tripStarttime;
	}

	public void setTripStarttime(Date tripStarttime) {
		this.tripStarttime = tripStarttime;
	}

	public Date getTripTime() {
		return this.tripTime;
	}

	public void setTripTime(Date tripTime) {
		this.tripTime = tripTime;
	}

	public String getTripendOtp() {
		return this.tripendOtp;
	}

	public void setTripendOtp(String tripendOtp) {
		this.tripendOtp = tripendOtp;
	}

	public String getTripstartOtp() {
		return this.tripstartOtp;
	}

	public void setTripstartOtp(String tripstartOtp) {
		this.tripstartOtp = tripstartOtp;
	}

	public DriverDetailsVo getDriverDetailsVo() {
		return driverDetailsVo;
	}

	public void setDriverDetailsVo(DriverDetailsVo driverDetailsVo) {
		this.driverDetailsVo = driverDetailsVo;
	}

	public CustomerDetailsVo getCustomerDetailsVo() {
		return customerDetailsVo;
	}

	public void setCustomerDetailsVo(CustomerDetailsVo customerDetailsVo) {
		this.customerDetailsVo = customerDetailsVo;
	}

	public DeliveryStatusVo getDeliveryStatusVo() {
		return deliveryStatusVo;
	}

	public void setDeliveryStatusVo(DeliveryStatusVo deliveryStatusVo) {
		this.deliveryStatusVo = deliveryStatusVo;
	}


}
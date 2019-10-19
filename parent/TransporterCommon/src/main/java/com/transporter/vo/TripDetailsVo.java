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

	private String cancelledReason;

	private String cancelledAmountFromCustomer;

	private String cancelledAmountFromDriver;

	private int cancelledAmountStatus;

	private String cashMode;

	private String deliveryPersonMobile;

	private String deliveryPersonName;

	private String destinationLocation;

	private String goodsSize;

	private String goodsType;

	private String sourceLocation;

	private String pickupPersonMobile;

	private String pickupPersonName;

	private String ratings;

	private Date tripEndtime;

	private Date tripStarttime;

	private Date tripTime;

	private String tripEndOtp;

	private String tripStartOtp;

	private DriverDetailsVo driverDetails;

	private CustomerDetailsVo customerDetails;

	private DeliveryStatusVo deliveryStatus;
	
	private int customerId;
	
	private int driverId;
	
	private Double sourceLattitude;
	
	private Double sourceLongitude;
	private Double destinationLattitude;
	private Double destinationLongitude;
	private String sourceLandmark;
	private String destinationLandmark;	
	private String tripStatus;
	private String tripHistoryJson;
	private int vehicleType;
	private TripDetailsHistoryVo tripDetailsHistory;
	private int capacity;
	private double kms;
	private Double cgst;
	private Double sgst;
	private Double rideFare;
	
	public TripDetailsVo() {
	}

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

	public String getCancelledReason() {
		return cancelledReason;
	}

	public void setCancelledReason(String cancelledReason) {
		this.cancelledReason = cancelledReason;
	}

	public String getCancelledAmountFromCustomer() {
		return cancelledAmountFromCustomer;
	}

	public void setCancelledAmountFromCustomer(String cancelledAmountFromCustomer) {
		this.cancelledAmountFromCustomer = cancelledAmountFromCustomer;
	}

	public String getCancelledAmountFromDriver() {
		return cancelledAmountFromDriver;
	}

	public void setCancelledAmountFromDriver(String cancelledAmountFromDriver) {
		this.cancelledAmountFromDriver = cancelledAmountFromDriver;
	}

	public int getCancelledAmountStatus() {
		return cancelledAmountStatus;
	}

	public void setCancelledAmountStatus(int cancelledAmountStatus) {
		this.cancelledAmountStatus = cancelledAmountStatus;
	}

	public String getCashMode() {
		return cashMode;
	}

	public void setCashMode(String cashMode) {
		this.cashMode = cashMode;
	}

	public String getDeliveryPersonMobile() {
		return deliveryPersonMobile;
	}

	public void setDeliveryPersonMobile(String deliveryPersonMobile) {
		this.deliveryPersonMobile = deliveryPersonMobile;
	}

	public String getDeliveryPersonName() {
		return deliveryPersonName;
	}

	public void setDeliveryPersonName(String deliveryPersonName) {
		this.deliveryPersonName = deliveryPersonName;
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
	
	public String getSourceLocation() {
		return sourceLocation;
	}

	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public String getPickupPersonMobile() {
		return pickupPersonMobile;
	}

	public void setPickupPersonMobile(String pickupPersonMobile) {
		this.pickupPersonMobile = pickupPersonMobile;
	}

	public String getPickupPersonName() {
		return pickupPersonName;
	}

	public void setPickupPersonName(String pickupPersonName) {
		this.pickupPersonName = pickupPersonName;
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

	public String getTripEndOtp() {
		return tripEndOtp;
	}

	public void setTripEndOtp(String tripEndOtp) {
		this.tripEndOtp = tripEndOtp;
	}

	public String getTripStartOtp() {
		return tripStartOtp;
	}

	public void setTripStartOtp(String tripStartOtp) {
		this.tripStartOtp = tripStartOtp;
	}

	public DriverDetailsVo getDriverDetails() {
		return driverDetails;
	}

	public void setDriverDetails(DriverDetailsVo driverDetails) {
		this.driverDetails = driverDetails;
	}

	public CustomerDetailsVo getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetailsVo customerDetails) {
		this.customerDetails = customerDetails;
	}

	public DeliveryStatusVo getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(DeliveryStatusVo deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public Double getSourceLattitude() {
		return sourceLattitude;
	}

	public void setSourceLattitude(Double sourceLattitude) {
		this.sourceLattitude = sourceLattitude;
	}

	public Double getSourceLongitude() {
		return sourceLongitude;
	}

	public void setSourceLongitude(Double sourceLongitude) {
		this.sourceLongitude = sourceLongitude;
	}

	public Double getDestinationLattitude() {
		return destinationLattitude;
	}

	public void setDestinationLattitude(Double destinationLattitude) {
		this.destinationLattitude = destinationLattitude;
	}

	public Double getDestinationLongitude() {
		return destinationLongitude;
	}

	public void setDestinationLongitude(Double destinationLongitude) {
		this.destinationLongitude = destinationLongitude;
	}

	public String getSourceLandmark() {
		return sourceLandmark;
	}

	public void setSourceLandmark(String sourceLandmark) {
		this.sourceLandmark = sourceLandmark;
	}

	public String getDestinationLandmark() {
		return destinationLandmark;
	}

	public void setDestinationLandmark(String destinationLandmark) {
		this.destinationLandmark = destinationLandmark;
	}

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}

	public String getTripHistoryJson() {
		return tripHistoryJson;
	}

	public void setTripHistoryJson(String tripHistoryJson) {
		this.tripHistoryJson = tripHistoryJson;
	}

	public int getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}

	public TripDetailsHistoryVo getTripDetailsHistory() {
		return tripDetailsHistory;
	}

	public void setTripDetailsHistory(TripDetailsHistoryVo tripDetailsHistory) {
		this.tripDetailsHistory = tripDetailsHistory;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getKms() {
		return kms;
	}

	public void setKms(double kms) {
		this.kms = kms;
	}

	public Double getCgst() {
		return cgst;
	}

	public void setCgst(Double cgst) {
		this.cgst = cgst;
	}

	public Double getSgst() {
		return sgst;
	}

	public void setSgst(Double sgst) {
		this.sgst = sgst;
	}

	public Double getRideFare() {
		return rideFare;
	}

	public void setRideFare(Double rideFare) {
		this.rideFare = rideFare;
	}
}
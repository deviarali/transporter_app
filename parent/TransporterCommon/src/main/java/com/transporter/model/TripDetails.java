package com.transporter.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.transporter.vo.TripDetailsVo;

/**
 * The persistent class for the tripdetails database table.
 * 
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity
@Table(name = "tripdetails")
public class TripDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "amount")
	private String amount;

	@Column(name = "amount_to_app")
	private String amountToApp;

	@Column(name = "amount_to_driver")
	private String amountToDriver;

	@Column(name = "cancelled_reason")
	private String cancelledReason;

	@Column(name = "cancelledamount_from_customer")
	private String cancelledAmountFromCustomer;

	@Column(name = "cancelledamount_from_driver")
	private String cancelledAmountFromDriver;

	@Column(name = "cancelledamount_status")
	private int cancelledAmountStatus;

	@Column(name = "cash_mode")
	private String cashMode;

	@Column(name = "deliveryperson_mobile")
	private String deliveryPersonMobile;

	@Column(name = "deliveryperson_name")
	private String deliveryPersonName;

	@Column(name = "destination_location")
	private String destinationLocation;

	@Column(name = "goods_size")
	private String goodsSize;

	@Column(name = "goods_type")
	private String goodsType;

	@Column(name = "source_location")
	private String sourceLocation;

	@Column(name = "pickupperson_mobile")
	private String pickupPersonMobile;

	@Column(name = "pickupperson_name")
	private String pickupPersonName;

	@Column(name = "ratings")
	private String ratings;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trip_endtime")
	private Date tripEndtime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trip_starttime")
	private Date tripStarttime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trip_time")
	private Date tripTime;

	@Column(name = "tripend_otp")
	private String tripEndOtp;

	@Column(name = "tripstart_otp")
	private String tripStartOtp;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "driver_id")
	private DriverDetails driverDetails;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private CustomerDetails customerDetails;

	// bi-directional many-to-one association to Deliverystatus
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trip_status")
	private DeliveryStatus deliveryStatus;
	
	@Column(name = "source_lattitude")
	private Double sourceLattitude;
	
	@Column(name = "source_longitude")
	private Double sourceLongitude;
	
	@Column(name = "destination_lattitude")
	private Double destinationLattitude;
	
	@Column(name = "destination_longitude")
	private Double destinationLongitude;
	
	@Column(name = "source_landmark")
	private String sourceLandmark;
	
	@Column(name = "destination_landmark")
	private String destinationLandmark;
	
	@Column(name = "trip_history_json")
	private String tripHistoryJson;
	
	@Column(name = "capacity")
	private int capacity;
	
	@Column(name = "kms")
	private double kms;
	
	@Column(name = "driver_ratings")
	private String driverRatings;
	
	@Column(name = "customer_feedback")
	private String customerFeedback;
	
	@Column(name = "driver_feedback")
	private String driverFeedback;

	public TripDetails() {
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

	public DriverDetails getDriverDetails() {
		return driverDetails;
	}

	public void setDriverDetails(DriverDetails driverDetails) {
		this.driverDetails = driverDetails;
	}

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
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

	public String getTripHistoryJson() {
		return tripHistoryJson;
	}

	public void setTripHistoryJson(String tripHistoryJson) {
		this.tripHistoryJson = tripHistoryJson;
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

	public static TripDetailsVo convertEntityTOVo(TripDetails tripDetails) {
		TripDetailsVo tripDetailsVo = new TripDetailsVo();
		tripDetailsVo.setId(tripDetails.getId());
		tripDetailsVo.setAmount(tripDetails.getAmount());
		tripDetailsVo.setAmountToApp(tripDetails.getAmountToApp());
		tripDetailsVo.setAmountToDriver(tripDetails.getAmountToDriver());
		tripDetailsVo.setCancelledReason(tripDetails.getCancelledReason());
		tripDetailsVo.setCancelledAmountFromCustomer(tripDetails.getCancelledAmountFromCustomer());
		tripDetailsVo.setCancelledAmountFromDriver(tripDetails.getCancelledAmountFromDriver());
		tripDetailsVo.setCancelledAmountStatus(tripDetails.getCancelledAmountStatus());
		tripDetailsVo.setCustomerId(tripDetails.getCustomerDetails().getId());
		tripDetailsVo.setCashMode(tripDetails.getCashMode());
		tripDetailsVo.setDeliveryPersonMobile(tripDetails.getDeliveryPersonMobile());
		tripDetailsVo.setDeliveryPersonName(tripDetails.getDeliveryPersonName());
		tripDetailsVo.setDestinationLocation(tripDetails.getDestinationLocation());
		tripDetailsVo.setDestinationLandmark(tripDetails.getDestinationLandmark());
		tripDetailsVo.setDestinationLattitude(tripDetails.getDestinationLattitude());
		tripDetailsVo.setDestinationLongitude(tripDetails.getDestinationLongitude());
		tripDetailsVo.setGoodsType(tripDetails.getGoodsType());
		tripDetailsVo.setGoodsSize(tripDetails.getGoodsSize());
		tripDetailsVo.setSourceLocation(tripDetails.getSourceLocation());
		tripDetailsVo.setRatings(tripDetails.getRatings());
		tripDetailsVo.setTripTime(tripDetails.getTripTime());
		tripDetailsVo.setTripStarttime(tripDetails.getTripStarttime());
		tripDetailsVo.setTripEndtime(tripDetails.getTripEndtime());
		//tripDetailsVo.setDriverDetails(DriverDetails.convertModelToVo(tripDetails.getDriverDetails()));
		tripDetailsVo.setTripHistoryJson(tripDetails.getTripHistoryJson());
		tripDetailsVo.setTripStatus(tripDetails.getDeliveryStatus().getDeliverystatus());
		tripDetailsVo.setDriverId(tripDetails.getDriverDetails().getId());
		tripDetailsVo.setPickupPersonMobile(tripDetails.getPickupPersonMobile());
		tripDetailsVo.setPickupPersonName(tripDetails.getPickupPersonName());
		tripDetailsVo.setSourceLandmark(tripDetails.getSourceLandmark());
		tripDetailsVo.setSourceLattitude(tripDetails.getSourceLattitude());
		tripDetailsVo.setSourceLongitude(tripDetails.getSourceLongitude());
		tripDetailsVo.setTripStartOtp(tripDetails.getTripStartOtp());
		tripDetailsVo.setTripEndOtp(tripDetails.getTripEndOtp());
		tripDetailsVo.setCapacity(tripDetails.getCapacity());
		tripDetailsVo.setKms(tripDetails.getKms());
		tripDetailsVo.setDriverRatings(tripDetails.getDriverRatings());
		tripDetailsVo.setCustomerFeedback(tripDetails.getCustomerFeedback());
		tripDetailsVo.setDriverFeedback(tripDetails.getDriverFeedback());
		return tripDetailsVo;
	}
	
	public String getDriverRatings() {
		return driverRatings;
	}
	
	public void setDriverRatings(String driverRatings) {
		this.driverRatings = driverRatings;
	}

	public String getCustomerFeedback() {
		return customerFeedback;
	}

	public void setCustomerFeedback(String customerFeedback) {
		this.customerFeedback = customerFeedback;
	}

	public String getDriverFeedback() {
		return driverFeedback;
	}

	public void setDriverFeedback(String driverFeedback) {
		this.driverFeedback = driverFeedback;
	}

}
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

	@Column(name = "canceled_reason")
	private String canceledReason;

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

	@Column(name = "pickup_location")
	private String pickupLocation;

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

	public TripDetails() {
	}

	

	public final int getId() {
		return id;
	}



	public final void setId(int id) {
		this.id = id;
	}



	public final String getAmount() {
		return amount;
	}



	public final void setAmount(String amount) {
		this.amount = amount;
	}



	public final String getAmountToApp() {
		return amountToApp;
	}



	public final void setAmountToApp(String amountToApp) {
		this.amountToApp = amountToApp;
	}



	public final String getAmountToDriver() {
		return amountToDriver;
	}



	public final void setAmountToDriver(String amountToDriver) {
		this.amountToDriver = amountToDriver;
	}



	public final String getCanceledReason() {
		return canceledReason;
	}



	public final void setCanceledReason(String canceledReason) {
		this.canceledReason = canceledReason;
	}



	public final String getCancelledAmountFromCustomer() {
		return cancelledAmountFromCustomer;
	}



	public final void setCancelledAmountFromCustomer(String cancelledAmountFromCustomer) {
		this.cancelledAmountFromCustomer = cancelledAmountFromCustomer;
	}



	public final String getCancelledAmountFromDriver() {
		return cancelledAmountFromDriver;
	}



	public final void setCancelledAmountFromDriver(String cancelledAmountFromDriver) {
		this.cancelledAmountFromDriver = cancelledAmountFromDriver;
	}



	public final int getCancelledAmountStatus() {
		return cancelledAmountStatus;
	}



	public final void setCancelledAmountStatus(int cancelledAmountStatus) {
		this.cancelledAmountStatus = cancelledAmountStatus;
	}



	public final String getCashMode() {
		return cashMode;
	}



	public final void setCashMode(String cashMode) {
		this.cashMode = cashMode;
	}



	public final String getDeliveryPersonMobile() {
		return deliveryPersonMobile;
	}



	public final void setDeliveryPersonMobile(String deliveryPersonMobile) {
		this.deliveryPersonMobile = deliveryPersonMobile;
	}



	public final String getDeliveryPersonName() {
		return deliveryPersonName;
	}



	public final void setDeliveryPersonName(String deliveryPersonName) {
		this.deliveryPersonName = deliveryPersonName;
	}



	public final String getDestinationLocation() {
		return destinationLocation;
	}



	public final void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}



	public final String getGoodsSize() {
		return goodsSize;
	}



	public final void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}



	public final String getGoodsType() {
		return goodsType;
	}



	public final void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}



	public final String getPickupLocation() {
		return pickupLocation;
	}



	public final void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}



	public final String getPickupPersonMobile() {
		return pickupPersonMobile;
	}



	public final void setPickupPersonMobile(String pickupPersonMobile) {
		this.pickupPersonMobile = pickupPersonMobile;
	}



	public final String getPickupPersonName() {
		return pickupPersonName;
	}



	public final void setPickupPersonName(String pickupPersonName) {
		this.pickupPersonName = pickupPersonName;
	}



	public final String getRatings() {
		return ratings;
	}



	public final void setRatings(String ratings) {
		this.ratings = ratings;
	}



	public final Date getTripEndtime() {
		return tripEndtime;
	}



	public final void setTripEndtime(Date tripEndtime) {
		this.tripEndtime = tripEndtime;
	}



	public final Date getTripStarttime() {
		return tripStarttime;
	}



	public final void setTripStarttime(Date tripStarttime) {
		this.tripStarttime = tripStarttime;
	}



	public final Date getTripTime() {
		return tripTime;
	}



	public final void setTripTime(Date tripTime) {
		this.tripTime = tripTime;
	}



	public final String getTripEndOtp() {
		return tripEndOtp;
	}



	public final void setTripEndOtp(String tripEndOtp) {
		this.tripEndOtp = tripEndOtp;
	}



	public final String getTripStartOtp() {
		return tripStartOtp;
	}



	public final void setTripStartOtp(String tripStartOtp) {
		this.tripStartOtp = tripStartOtp;
	}



	public final DriverDetails getDriverDetails() {
		return driverDetails;
	}



	public final void setDriverDetails(DriverDetails driverDetails) {
		this.driverDetails = driverDetails;
	}



	public final CustomerDetails getCustomerDetails() {
		return customerDetails;
	}



	public final void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}



	public final DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}



	public final void setDeliveryStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}



	public static TripDetailsVo convertTOVo(TripDetails tripDetails) {
		TripDetailsVo tripDetailsVo = new TripDetailsVo();
		tripDetailsVo.setId(tripDetails.getId());
		tripDetailsVo.setAmount(tripDetails.getAmount());
		tripDetailsVo.setAmountToApp(tripDetails.getAmountToApp());
		tripDetailsVo.setAmountToDriver(tripDetails.getAmountToDriver());
		tripDetailsVo.setCanceledReason(tripDetails.getCanceledReason());
		tripDetailsVo.setCancelledAmountFromCustomer(tripDetails.getCancelledAmountFromCustomer());
		tripDetailsVo.setCancelledAmountFromDriver(tripDetails.getCancelledAmountFromDriver());
		tripDetailsVo.setCancelledAmountStatus(tripDetails.getCancelledAmountStatus());
		tripDetailsVo.setCashMode(tripDetails.getCashMode());
		tripDetailsVo.setDeliveryPersonMobile(tripDetails.getDeliveryPersonMobile());
		tripDetailsVo.setDeliveryPersonName(tripDetails.getDeliveryPersonName());
		tripDetailsVo.setDestinationLocation(tripDetails.getDestinationLocation());
		tripDetailsVo.setGoodsType(tripDetails.getGoodsType());
		tripDetailsVo.setGoodsSize(tripDetails.getGoodsSize());
		tripDetailsVo.setPickupLocation(tripDetails.getPickupLocation());
		tripDetailsVo.setRatings(tripDetails.getRatings());
		tripDetailsVo.setTripTime(tripDetails.getTripTime());
		tripDetailsVo.setTripStarttime(tripDetails.getTripStarttime());
		tripDetailsVo.setTripEndtime(tripDetails.getTripEndtime());
		tripDetailsVo.setDriverDetails(DriverDetails.convertModelToVo(tripDetails.getDriverDetails()));
		return tripDetailsVo;
	}

}
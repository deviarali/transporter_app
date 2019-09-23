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
	private String cancelledamountFromCustomer;

	@Column(name = "cancelledamount_from_driver")
	private String cancelledamountFromDriver;

	@Column(name = "cancelledamount_status")
	private byte cancelledamountStatus;

	@Column(name = "cash_mode")
	private String cashMode;

	@Column(name = "deliveryperson_mobile")
	private String deliverypersonMobile;

	@Column(name = "deliveryperson_name")
	private String deliverypersonName;

	@Column(name = "destination_location")
	private String destinationLocation;

	@Column(name = "goods_size")
	private String goodsSize;

	@Column(name = "goods_type")
	private String goodsType;

	@Column(name = "pickup_location")
	private String pickupLocation;

	@Column(name = "pickupperson_mobile")
	private String pickuppersonMobile;

	@Column(name = "pickupperson_name")
	private String pickuppersonName;

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
	private String tripendOtp;

	@Column(name = "tripstart_otp")
	private String tripstartOtp;

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

	public static TripDetailsVo convertTOVo(TripDetails tripDetails) {
		TripDetailsVo tripDetailsVo = new TripDetailsVo();
		tripDetailsVo.setId(tripDetails.getId());
		tripDetailsVo.setAmount(tripDetails.getAmount());
		tripDetailsVo.setAmountToApp(tripDetails.getAmountToApp());
		tripDetailsVo.setAmountToDriver(tripDetails.getAmountToDriver());
		tripDetailsVo.setCanceledReason(tripDetails.getCanceledReason());
		tripDetailsVo.setCancelledamountFromCustomer(tripDetails.getCancelledamountFromCustomer());
		tripDetailsVo.setCancelledamountFromDriver(tripDetails.getCancelledamountFromDriver());
		tripDetailsVo.setCancelledamountStatus(tripDetails.getCancelledamountStatus());
		tripDetailsVo.setCashMode(tripDetails.getCashMode());
		tripDetailsVo.setDeliverypersonMobile(tripDetails.getDeliverypersonMobile());
		tripDetailsVo.setDeliverypersonName(tripDetails.getDeliverypersonName());
		tripDetailsVo.setDestinationLocation(tripDetails.getDestinationLocation());
		tripDetailsVo.setGoodsType(tripDetails.getGoodsType());
		tripDetailsVo.setGoodsSize(tripDetails.getGoodsSize());
		tripDetailsVo.setPickupLocation(tripDetails.getPickupLocation());
		tripDetailsVo.setRatings(tripDetails.getRatings());
		tripDetailsVo.setTripTime(tripDetails.getTripTime());
		tripDetailsVo.setTripStarttime(tripDetails.getTripStarttime());
		tripDetailsVo.setTripEndtime(tripDetails.getTripEndtime());
		tripDetailsVo.setDriverDetailsVo(DriverDetails.convertModelToVo(tripDetails.getDriverDetails()));
		return tripDetailsVo;
	}

}
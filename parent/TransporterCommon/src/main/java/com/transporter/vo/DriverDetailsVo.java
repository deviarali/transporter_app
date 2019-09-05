package com.transporter.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the driverdetails database table.
 * 
 */
public class DriverDetailsVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String addressCity;

	private String addressState;

	private String addressStreet;

	private String addressZipcode;

	private Date createdOn;

	private Date dateofbirth;

	private String driverDocuments;

	private String driverVerificationPendingReason;

	private String driverVerificationStatus;

	private String drivername;

	private int onRoad;

	private String ratings;

	private String transportType;

	private UserVo createdBy;

	private UserVo user;

	private UserVo verifedBy;

	private List<VehicleDetailsVo> vehicleDetailsVoList;

	private List<TripDetailsVo> tripDetailsVoList;

	public DriverDetailsVo() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressState() {
		return addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getAddressZipcode() {
		return addressZipcode;
	}

	public void setAddressZipcode(String addressZipcode) {
		this.addressZipcode = addressZipcode;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getDriverDocuments() {
		return driverDocuments;
	}

	public void setDriverDocuments(String driverDocuments) {
		this.driverDocuments = driverDocuments;
	}

	public String getDriverVerificationPendingReason() {
		return driverVerificationPendingReason;
	}

	public void setDriverVerificationPendingReason(String driverVerificationPendingReason) {
		this.driverVerificationPendingReason = driverVerificationPendingReason;
	}

	public String getDriverVerificationStatus() {
		return driverVerificationStatus;
	}

	public void setDriverVerificationStatus(String driverVerificationStatus) {
		this.driverVerificationStatus = driverVerificationStatus;
	}

	public String getDrivername() {
		return drivername;
	}

	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}

	public int getOnRoad() {
		return onRoad;
	}

	public void setOnRoad(int onRoad) {
		this.onRoad = onRoad;
	}

	public String getRatings() {
		return ratings;
	}

	public void setRatings(String ratings) {
		this.ratings = ratings;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public UserVo getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserVo createdBy) {
		this.createdBy = createdBy;
	}

	public UserVo getUser() {
		return user;
	}

	public void setUser(UserVo user) {
		this.user = user;
	}

	public UserVo getVerifedBy() {
		return verifedBy;
	}

	public void setVerifedBy(UserVo verifedBy) {
		this.verifedBy = verifedBy;
	}

	public List<VehicleDetailsVo> getVehicleDetailsVoList() {
		return vehicleDetailsVoList;
	}

	public void setVehicleDetailsVoList(List<VehicleDetailsVo> vehicleDetailsVoList) {
		this.vehicleDetailsVoList = vehicleDetailsVoList;
	}

	public List<TripDetailsVo> getTripDetailsVoList() {
		return tripDetailsVoList;
	}

	public void setTripDetailsVoList(List<TripDetailsVo> tripDetailsVoList) {
		this.tripDetailsVoList = tripDetailsVoList;
	}

}
package com.transporter.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the vehicledetails database table.
 * 
 */
@Entity
@Table(name="vehicledetails")
public class VehicleDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="created_on")
	private Date createdOn;

	@Column(name="vehicle_color")
	private String vehicleColor;

	@Lob
	@Column(name="vehicle_documents")
	private String vehicleDocuments;

	@Column(name="vehicle_model")
	private String vehicleModel;

	@Column(name="vehicle_num")
	private String vehicleNum;

	@Column(name="vehicle_type")
	private String vehicleType;

	@Lob
	@Column(name="vehicle_verification_pending_reason")
	private String vehicleVerificationPendingReason;

	@Column(name="vehicle_verification_status")
	private int vehicleVerificationStatus;

	@Column(name="created_by")
	private String createdBy;

	@OneToOne
	@JoinColumn(name="driver_id")
	private DriverDetails driverDetails;

	@Column(name="verified_by")
	private String verifiedBy;

	public VehicleDetails() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getVehicleDocuments() {
		return vehicleDocuments;
	}

	public void setVehicleDocuments(String vehicleDocuments) {
		this.vehicleDocuments = vehicleDocuments;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getVehicleNum() {
		return vehicleNum;
	}

	public void setVehicleNum(String vehicleNum) {
		this.vehicleNum = vehicleNum;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleVerificationPendingReason() {
		return vehicleVerificationPendingReason;
	}

	public void setVehicleVerificationPendingReason(String vehicleVerificationPendingReason) {
		this.vehicleVerificationPendingReason = vehicleVerificationPendingReason;
	}

	public int getVehicleVerificationStatus() {
		return vehicleVerificationStatus;
	}

	public void setVehicleVerificationStatus(int vehicleVerificationStatus) {
		this.vehicleVerificationStatus = vehicleVerificationStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public DriverDetails getDriverDetails() {
		return driverDetails;
	}

	public void setDriverDetails(DriverDetails driverDetails) {
		this.driverDetails = driverDetails;
	}

	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}
	
}
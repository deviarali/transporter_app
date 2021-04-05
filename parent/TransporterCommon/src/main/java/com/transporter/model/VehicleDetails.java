package com.transporter.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.transporter.vo.VehicleDetailsVo;

/**
 * The persistent class for the vehicledetails database table.
 * 
 */
@Entity
@Table(name = "vehicledetails")
public class VehicleDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "vehicle_color")
	private String vehicleColor;

	@Lob
	@Column(name = "vehicle_documents")
	private String vehicleDocuments;

	@Column(name = "vehicle_model")
	private String vehicleModel;

	@Column(name = "vehicle_num")
	private String vehicleNum;

	@ManyToOne
	@JoinColumn(name="vehicle_type")
	private VehicleType vehicleType;

	@Lob
	@Column(name = "vehicle_verification_pending_reason")
	private String vehicleVerificationPendingReason;

	@Column(name = "vehicle_verification_status")
	private int vehicleVerificationStatus;

	@Column(name = "created_by")
	private int createdBy;

	@OneToOne
	@JoinColumn(name = "driver_id")
	private DriverDetails driverDetails;

	@Column(name = "verified_by")
	private String verifiedBy;
	
	@Column(name = "current_lattitude")
	private Double currentLattitude;
	
	@Column(name = "current_longitude")
	private Double currentLongitude;
	
	@Column(name = "vehicle_name")
	private String vehicleName;

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

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
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

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
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

	public final Double getCurrentLattitude() {
		return currentLattitude;
	}

	public final void setCurrentLattitude(Double currentLattitude) {
		this.currentLattitude = currentLattitude;
	}

	public final Double getCurrentLongitude() {
		return currentLongitude;
	}

	public final void setCurrentLongitude(Double currentLongitude) {
		this.currentLongitude = currentLongitude;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public static VehicleDetailsVo convertModelToVo(VehicleDetails vehicleDetails) {
		if (vehicleDetails == null)
			return null;
		VehicleDetailsVo vehicleDetailsVo = new VehicleDetailsVo();
		vehicleDetailsVo.setId(vehicleDetails.getId());
		vehicleDetailsVo.setVehicleColor(vehicleDetails.getVehicleColor());
		vehicleDetailsVo.setVehicleModel(vehicleDetails.getVehicleModel());
		vehicleDetailsVo.setVehicleNum(vehicleDetails.getVehicleNum());
		vehicleDetailsVo.setVehicleType(VehicleType.convertModelToVo(vehicleDetails.getVehicleType()));
		vehicleDetailsVo.setCurrentLattitude(vehicleDetails.getCurrentLattitude());
		vehicleDetailsVo.setCurrentLongitude(vehicleDetails.getCurrentLongitude());
		vehicleDetailsVo.setVehicleName(vehicleDetails.getVehicleName());
		vehicleDetailsVo.setDriverName(vehicleDetails.getDriverDetails().getUser().getFirstName());
		return vehicleDetailsVo;
	}

}
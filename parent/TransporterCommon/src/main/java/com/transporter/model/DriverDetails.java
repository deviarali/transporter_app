package com.transporter.model;

import java.io.Serializable;
import javax.persistence.*;

import com.transporter.vo.DriverDetailsVo;
import com.transporter.vo.UserVo;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the driverdetails database table.
 * 
 */
@Entity
@Table(name="driverdetails")
public class DriverDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="address_city")
	private String addressCity;

	@Column(name="address_state")
	private String addressState;

	@Lob
	@Column(name="address_street")
	private String addressStreet;

	@Column(name="address_zipcode")
	private String addressZipcode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	private Date createdOn;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Lob
	@Column(name="driver_documents")
	private String driverDocuments;

	@Lob
	@Column(name="driver_verification_pending_reason")
	private String driverVerificationPendingReason;

	@Column(name="driver_verification_status")
	private String driverVerificationStatus;

	@Column(name="driver_name")
	private String drivername;

	@Column(name="on_road")
	private int onRoad;

	@Column(name="ratings")
	private String ratings;

	@Column(name="transport_type")
	private String transportType;

	//bi-directional many-to-one association to User
	@OneToOne
	@JoinColumn(name="created_by")
	private User createdBy;

	//bi-directional many-to-one association to User
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;

	//bi-directional many-to-one association to User
	@OneToOne
	@JoinColumn(name="verified_by")
	private User verifedBy;

	//bi-directional many-to-one association to Vehicledetail
	@OneToMany(mappedBy="driverDetails")
	private List<VehicleDetails> vehicleDetailsList;
	
	@OneToMany(mappedBy="driverDetails")
	private List<TripDetails> tripDetailsList;
	
	@Column(name = "current_lattitude")
	private Double currentLattitude;
	
	@Column(name = "current_longitude")
	private Double currentLongitude;

	public DriverDetails() {
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getVerifedBy() {
		return verifedBy;
	}

	public void setVerifedBy(User verifedBy) {
		this.verifedBy = verifedBy;
	}

	public List<VehicleDetails> getVehicleDetailsList() {
		return vehicleDetailsList;
	}

	public void setVehicleDetailsList(List<VehicleDetails> vehicleDetailsList) {
		this.vehicleDetailsList = vehicleDetailsList;
	}

	public List<TripDetails> getTripDetailsList() {
		return tripDetailsList;
	}

	public void setTripDetailsList(List<TripDetails> tripDetailsList) {
		this.tripDetailsList = tripDetailsList;
	}

	public Double getCurrentLattitude() {
		return currentLattitude;
	}

	public void setCurrentLattitude(Double currentLattitude) {
		this.currentLattitude = currentLattitude;
	}

	public Double getCurrentLongitude() {
		return currentLongitude;
	}

	public void setCurrentLongitude(Double currentLongitude) {
		this.currentLongitude = currentLongitude;
	}

	public static DriverDetailsVo convertModelToVo(DriverDetails driverDetails) {
		if(driverDetails == null)
		return null;
		DriverDetailsVo driverDetailsVo = new DriverDetailsVo();
		driverDetailsVo.setAddressCity(driverDetails.getAddressCity());
		driverDetailsVo.setAddressState(driverDetails.getAddressState());
		driverDetailsVo.setAddressStreet(driverDetails.getAddressStreet());
		driverDetailsVo.setAddressZipcode(driverDetails.getAddressZipcode());
		driverDetailsVo.setCreatedOn(driverDetails.createdOn);
		driverDetailsVo.setDateofbirth(driverDetails.getDateOfBirth());
		driverDetailsVo.setDriverDocuments(driverDetails.getDriverDocuments());
		driverDetailsVo.setDrivername(driverDetails.getDrivername());
		driverDetailsVo.setDriverVerificationPendingReason(driverDetails.getDriverVerificationPendingReason());
		driverDetailsVo.setDriverVerificationStatus(driverDetails.getDriverVerificationStatus());
		driverDetailsVo.setId(driverDetails.getId());
		driverDetailsVo.setOnRoad(driverDetails.getOnRoad());
		driverDetailsVo.setRatings(driverDetails.getRatings());
		driverDetailsVo.setTransportType(driverDetails.getTransportType());
		/*driverDetailsVo.setTripDetailsVoList(driverDetailsCreatedBy.getTripDetailsList());
		driverDetailsVo.setUser();*/
		driverDetailsVo.setCurrentLattitude(driverDetails.getCurrentLattitude());
		driverDetailsVo.setCurrentLongitude(driverDetails.getCurrentLongitude());
		return driverDetailsVo;
	}

	
	
		
	

}
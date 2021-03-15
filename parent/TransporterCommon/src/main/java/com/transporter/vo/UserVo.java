package com.transporter.vo;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the user database table.
 * 
 */
public class UserVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private int createdBy;

	private Date createdOn;

	private String emailId;

	private byte emailVerified;

	private String firstName;

	private String gender;

	private String lastName;

	private String loginOtp;

	private Date loginTime;

	private int noOfVehicles;

	private String password;

	private String mobileNumber;

	private String previousEmailId;

	private int status;

	private int transporterId;

	private CustomerDetailsVo customerDetails;

	private DriverDetailsVo driverDetails;

	private UserRoleVo userRole;

	private InternalUserDetailsVo internalUserDetails;

	private String profilePictureUrl;
	
	private String fcmToken;

	private String inActiveReason;

	public UserVo() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public byte getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(byte emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLoginOtp() {
		return loginOtp;
	}

	public void setLoginOtp(String loginOtp) {
		this.loginOtp = loginOtp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public int getNoOfVehicles() {
		return noOfVehicles;
	}

	public void setNoOfVehicles(int noOfVehicles) {
		this.noOfVehicles = noOfVehicles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPreviousEmailId() {
		return previousEmailId;
	}

	public void setPreviousEmailId(String previousEmailId) {
		this.previousEmailId = previousEmailId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTransporterId() {
		return transporterId;
	}

	public void setTransporterId(int transporterId) {
		this.transporterId = transporterId;
	}
	
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public CustomerDetailsVo getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetailsVo customerDetails) {
		this.customerDetails = customerDetails;
	}

	public DriverDetailsVo getDriverDetails() {
		return driverDetails;
	}

	public void setDriverDetails(DriverDetailsVo driverDetails) {
		this.driverDetails = driverDetails;
	}

	public UserRoleVo getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRoleVo userRole) {
		this.userRole = userRole;
	}

	public InternalUserDetailsVo getInternalUserDetails() {
		return internalUserDetails;
	}

	public void setInternalUserDetails(InternalUserDetailsVo internalUserDetails) {
		this.internalUserDetails = internalUserDetails;
	}
	
	public String getInActiveReason() {
		return inActiveReason;
	}
	
	public void setInActiveReason(String inActiveReason) {
		this.inActiveReason = inActiveReason;
	}

}
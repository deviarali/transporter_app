package com.transporter.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.transporter.vo.UserVo;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	private Date createdOn;

	@Column(name="email_id")
	private String emailId;

	@Column(name="email_verified")
	private byte emailVerified;

	@Column(name="first_name")
	private String firstName;

	@Column(name="gender")
	private String gender;

	@Column(name="last_name")
	private String lastName;

	@Column(name="login_otp")
	private String loginOtp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="login_time")
	private Date loginTime;

	@Column(name="no_of_vehicles")
	private int noOfVehicles;

	@Column(name="password")
	private String password;

	@Column(name="mobile_number")
	private String mobileNumber;

	@Column(name="previous_email_id")
	private String previousEmailId;

	@Column(name="status")
	private int status;

	@Column(name="transporter_id")
	private int transporterId;

	//bi-directional many-to-one association to Customerdetail
	@OneToOne(mappedBy="user")
	private CustomerDetails customerDetails;

	//bi-directional many-to-one association to Driverdetail
	@OneToOne(mappedBy="createdBy")
	private DriverDetails driverDetailsCreatedBy;

	//bi-directional many-to-one association to Driverdetail
	@OneToOne(mappedBy="user")
	private DriverDetails driverDetails;

	//bi-directional many-to-one association to Driverdetail
	@OneToOne(mappedBy="verifedBy")
	private DriverDetails driverDetailsVerifedBy;

	//bi-directional many-to-one association to Userrole
	@ManyToOne
	@JoinColumn(name="user_role")
	private UserRole userrole;

	//bi-directional many-to-one association to Internaluserdetail
	@OneToOne
	@JoinColumn(name="internal_user_detail")
	private InternalUserDetails internalUserDetails;

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
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

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public DriverDetails getDriverDetailsCreatedBy() {
		return driverDetailsCreatedBy;
	}

	public void setDriverDetailsCreatedBy(DriverDetails driverDetailsCreatedBy) {
		this.driverDetailsCreatedBy = driverDetailsCreatedBy;
	}

	public DriverDetails getDriverDetails() {
		return driverDetails;
	}

	public void setDriverDetails(DriverDetails driverDetails) {
		this.driverDetails = driverDetails;
	}

	public DriverDetails getDriverDetailsVerifedBy() {
		return driverDetailsVerifedBy;
	}

	public void setDriverDetailsVerifedBy(DriverDetails driverDetailsVerifedBy) {
		this.driverDetailsVerifedBy = driverDetailsVerifedBy;
	}

	public UserRole getUserRole() {
		return userrole;
	}

	public void setUserRole(UserRole userrole) {
		this.userrole = userrole;
	}

	public InternalUserDetails getInternalUserDetails() {
		return internalUserDetails;
	}

	public void setInternalUserDetails(InternalUserDetails internalUserDetails) {
		this.internalUserDetails = internalUserDetails;
	}

	public static UserVo convertModelToVo(User user) {
		if(user == null)
			return null;
		UserVo userVo = new UserVo();
		userVo.setId(user.getId());
		userVo.setUserRoleVo(UserRole.convertModelToVo(user.getUserRole()));
		userVo.setFirstName(user.getFirstName());
		userVo.setLastName(user.getLastName());
		userVo.setEmailId(user.getEmailId());
		userVo.setEmailVerified(user.getEmailVerified());
		userVo.setGender(user.getGender());
		userVo.setLoginOtp(user.getLoginOtp());
		userVo.setLoginTime(user.getLoginTime());
		userVo.setNoOfVehicles(user.getNoOfVehicles());
		userVo.setPassword(user.getPassword());
		userVo.setMobileNumber(user.getMobileNumber());
		userVo.setPreviousEmailId(user.getPreviousEmailId());
		userVo.setStatus(user.getStatus());
		userVo.setTransporterId(user.getTransporterId());
		userVo.setCreatedBy(user.getCreatedBy());
		userVo.setCreatedOn(user.getCreatedOn());
		userVo.setCustomerDetails(CustomerDetails.convertModelToVO(user.getCustomerDetails()));
		userVo.setDriverDetailsCreatedByVo(DriverDetails.convertModelToVo(user.getDriverDetailsCreatedBy()));
		
		return userVo;
	}
	

}
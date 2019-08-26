package com.transporter.vo;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the internaluserdetails database table.
 * 
 */
public class InternalUserDetailsVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private byte activeInternal;

	private String addressCity;

	private String addressState;

	private String addressStreet;

	private String addressZipcode;

	private Date dateofbirth;

	private InternalUserroleMasterVo internalUserroleMasterVo;

	private UserVo userVo;

	public InternalUserDetailsVo() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getActiveInternal() {
		return activeInternal;
	}

	public void setActiveInternal(byte activeInternal) {
		this.activeInternal = activeInternal;
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

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public InternalUserroleMasterVo getInternalUserroleMasterVo() {
		return internalUserroleMasterVo;
	}

	public void setInternalUserroleMasterVo(InternalUserroleMasterVo internalUserroleMasterVo) {
		this.internalUserroleMasterVo = internalUserroleMasterVo;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}
	
}
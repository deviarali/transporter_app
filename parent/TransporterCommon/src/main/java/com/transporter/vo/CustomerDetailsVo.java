package com.transporter.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class CustomerDetailsVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String addressCity;

	private String addressState;

	private String addressStreet;

	private String addressZipcode;

	private Date dateofbirth;

	private UserVo user;
	
	private List<TripDetailsVo> tripDetailsVoList;

	public CustomerDetailsVo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddressCity() {
		return this.addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressState() {
		return this.addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}

	public String getAddressStreet() {
		return this.addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getAddressZipcode() {
		return this.addressZipcode;
	}

	public void setAddressZipcode(String addressZipcode) {
		this.addressZipcode = addressZipcode;
	}

	public Date getDateofbirth() {
		return this.dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public UserVo getUser() {
		return this.user;
	}

	public void setUser(UserVo user) {
		this.user = user;
	}

	public List<TripDetailsVo> getTripDetailsVoList() {
		return tripDetailsVoList;
	}

	public void setTripDetailsVoList(List<TripDetailsVo> tripDetailsVoList) {
		this.tripDetailsVoList = tripDetailsVoList;
	}
	
}
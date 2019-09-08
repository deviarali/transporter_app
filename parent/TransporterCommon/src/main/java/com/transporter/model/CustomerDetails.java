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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.transporter.vo.CustomerDetailsVo;


/**
 * The persistent class for the customerdetails database table.
 * 
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="customerdetails")
public class CustomerDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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

	@Temporal(TemporalType.DATE)
	private Date dateofbirth;

	//bi-directional many-to-one association to User
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public CustomerDetails() {
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public static CustomerDetailsVo convertModelToVO(CustomerDetails customerDetails) {
		if(customerDetails == null)
			return null;
		CustomerDetailsVo customerDetailsVo = new CustomerDetailsVo();
		customerDetailsVo.setId(customerDetails.getId());
		customerDetailsVo.setAddressCity(customerDetails.getAddressCity());
		customerDetailsVo.setAddressState(customerDetails.getAddressState());
		customerDetailsVo.setAddressStreet(customerDetails.getAddressStreet());
		customerDetailsVo.setAddressZipcode(customerDetails.getAddressZipcode());
		customerDetailsVo.setDateofbirth(customerDetails.getDateofbirth());
		customerDetailsVo.setUser(User.convertModelToVo(customerDetails.getUser()));
		return customerDetailsVo;
	}

	

}
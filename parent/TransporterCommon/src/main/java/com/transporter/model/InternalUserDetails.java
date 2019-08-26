package com.transporter.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the internaluserdetails database table.
 * 
 */
@Entity
@Table(name="internaluserdetails")
public class InternalUserDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="active_internal")
	private byte activeInternal;

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

	//bi-directional many-to-one association to Internaluserrolemaster
	@ManyToOne
	@JoinColumn(name="internal_user_role")
	private InternalUserroleMaster internalUserroleMaster;

	//bi-directional many-to-one association to User
	@OneToOne(mappedBy="internalUserDetails")
	private User user;

	public InternalUserDetails() {
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

	public InternalUserroleMaster getInternalUserroleMaster() {
		return internalUserroleMaster;
	}

	public void setInternalUserroleMaster(InternalUserroleMaster internalUserroleMaster) {
		this.internalUserroleMaster = internalUserroleMaster;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
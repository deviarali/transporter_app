package com.transporter.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author SHARAN A
 */
@Entity
@Table(name = "referral_detail")
public class ReferralDetail implements Serializable {

	private Integer id;
	private Boolean isActive;

	private Calendar createdOn;

	private User referredUser;
	private User referreeUser;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "is_active", nullable = false)
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "created_on", nullable = false)
	public Calendar getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="referred_user_id", nullable = false)
	public User getReferredUser() {
		return referredUser;
	}

	public void setReferredUser(User referredUser) {
		this.referredUser = referredUser;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="referree_user_id", nullable = true)
	public User getReferreeUser() {
		return referreeUser;
	}

	public void setReferreeUser(User referreeUser) {
		this.referreeUser = referreeUser;
	}

}

package com.transporter.vo;

import java.util.Calendar;

/**
 * @author SHARAN A
 */
public class ReferralDetailVo {

	private Integer id;
	private Boolean isActive;

	private Calendar createdOn;

	private UserVo referredUser;
	private UserVo referreeUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Calendar getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}

	public UserVo getReferredUser() {
		return referredUser;
	}

	public void setReferredUser(UserVo referredUser) {
		this.referredUser = referredUser;
	}

	public UserVo getReferreeUser() {
		return referreeUser;
	}

	public void setReferreeUser(UserVo referreeUser) {
		this.referreeUser = referreeUser;
	}

}

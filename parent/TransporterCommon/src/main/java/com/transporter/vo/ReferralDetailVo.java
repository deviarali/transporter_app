package com.transporter.vo;

/**
 * @author SHARAN A
 */
public class ReferralDetailVo {

	private Integer id;
	private Boolean isActive;

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

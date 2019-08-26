package com.transporter.vo;

import java.io.Serializable;


/**
 * The persistent class for the userrole database table.
 * 
 */
public class UserRoleVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String label;

	private int modifiedBy;

	private String roleCd;

	private String roleDecription;

	public UserRoleVo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getRoleCd() {
		return this.roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}

	public String getRoleDecription() {
		return this.roleDecription;
	}

	public void setRoleDecription(String roleDecription) {
		this.roleDecription = roleDecription;
	}

}
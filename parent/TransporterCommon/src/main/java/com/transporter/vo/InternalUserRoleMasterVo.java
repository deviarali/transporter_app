package com.transporter.vo;

import java.io.Serializable;


/**
 * The persistent class for the internaluserrolemaster database table.
 * 
 */
public class InternalUserRoleMasterVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String roleDescription;

	private String roleName;

	public InternalUserRoleMasterVo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleDescription() {
		return this.roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
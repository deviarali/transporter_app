package com.transporter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.transporter.vo.InternalUserRoleMasterVo;


/**
 * The persistent class for the internaluserrolemaster database table.
 * 
 */
@Entity
@Table(name="internaluserrolemaster")
public class InternalUserRoleMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;

	@Column(name="role_description")
	private String roleDescription;

	@Column(name="role_name")
	private String roleName;

	public InternalUserRoleMaster() {
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
	
	public static InternalUserRoleMasterVo convertModelToVo(InternalUserRoleMaster internalUserRoleMaster) {
		if(null == internalUserRoleMaster)
			return null;
		InternalUserRoleMasterVo internalUserRoleMasterVo = new InternalUserRoleMasterVo();
		internalUserRoleMasterVo.setId(internalUserRoleMaster.getId());
		internalUserRoleMasterVo.setRoleName(internalUserRoleMaster.getRoleName());
		internalUserRoleMasterVo.setRoleDescription(internalUserRoleMaster.getRoleDescription());
		return internalUserRoleMasterVo;
	}
	
}
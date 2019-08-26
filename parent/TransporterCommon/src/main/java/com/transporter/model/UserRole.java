package com.transporter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.transporter.vo.UserRoleVo;


/**
 * The persistent class for the userrole database table.
 * 
 */
@Entity
@Table(name = "userrole")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String label;

	@Column(name="modified_by")
	private int modifiedBy;

	@Column(name="role_cd")
	private String roleCd;

	@Column(name="role_decription")
	private String roleDecription;

	public UserRole() {
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

	public static UserRoleVo convertModelToVo(UserRole userRole) {
		if(userRole == null)
			return null;
		UserRoleVo userRoleVo = new UserRoleVo();
		userRoleVo.setId(userRole.getId());
		userRoleVo.setLabel(userRole.getLabel());
		userRoleVo.setModifiedBy(userRole.getModifiedBy());
		userRoleVo.setRoleCd(userRole.getRoleCd());
		userRoleVo.setRoleDecription(userRole.getRoleDecription());
		return userRoleVo;
	}

}
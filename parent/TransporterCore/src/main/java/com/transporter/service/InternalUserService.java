package com.transporter.service;

import java.util.List;

import com.transporter.vo.DriverDetailsVo;
import com.transporter.vo.InternalUserDetailsVo;
import com.transporter.vo.InternalUserRoleMasterVo;

/**
 * @author Devappa.Arali
 *
 */

public interface InternalUserService {

	String createInternalUser(InternalUserDetailsVo internalUserDetailsVo);
	
	List<DriverDetailsVo> getDriversForEmployee(int id);

	List<InternalUserRoleMasterVo> getInternalUserRoles();

}

package com.transporter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.constants.WebConstants;
import com.transporter.dao.InternalUserDao;
import com.transporter.enums.UserRoleEnum;
import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.model.InternalUserDetails;
import com.transporter.model.InternalUserRoleMaster;
import com.transporter.model.User;
import com.transporter.service.InternalUserService;
import com.transporter.service.UserService;
import com.transporter.vo.InternalUserDetailsVo;
import com.transporter.vo.UserRoleVo;
import com.transporter.vo.UserVo;

/**
 * @author Devappa.Arali
 *
 */

@Service
public class InternalUserServiceImpl implements InternalUserService {

	@Autowired
	private InternalUserDao internalUserDao;
	
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional
	public String createInternalUser(InternalUserDetailsVo internalUserDetailsVo) {
		String response = null;
		UserVo userExists = userService.isUserExists(internalUserDetailsVo.getUser().getMobileNumber());
		if(null != userExists) {
			throw new BusinessException(ErrorCodes.MOEXISTS.name(), ErrorCodes.MOEXISTS.value());
		}
		UserVo userVo = internalUserDetailsVo.getUser();
		UserRoleVo userRoleVo = new UserRoleVo();
		userRoleVo.setId(UserRoleEnum.INTERNALUSER.getId());
		userVo.setUserRoleVo(userRoleVo);
		User user = userService.registerUser(userVo);
		InternalUserDetails internalUserDetails = new InternalUserDetails();
		internalUserDetails.setActiveInternal(1);
		internalUserDetails.setAddressCity(internalUserDetailsVo.getAddressCity());
		internalUserDetails.setAddressState(internalUserDetailsVo.getAddressState());
		internalUserDetails.setAddressStreet(internalUserDetailsVo.getAddressStreet());
		internalUserDetails.setAddressZipcode(internalUserDetailsVo.getAddressZipcode());
		internalUserDetails.setDateofbirth(internalUserDetailsVo.getDateofbirth());
		internalUserDetails.setUser(user);
		InternalUserRoleMaster internalUserroleMaster = new InternalUserRoleMaster();
		internalUserroleMaster.setId(internalUserDetailsVo.getInternalUserRoleMaster().getId());
		internalUserDetails.setInternalUserroleMaster(internalUserroleMaster);
		internalUserDao.save(internalUserDetails);
		if(internalUserDetails.getId() > 0) {
			response = WebConstants.SUCCESS;
		}
		return response;
	}

}

package com.transporter.service.impl;

import com.transporter.dao.UserRoleDao;
import com.transporter.service.UserRoleService;

public class UserRoleServiceImpl implements UserRoleService{

	private UserRoleDao userRoleDao;
	
	/*@Override
	public List<UserRoleVO> getAllUserRoles() {
		List<UserRoleModel> userRoleModelList = userRoleDao.getAllUserRoles();
		List<UserRoleVO> userRoleVOList = new ArrayList<>();
		for(UserRoleModel userRoleModel : userRoleModelList)
		{
			userRoleVOList.add(UserRoleModel.convertModelToVO(userRoleModel));
		}
		return userRoleVOList;
	}
*/
}

package com.transporter.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.dao.UserDao;
import com.transporter.enums.UserRoleEnum;
import com.transporter.model.User;
import com.transporter.model.UserRole;
import com.transporter.service.UserService;
import com.transporter.utils.PasswordUtils;
import com.transporter.vo.UserVo;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	

	@Override
	public User registerUser(UserVo userVo) {
		User user = new User();
		user.setCreatedOn(new Date());
		user.setFirstName(userVo.getFirstName());
		user.setLastName(userVo.getLastName());
		user.setEmailId(userVo.getEmailId());
		user.setMobileNumber(userVo.getMobileNumber());
		user.setPassword(PasswordUtils.generateSecurePassword("devaraj"));
		user.setStatus(0);
		UserRole userRole = new UserRole();
		userRole.setId(UserRoleEnum.CUSTOMER.getId());
		user.setUserRole(userRole);
		userDao.save(user);
		return user;
	}

	@Override
	public UserVo isUserExists(String phoneNumber) {
		User user = userDao.isUserExists(phoneNumber);
		return User.convertModelToVo(user);
	}

	/*@Override
	public CustomerModel customerLogin(UserVO userVO) {
		UserModel userModel = userDao.login(userVO);
		if(null != userModel)
		{
			return userModel.getCustomers().get(0);
		}
		return null;
	}

	@Override
	public int generateOtp(String mobile) {
		return userDao.generateOtp(mobile, generateOtp());
	}

	public String generateOtp()
	{
		return "55555";
	}

	@Override
	public UserVO validateOtp(String mobile, String otp) {
		
		UserModel userModel = userDao.validateOtp(mobile, otp);
		return UserModel.convertModelToVO(userModel);
	}*/

}

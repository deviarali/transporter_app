package com.transporter.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.transporter.constants.WebConstants;
import com.transporter.dao.UserDao;
import com.transporter.enums.UserRoleEnum;
import com.transporter.model.User;
import com.transporter.model.UserRole;
import com.transporter.service.UserService;
import com.transporter.utility.TransporterUtility;
import com.transporter.utils.PasswordUtils;
import com.transporter.vo.UserVo;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TransporterUtility transporterUtility;
	

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
		user.setUserRole(UserRole.convertVoToModel(userVo.getUserRoleVo()));
		userDao.save(user);
		return user;
	}

	@Override
	public UserVo isUserExists(String phoneNumber) {
		User user = userDao.isUserExists(phoneNumber);
		return User.convertModelToVo(user);
	}

	@Override
	public int generateOtp(String mobile) {
		return userDao.generateOtp(mobile, generateOtp());
	}

	@Override
	@Transactional
	public UserVo validateOtp(String mobile, String otp) {
		
		User user = userDao.validateOtp(mobile, otp);
		return User.convertModelToVo(user);
	}
	
	@Override
	public User updateUser(UserVo userVo) {
		User user = new User();
		user.setCreatedOn(new Date());
		user.setFirstName(userVo.getFirstName());
		user.setLastName(userVo.getLastName());
		user.setEmailId(userVo.getEmailId());
		user.setId(userVo.getId());
		user.setMobileNumber(userVo.getMobileNumber());
		user.setPassword(PasswordUtils.generateSecurePassword("devaraj"));
		user.setStatus(0);
		UserRole userRole = new UserRole();
		userRole.setId(UserRoleEnum.CUSTOMER.getId());
		user.setUserRole(userRole);
		userDao.saveOrUpdate(user);
		return user;
	}
	
	public String generateOtp()
	{
		return "55555";
	}

	@Override
	@Transactional
	public String updateProfilePicture(MultipartFile multipart, String mobileNumber) {
		String generateFilePathAndStore = transporterUtility.generateFilePathAndStore(multipart, "user");
		if(!StringUtils.isNullOrEmpty(generateFilePathAndStore)) {
			int updated = userDao.updateProfilePicture(mobileNumber, generateFilePathAndStore);
			if(updated != 0) {
				return generateFilePathAndStore;
			}
		}
		return null;
	}

	@Override
	public String updateFcmToken(int id, String fcmToken) {
		String response = null;
		int update = userDao.updateFcmToken(id, fcmToken);
		if(update != 0) {
			response = WebConstants.SUCCESS;
		}
		return response;
	}

}

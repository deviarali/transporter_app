package com.transporter.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.transporter.constants.WebConstants;
import com.transporter.dao.UserDao;
import com.transporter.enums.UserRoleEnum;
import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.model.User;
import com.transporter.model.UserRole;
import com.transporter.repo.UserRepo;
import com.transporter.service.UserService;
import com.transporter.utility.TransporterUtility;
import com.transporter.utils.DateTimeUtils;
import com.transporter.utils.PasswordUtils;
import com.transporter.vo.UserVo;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private TransporterUtility transporterUtility;

	@Autowired
	private UserRepo userRepo;

	@Override
	public User registerUser(UserVo userVo) {
		User user = new User();
		user.setCreatedOn(new Date());
		user.setFirstName(userVo.getFirstName());
		user.setLastName(userVo.getLastName());
		user.setEmailId(userVo.getEmailId());
		user.setMobileNumber(userVo.getMobileNumber());
		user.setPassword(PasswordUtils.generateSecurePassword("devaraj"));
		user.setStatus(1);
		user.setUserRole(UserRole.convertVoToModel(userVo.getUserRole()));
		User createdBy = new User();
		createdBy.setId(userVo.getCreatedBy());
		user.setCreatedBy(createdBy);
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
		//user.setCreatedOn(new Date());
		user.setFirstName(userVo.getFirstName());
		user.setLastName(userVo.getLastName());
		user.setEmailId(userVo.getEmailId());
		user.setId(userVo.getId());
		user.setMobileNumber(userVo.getMobileNumber());
		/*user.setPassword(PasswordUtils.generateSecurePassword("devaraj"));
		user.setStatus(0);
		UserRole userRole = new UserRole();
		userRole.setId(UserRoleEnum.CUSTOMER.getId());
		user.setUserRole(userRole);*/
		userDao.updateUser(user);
		return user;
	}

	@Override
	public String generateOtp() {
		return "55555";
	}

	@Override
	@Transactional
	public String updateProfilePicture(MultipartFile multipart, String mobileNumber) {
		User user = userDao.isUserExists(mobileNumber);
		if (user == null) {
			throw new BusinessException(ErrorCodes.UNFOUND.name(), ErrorCodes.UNFOUND.value());
		}
		String generateFilePathAndStore = transporterUtility.generateFilePathAndStore(multipart, "profile");
		if (!StringUtils.isBlank(generateFilePathAndStore)) {
			int updated = userDao.updateProfilePicture(mobileNumber, generateFilePathAndStore);
			if (updated != 0) {
				return generateFilePathAndStore;
			}
		}
		return null;
	}

	@Override
	public String updateFcmToken(int id, String fcmToken) {
		String response = null;
		int update = userDao.updateFcmToken(id, fcmToken);
		if (update != 0) {
			response = WebConstants.SUCCESS;
		}
		return response;
	}

	@Override
	public User findById(int id) {
		User internalUser = userRepo.findOne(id);
		return internalUser;
	}

	@Override
	public User updateInternalUser(UserVo userVo, int id) {

		User user = findById(id);
		user.setFirstName(userVo.getFirstName());
		user.setLastName(userVo.getLastName());
		user.setEmailId(userVo.getEmailId());

		return userRepo.save(user);

	}

	@Override
	public int getTotalUsersCountByRole(int roleId) {
		return userRepo.getTotalUsersCountByRole(roleId);

	}
	
	@Override
	public int getTotalUsersCountForToday(int roleId) {
		Date startTime = DateTimeUtils.getZeroTimeDate(DateTimeUtils.getCurrentDate());
		Date endTime = DateTimeUtils.getLastMinTimeDate(DateTimeUtils.getCurrentDate());
		return userRepo.getTotalUsersCountForToday(roleId, startTime, endTime);

	}

	@Override
	public int deleteUser(int id, String reason) {
		int deleted = userDao.deleteUser(id, reason);
		return deleted;
	}
}

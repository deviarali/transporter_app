package com.transporter.dao;

import com.transporter.model.User;
import com.transporter.vo.UserVo;

public interface UserDao extends GenericDao {

	User isUserExists(String mobileNumber);

	User login(UserVo userVo);

	int generateOtp(String mobile, String generateOtp);

	User validateOtp(String mobile, String otp);

	int updateProfilePicture(String mobileNumber, String generateFilePathAndStore);

	int updateFcmToken(int id, String fcmToken);

	User isUserExistsUsingId(int id);

	int deleteUser(int id, String reason);

	int updateUser(User user);

}

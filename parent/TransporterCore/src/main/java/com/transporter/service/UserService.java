package com.transporter.service;

import org.springframework.web.multipart.MultipartFile;

import com.transporter.model.User;
import com.transporter.vo.UserVo;

public interface UserService {
	
	public User registerUser(UserVo userVo);

	public UserVo isUserExists(String mobileNumber);

	public int generateOtp(String mobile);

	public UserVo validateOtp(String mobile, String otp);
	
	public User updateUser(UserVo userVo);

	public String updateProfilePicture(MultipartFile multipart, String mobileNumber);

	public String updateFcmToken(int id, String fcmToken);

	public int updateProfilePicture(String mobileNumber, String generateFilePathAndStore);
	
}

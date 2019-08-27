package com.transporter.service;

import com.transporter.model.User;
import com.transporter.vo.UserVo;

public interface UserService {
	
	public User registerUser(UserVo userVo);

	public UserVo isUserExists(String mobileNumber);

	public int generateOtp(String mobile);

	public UserVo validateOtp(String mobile, String otp);
	
	public User updateUser(UserVo userVo);
	
}

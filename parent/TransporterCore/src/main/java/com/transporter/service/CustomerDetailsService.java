package com.transporter.service;

import com.transporter.vo.CustomerDetailsVo;
import com.transporter.vo.UserVo;

public interface CustomerDetailsService {

	String registerCustomer(CustomerDetailsVo customerDetailsVo);

	UserVo isUserExists(CustomerDetailsVo customerDetailsVo);

	int generateOtp(String mobile);

	CustomerDetailsVo validateOtp(String mobile, String otp);
	
	CustomerDetailsVo updateCustomer(CustomerDetailsVo customerDetailsVo);

}

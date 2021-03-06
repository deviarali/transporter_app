package com.transporter.service;

import com.transporter.model.CustomerDetails;
import com.transporter.vo.CustomerDetailsVo;
import com.transporter.vo.UserVo;

public interface CustomerDetailsService {

	public String registerCustomer(CustomerDetailsVo customerDetailsVo);

	UserVo isUserExists(CustomerDetailsVo customerDetailsVo);

	int generateOtp(String mobileNumber);

	CustomerDetailsVo validateOtp(String mobile, String otp);

	CustomerDetailsVo updateCustomer(CustomerDetailsVo customerDetailsVo);

	CustomerDetailsVo updateUserProfile(UserVo userVo);

	public CustomerDetails findCustomerById(int customerId);
	
	CustomerDetailsVo getUserById(int id);

}

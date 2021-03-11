package com.transporter.service;

import java.util.List;

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
	
	public CustomerDetailsVo findCustomerByUserId(int id);
	
	CustomerDetailsVo getUserById(int id);

	public List<CustomerDetailsVo> getTopCustomerForWeek(Integer count);

}

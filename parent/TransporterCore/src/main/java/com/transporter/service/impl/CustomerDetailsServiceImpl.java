package com.transporter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.dao.CustomerDetailsDao;
import com.transporter.enums.UserRoleEnum;
import com.transporter.model.CustomerDetails;
import com.transporter.model.User;
import com.transporter.service.CustomerDetailsService;
import com.transporter.service.UserService;
import com.transporter.vo.CustomerDetailsVo;
import com.transporter.vo.UserRoleVo;
import com.transporter.vo.UserVo;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService{

	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerDetailsDao customerDetailsDao;
	
	@Override
	@Transactional
	public CustomerDetailsVo registerCustomer(CustomerDetailsVo customerDetailsVo) {
		UserVo userVo = customerDetailsVo.getUserVo();
		UserRoleVo userRoleVo = new UserRoleVo();
		userRoleVo.setId(UserRoleEnum.CUSTOMER.getId());
		userVo.setUserRoleVo(userRoleVo);
		User user = userService.registerUser(userVo);

		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setUser(user);
		customerDetailsDao.save(customerDetails);		
		return CustomerDetails.convertModelToVO(customerDetails);
	}

	@Override
	public UserVo isUserExists(CustomerDetailsVo customerDetailsVo) {
		return userService.isUserExists(customerDetailsVo.getUserVo().getMobileNumber());
	}
	
	@Override
	public int generateOtp(String mobile) {
		return userService.generateOtp(mobile);
	}

	@Override
	public CustomerDetailsVo validateOtp(String mobile, String otp) {
		UserVo userVo = userService.validateOtp(mobile, otp); 
		CustomerDetailsVo customerDetailsVo = null;
		if(userVo != null)
		{
			customerDetailsVo = findCustomerByUserId(userVo.getId());
		}
		return customerDetailsVo;
	}
	
	@Override
	@Transactional
	public CustomerDetailsVo updateCustomer(CustomerDetailsVo customerDetailsVo) {
		User user = userService.updateUser(customerDetailsVo.getUserVo());

		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setUser(user);
		customerDetailsDao.saveOrUpdate(customerDetails);		
		return CustomerDetails.convertModelToVO(customerDetails);
	}

	private CustomerDetailsVo findCustomerByUserId(int id) {
		return CustomerDetails.convertModelToVO(customerDetailsDao.findCustomerByUserId(id));
	}

}

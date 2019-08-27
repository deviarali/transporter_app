package com.transporter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.dao.CustomerDetailsDao;
import com.transporter.model.CustomerDetails;
import com.transporter.model.User;
import com.transporter.service.CustomerDetailsService;
import com.transporter.service.UserService;
import com.transporter.vo.CustomerDetailsVo;
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
		
		User user = userService.registerUser(customerDetailsVo.getUserVo());

		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setUser(user);
		customerDetailsDao.save(customerDetails);		
		return CustomerDetails.convertModelToVO(customerDetails);
	}

	@Override
	public UserVo isUserExists(CustomerDetailsVo customerDetailsVo) {
		return userService.isUserExists(customerDetailsVo.getUserVo().getMobileNumber());
	}

	/*@Override
	public CustomerVO login(UserVO userVO) {
		userVO.setPassword(PasswordUtils.generateSecurePassword(userVO.getPassword()));
		CustomerModel customerModel = userService.customerLogin(userVO);
		CustomerVO customerVO = null;
		if(null != customerModel)
		{
			customerVO = CustomerModel.convertModelToVO(customerModel);
		}
		return customerVO;
	}

	@Override
	public int updateCustomer(CustomerVO customerVO) {
		int result = customerDao.updateCustomer(CustomerVO.convertVOToModel(customerVO));
		return result;
	}*/

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

	private CustomerDetailsVo findCustomerByUserId(int id) {
		return CustomerDetails.convertModelToVO(customerDetailsDao.findCustomerByUserId(id));
	}

}

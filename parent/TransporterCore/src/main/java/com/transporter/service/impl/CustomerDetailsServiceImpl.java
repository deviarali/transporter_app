package com.transporter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.transporter.constants.WebConstants;
import com.transporter.dao.CustomerDetailsDao;
import com.transporter.enums.UserRoleEnum;
import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.model.CustomerDetails;
import com.transporter.model.User;
import com.transporter.service.CustomerDetailsService;
import com.transporter.service.UserService;
import com.transporter.utility.TransporterUtility;
import com.transporter.vo.CustomerDetailsVo;
import com.transporter.vo.UserRoleVo;
import com.transporter.vo.UserVo;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService{

	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerDetailsDao customerDetailsDao;
	
	@Autowired
	private TransporterUtility transporterUtility;
	
	@Override
	@Transactional
	public String registerCustomer(CustomerDetailsVo customerDetailsVo) {
		String response = null;
		UserVo userExists = userService.isUserExists(customerDetailsVo.getUser().getMobileNumber());
		if(null != userExists) {
			throw new BusinessException(ErrorCodes.MOEXISTS.name(), ErrorCodes.MOEXISTS.value());
		}
		UserVo userVo = customerDetailsVo.getUser();
		UserRoleVo userRoleVo = new UserRoleVo();
		userRoleVo.setId(UserRoleEnum.CUSTOMER.getId());
		userVo.setUserRoleVo(userRoleVo);
		User user = userService.registerUser(userVo);

		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setUser(user);
		customerDetailsDao.save(customerDetails);
		if(customerDetails.getId() > 0) {
			int generate = generateOtp(customerDetailsVo.getUser().getMobileNumber());
			if(generate == 1) {
				response = WebConstants.SUCCESS;
			}
		}
		return response;
	}

	@Override
	public UserVo isUserExists(CustomerDetailsVo customerDetailsVo) {
		return userService.isUserExists(customerDetailsVo.getUser().getMobileNumber());
	}
	
	@Override
	public int generateOtp(String mobile) {
		return userService.generateOtp(mobile);
	}

	@Override
	@Transactional
	public CustomerDetailsVo validateOtp(String mobile, String otp) {
		CustomerDetailsVo customerDetailsVo = null;
		UserVo userVo = userService.validateOtp(mobile, otp);
		if(userVo == null) {
			throw new BusinessException(ErrorCodes.INVALIDOTP.name(), ErrorCodes.INVALIDOTP.value());
		}
		customerDetailsVo = findCustomerByUserId(userVo.getId());
		return customerDetailsVo;
	}
	
	@Override
	@Transactional
	public CustomerDetailsVo updateCustomer(CustomerDetailsVo customerDetailsVo) {
		User user = userService.updateUser(customerDetailsVo.getUser());

		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setUser(user);
		customerDetailsDao.saveOrUpdate(customerDetails);		
		return CustomerDetails.convertModelToVO(customerDetails);
	}

	@Transactional
	private CustomerDetailsVo findCustomerByUserId(int id) {
		CustomerDetails customerDetails = customerDetailsDao.findCustomerByUserId(id);
		CustomerDetailsVo customerDetailsVo = CustomerDetails.convertModelToVO(customerDetails);
		return customerDetailsVo;
	}

	@Override
	public String updateProfilePicture(MultipartFile multipartFile, String mobileNumber) {
		UserVo user = userService.isUserExists(mobileNumber);
		if(user == null) {
			throw new BusinessException(ErrorCodes.UNFOUND.name(), ErrorCodes.UNFOUND.value());
		}
		String generateFilePathAndStore = transporterUtility.generateFilePathAndStore(multipartFile, "customer");
		if(!StringUtils.isNullOrEmpty(generateFilePathAndStore)) {
			int updated = userService.updateProfilePicture(mobileNumber, generateFilePathAndStore);
			if(updated != 0) {
				return generateFilePathAndStore;
			}
		}
		return null;
	}

}

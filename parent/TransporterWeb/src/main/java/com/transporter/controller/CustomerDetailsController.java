package com.transporter.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.jdbc.StringUtils;
import com.transporter.constants.CommonConstants;
import com.transporter.constants.WebConstants;
import com.transporter.response.CommonResponse;
import com.transporter.service.CustomerDetailsService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.CustomerDetailsVo;
import com.transporter.vo.UserVo;

/**
 * @author Devappa.Arali
 *
 */

@RestController
public class CustomerDetailsController {
	
	private static final Logger LOG = LoggerFactory
	        .getLogger(CustomerDetailsController.class);
	
	@Autowired
	private CustomerDetailsService customerDetailsService;
	
	@RequestMapping(value = "customer/registerCustomer", method = RequestMethod.POST)
	public CommonResponse registerCustomer(@RequestBody CustomerDetailsVo customerDetailsVo)
	{
		CommonResponse response = null;
		
		Map<String, Object> map = validateCustomer(customerDetailsVo);
		
		if(!map.isEmpty())
		{
			response = RestUtils.wrapObjectForFailure(map, "validation error", WebConstants.WEB_RESPONSE_ERORR);
			LOG.error("Validation missing");
			return response;
		}
		
		UserVo userVo = customerDetailsService.isUserExists(customerDetailsVo);
		if(null != userVo)
		{
			response = RestUtils.wrapObjectForFailure("user already exists with role : "+customerDetailsVo.getUserVo().getUserroleVo().getRoleDecription(), "error", WebConstants.WEB_RESPONSE_ERORR);
			return response;
		}
		
		CustomerDetailsVo customerDetailsVo2 = customerDetailsService.registerCustomer(customerDetailsVo);
		if(customerDetailsVo2 != null)
		{
			response = RestUtils.wrapObjectForSuccess(customerDetailsVo2);
			LOG.info("Owner registed successfully "+customerDetailsVo.getUserVo().getFirstName());
		}
		else
		{
			response = RestUtils.wrapObjectForFailure("Not registered", "error", WebConstants.WEB_RESPONSE_ERORR);
			LOG.error("Owner not registed "+customerDetailsVo
					.getUserVo().getFirstName());
		}
		
		return response;
	}
	
	/*@RequestMapping(value = "customer/login", method = RequestMethod.POST)
	public CommonResponse login(@RequestBody UserVO userVO)
	{
		CommonResponse response = null;
		
		Map<String, Object> map = validateLogin(userVO);
		if(!map.isEmpty())
		{
			response = RestUtils.wrapObjectForFailure(map, "validation error", WebConstants.WEB_RESPONSE_ERORR);
			LOG.error("Login Validation missing");
		}
		else
		{
			CustomerVO customerVO = customerDetailsService.login(userVO);
			if(null != customerVO)
			{
				response = RestUtils.wrapObjectForSuccess(customerVO);
				LOG.info("Logged in successfully "+customerVO.getFirstName());
			}
			else
			{
				response = RestUtils.wrapObjectForFailure("invalid credentials", "error", WebConstants.WEB_RESPONSE_ERORR);
				LOG.error("Invalid credentials "+userVO.getUserName());
			}
		}
		return response;
	}
	
	@RequestMapping(value = "customer/updateCustomer", method = RequestMethod.POST)
	public CommonResponse updateCustomer(@RequestBody CustomerVO customerVO)
	{
		CommonResponse response = null;
		
		try {
			int result = customerDetailsService.updateCustomer(customerVO);
			if(result != 0) {
				LOG.info("Updated successfully");
				response = RestUtils.wrapObjectForSuccess("success");
			} else {
				response = RestUtils.wrapObjectForFailure("Not updated, invalid customer id", "error", WebConstants.WEB_RESPONSE_ERORR);
			}
			return response;
		}
		catch (Exception exception)
		{		
			response = RestUtils.wrapObjectForFailure("Exception occured", "error", WebConstants.WEB_RESPONSE_ERORR);
			LOG.error("Exception occured while updating customer : "+customerVO.getFirstName());
		}
		
		return response;
	}
	
	@RequestMapping(value = "customer/generateOtp", method = RequestMethod.POST)
	public CommonResponse generateOtp(@RequestParam String mobile)
	{
		CommonResponse response = null;
		int generated = customerDetailsService.generateOtp(mobile);
		if(generated != 0)
			response = RestUtils.wrapObjectForSuccess("success");
		else
			response = RestUtils.wrapObjectForFailure("Otp not generated, invalid user", "error", WebConstants.WEB_RESPONSE_ERORR);
		return response;
	}
	
	@RequestMapping(value = "customer/validateOtp", method = RequestMethod.POST)
	public CommonResponse validateOtp(@RequestParam String mobile, @RequestParam String otp)
	{
		CommonResponse response = null;
		CustomerVO customerVO = customerDetailsService.validateOtp(mobile, otp);
		if(customerVO != null)
			response = RestUtils.wrapObjectForSuccess(customerVO);
		else
			response = RestUtils.wrapObjectForFailure("invalid otp", "error", WebConstants.WEB_RESPONSE_ERORR);
		return response;
	}*/

	/*private Map<String, Object> validateLogin(UserVO userVO) {
		Map<String, Object> map = new HashMap<>();
		if(userVO == null)
		{
			map.put("All", CommonConstants.ALL_FIELDS_REQUIRED);
		}
		else
		{
			if(StringUtils.isNullOrEmpty(userVO.getUserName()) || StringUtils.isEmptyOrWhitespaceOnly(userVO.getUserName()))
			{
				map.put("mobileNumber", CommonConstants.MOBILE_NUMBER_EMPTY);
			}
			if(StringUtils.isNullOrEmpty(userVO.getPassword()) || StringUtils.isEmptyOrWhitespaceOnly(userVO.getPassword()))
			{
				map.put("password", CommonConstants.PASSWORD_EMPTY);
			}
		}
		return map;
	}*/

	private Map<String, Object> validateCustomer(CustomerDetailsVo customerDetailsVo) {
		Map<String, Object> map = new HashMap<>();
		if(customerDetailsVo == null)
		{
			map.put("All", CommonConstants.ALL_FIELDS_REQUIRED);
		}
		else
		{
			if(StringUtils.isNullOrEmpty(customerDetailsVo.getUserVo().getFirstName()))
			{
				map.put("firstName", CommonConstants.FIRST_NAME_EMPTY);
			}
			/*if(StringUtils.isNullOrEmpty(customerDetailsVo.getUserVo().getEmailId()) || StringUtils.isEmptyOrWhitespaceOnly(customerDetailsVo.getUserVo().getEmailId()))
			{
				map.put("email", CommonConstants.EMAIL_EMPTY);
			}*/
			if(StringUtils.isNullOrEmpty(customerDetailsVo.getUserVo().getMobileNumber()) || StringUtils.isEmptyOrWhitespaceOnly(customerDetailsVo.getUserVo().getMobileNumber()))
			{
				map.put("mobileNumber", CommonConstants.MOBILE_NUMBER_EMPTY);
			}
			/*if(StringUtils.isNullOrEmpty(customerVO.getUser().getPassword()) || StringUtils.isEmptyOrWhitespaceOnly(customerVO.getUser().getPassword()))
			{
				map.put("user.password", CommonConstants.PASSWORD_EMPTY);
			}*/
		}
		return map;
	}
}

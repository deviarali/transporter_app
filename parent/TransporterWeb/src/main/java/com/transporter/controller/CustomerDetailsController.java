package com.transporter.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.transporter.constants.CommonConstants;
import com.transporter.constants.WebConstants;
import com.transporter.exceptions.BusinessException;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDetailsController.class);

	@Autowired
	private CustomerDetailsService customerDetailsService;

	@RequestMapping(value = "customer/registerCustomer", method = RequestMethod.POST)
	public CommonResponse registerCustomer(@RequestBody CustomerDetailsVo customerDetailsVo) {
		CommonResponse response = null;
		try {
			String created = customerDetailsService.registerCustomer(customerDetailsVo);
			if(!StringUtils.isNullOrEmpty(created)) {
				response = RestUtils.wrapObjectForSuccess(created);
			} else {
				response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR,"internal server error");
				LOGGER.error("customer not created. Mobile number : "+customerDetailsVo.getUser().getMobileNumber());
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, be.getErrorCode(), be.getErrorMsg());
			LOGGER.error("user already exists. Mobile number : "+customerDetailsVo.getUser().getMobileNumber());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR,e.getMessage());
			LOGGER.error("Internal server error. Mobile number : "+customerDetailsVo.getUser().getMobileNumber());
		}

		return response;
	}

	@RequestMapping(value = "customer/updateCustomer", method = RequestMethod.PATCH)
	public CommonResponse updateCustomer(@RequestBody CustomerDetailsVo customerDetailsVo) {
		CommonResponse response = null;

		Map<String, Object> map = validateCustomer(customerDetailsVo);

		if (!map.isEmpty()) {
			response = RestUtils.wrapObjectForFailure(map, "validation error", WebConstants.WEB_RESPONSE_ERROR);
			LOGGER.error("Validation missing");
			return response;
		}

		UserVo userVo = customerDetailsService.isUserExists(customerDetailsVo);
		if (null != userVo) {

			CustomerDetailsVo updatedCustomerDetailsVo = customerDetailsService.updateCustomer(customerDetailsVo);

			if (updatedCustomerDetailsVo != null) {
				response = RestUtils.wrapObjectForSuccess(customerDetailsVo);
				LOGGER.info("Customer details updated successfully " + customerDetailsVo.getUser().getFirstName());
			} else {
				response = RestUtils.wrapObjectForFailure("customer not found", "error",
						WebConstants.WEB_RESPONSE_ERROR);
				LOGGER.error("customer not found " + customerDetailsVo.getUser().getFirstName());
			}
		} else {
			response = RestUtils.wrapObjectForFailure("Customer not found", "error", WebConstants.WEB_RESPONSE_ERROR);
			LOGGER.error("customer not found " + customerDetailsVo.getUser().getFirstName());
		}

		return response;
	}

	@RequestMapping(value = "customer/generateOtp", method = RequestMethod.POST)
	public CommonResponse generateOtp(@RequestParam String mobileNumber) {
		CommonResponse response = null;
		int generated = customerDetailsService.generateOtp(mobileNumber);
		if (generated != 0)
			response = RestUtils.wrapObjectForSuccess("success");
		else
			response = RestUtils.wrapObjectForFailure("Otp not generated, invalid user", "error",
					WebConstants.WEB_RESPONSE_ERROR);
		return response;
	}

	@RequestMapping(value = "customer/validateOtp", method = RequestMethod.POST)
	public CommonResponse validateOtp(@RequestParam String mobileNumber, @RequestParam String otp) {
		CommonResponse response = null;
		try {
			CustomerDetailsVo customerDetailsVo = customerDetailsService.validateOtp(mobileNumber, otp);
			if (customerDetailsVo != null)
				response = RestUtils.wrapObjectForSuccess(customerDetailsVo);
			else
				response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR, WebConstants.INVALID_USER);
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value = "customer/updateProfilePicture")
	public CommonResponse updateProfilePicture(HttpServletRequest req, @RequestParam(name = "file") MultipartFile multipartFile) {
		CommonResponse response = null;
		String mobileNumber = req.getParameter("mobileNumber");
		try {
			String updateProfilePicture = customerDetailsService.updateProfilePicture(multipartFile, mobileNumber);
			if(!StringUtils.isNullOrEmpty(updateProfilePicture)) {
				response = RestUtils.wrapObjectForSuccess(updateProfilePicture);
			} else {
				response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR, WebConstants.NOT_UPDATED);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR, WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			LOGGER.error("Update profile picture error, mobile number : "+mobileNumber +" exception is : "+e.getMessage());
		}
		
		return response;
	}

	private Map<String, Object> validateCustomer(CustomerDetailsVo customerDetailsVo) {
		Map<String, Object> map = new HashMap<>();
		if (customerDetailsVo == null) {
			map.put("All", CommonConstants.ALL_FIELDS_REQUIRED);
		} else {
			if (StringUtils.isNullOrEmpty(customerDetailsVo.getUser().getFirstName())) {
				map.put("firstName", CommonConstants.FIRST_NAME_EMPTY);
			}
			if (StringUtils.isNullOrEmpty(customerDetailsVo.getUser().getMobileNumber())
					|| StringUtils.isEmptyOrWhitespaceOnly(customerDetailsVo.getUser().getMobileNumber())) {
				map.put("mobileNumber", CommonConstants.MOBILE_NUMBER_EMPTY);
			}
		}
		return map;
	}
}

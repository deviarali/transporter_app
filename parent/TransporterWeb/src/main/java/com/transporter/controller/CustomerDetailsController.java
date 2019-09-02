package com.transporter.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	private static final Logger LOG = LoggerFactory.getLogger(CustomerDetailsController.class);

	@Autowired
	private CustomerDetailsService customerDetailsService;

	@RequestMapping(value = "customer/registerCustomer", method = RequestMethod.POST)
	public CommonResponse registerCustomer(@RequestBody CustomerDetailsVo customerDetailsVo) {
		CommonResponse response = null;

		Map<String, Object> map = validateCustomer(customerDetailsVo);

		if (!map.isEmpty()) {
			response = RestUtils.wrapObjectForFailure(map, "validation error", WebConstants.WEB_RESPONSE_ERROR);
			LOG.error("Validation missing");
			return response;
		}

		UserVo userVo = customerDetailsService.isUserExists(customerDetailsVo);
		if (null != userVo) {
			response = RestUtils.wrapObjectForFailure(
					"user already exists with role : "
							+ customerDetailsVo.getUserVo().getUserroleVo().getRoleDecription(),
					"error", WebConstants.WEB_RESPONSE_ERROR);
			return response;
		}

		CustomerDetailsVo customerDetailsVo2 = customerDetailsService.registerCustomer(customerDetailsVo);
		if (customerDetailsVo2 != null) {
			response = RestUtils.wrapObjectForSuccess(customerDetailsVo2);
			LOG.info("Owner registed successfully " + customerDetailsVo.getUserVo().getFirstName());
		} else {
			response = RestUtils.wrapObjectForFailure("Not registered", "error", WebConstants.WEB_RESPONSE_ERROR);
			LOG.error("Owner not registed " + customerDetailsVo.getUserVo().getFirstName());
		}

		return response;
	}

	@RequestMapping(value = "customer/updateCustomer", method = RequestMethod.PATCH)
	public CommonResponse updateCustomer(@RequestBody CustomerDetailsVo customerDetailsVo) {
		CommonResponse response = null;

		Map<String, Object> map = validateCustomer(customerDetailsVo);

		if (!map.isEmpty()) {
			response = RestUtils.wrapObjectForFailure(map, "validation error", WebConstants.WEB_RESPONSE_ERROR);
			LOG.error("Validation missing");
			return response;
		}

		UserVo userVo = customerDetailsService.isUserExists(customerDetailsVo);
		if (null != userVo) {

			CustomerDetailsVo updatedCustomerDetailsVo = customerDetailsService.updateCustomer(customerDetailsVo);

			if (updatedCustomerDetailsVo != null) {
				response = RestUtils.wrapObjectForSuccess(customerDetailsVo);
				LOG.info("Customer details updated successfully " + customerDetailsVo.getUserVo().getFirstName());
			} else {
				response = RestUtils.wrapObjectForFailure("customer not found", "error",
						WebConstants.WEB_RESPONSE_ERROR);
				LOG.error("customer not found " + customerDetailsVo.getUserVo().getFirstName());
			}
		} else {
			response = RestUtils.wrapObjectForFailure("Customer not found", "error", WebConstants.WEB_RESPONSE_ERROR);
			LOG.error("customer not found " + customerDetailsVo.getUserVo().getFirstName());
		}

		return response;
	}

	@RequestMapping(value = "customer/generateOtp", method = RequestMethod.POST)
	public CommonResponse generateOtp(@RequestParam String mobile) {
		CommonResponse response = null;
		int generated = customerDetailsService.generateOtp(mobile);
		if (generated != 0)
			response = RestUtils.wrapObjectForSuccess("success");
		else
			response = RestUtils.wrapObjectForFailure("Otp not generated, invalid user", "error",
					WebConstants.WEB_RESPONSE_ERROR);
		return response;
	}

	@RequestMapping(value = "customer/validateOtp", method = RequestMethod.POST)
	public CommonResponse validateOtp(@RequestParam String mobile, @RequestParam String otp) {
		CommonResponse response = null;
		CustomerDetailsVo customerDetailsVo = customerDetailsService.validateOtp(mobile, otp);
		if (customerDetailsVo != null)
			response = RestUtils.wrapObjectForSuccess(customerDetailsVo);
		else
			response = RestUtils.wrapObjectForFailure("invalid otp", "error", WebConstants.WEB_RESPONSE_ERROR);
		return response;
	}

	private Map<String, Object> validateCustomer(CustomerDetailsVo customerDetailsVo) {
		Map<String, Object> map = new HashMap<>();
		if (customerDetailsVo == null) {
			map.put("All", CommonConstants.ALL_FIELDS_REQUIRED);
		} else {
			if (StringUtils.isNullOrEmpty(customerDetailsVo.getUserVo().getFirstName())) {
				map.put("firstName", CommonConstants.FIRST_NAME_EMPTY);
			}
			if (StringUtils.isNullOrEmpty(customerDetailsVo.getUserVo().getMobileNumber())
					|| StringUtils.isEmptyOrWhitespaceOnly(customerDetailsVo.getUserVo().getMobileNumber())) {
				map.put("mobileNumber", CommonConstants.MOBILE_NUMBER_EMPTY);
			}
		}
		return map;
	}
}

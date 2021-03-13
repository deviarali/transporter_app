package com.transporter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

@CrossOrigin(origins = "*")
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
			if (!StringUtils.isBlank(created)) {
				response = RestUtils.wrapObjectForSuccess(created);
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
				LOGGER.error("customer not created. Mobile number : " + customerDetailsVo.getUser().getMobileNumber());
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
			LOGGER.error("user already exists. Mobile number : " + customerDetailsVo.getUser().getMobileNumber());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
			LOGGER.error("Internal server error. Mobile number : " + customerDetailsVo.getUser().getMobileNumber());
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
		try {
			int generated = customerDetailsService.generateOtp(mobileNumber);
			if (generated == 1)
				response = RestUtils.wrapObjectForSuccess("success");
			else
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
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
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.INVALID_USER);
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
		}
		return response;
	}

	private Map<String, Object> validateCustomer(CustomerDetailsVo customerDetailsVo) {
		Map<String, Object> map = new HashMap<>();
		if (customerDetailsVo == null) {
			map.put("All", CommonConstants.ALL_FIELDS_REQUIRED);
		} else {
			if (StringUtils.isBlank(customerDetailsVo.getUser().getFirstName())) {
				map.put("firstName", CommonConstants.FIRST_NAME_EMPTY);
			}
			if (StringUtils.isBlank(customerDetailsVo.getUser().getMobileNumber())
					|| StringUtils.isBlank(customerDetailsVo.getUser().getMobileNumber())) {
				map.put("mobileNumber", CommonConstants.MOBILE_NUMBER_EMPTY);
			}
		}
		return map;
	}

	@PutMapping(value = "/customer/updateCustomer")
	public CommonResponse updateUserProfile(@RequestBody UserVo userVo) {
		CommonResponse response = null;
		CustomerDetailsVo customerDetails = customerDetailsService.updateUserProfile(userVo);
		if (customerDetails == null) {
			response = RestUtils.wrapObjectForFailure("user not found", WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.WEB_RESPONSE_NO_RECORD_FOUND);
		} else {
			response = RestUtils.wrapObjectForSuccess(customerDetails);
		}
		return response;
	}
	@GetMapping(value = "/customer/customers")
	public CommonResponse getAllCustomers(@RequestParam(name = "status", required = false, defaultValue = "1") int status) {
		CommonResponse response = null;
		List<CustomerDetailsVo> customerDetails = customerDetailsService.getAllCustomers(status);
		if (customerDetails == null || customerDetails.isEmpty() ) {
			response = RestUtils.wrapObjectForFailure("customers not found", WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.WEB_RESPONSE_NO_RECORD_FOUND);
		} else {
			response = RestUtils.wrapObjectForSuccess(customerDetails);
		}
		return response;
	}

	/**
	 * Below api to get customer by user id
	 * 
	 * @author naveen
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/v1/customer/getCustomerByUserId/{id}")
	public CommonResponse getCustomerByUserId(@PathVariable(name = "id") int id) {
		CommonResponse response = null;
		CustomerDetailsVo customerDetails = customerDetailsService.findCustomerByUserId(id);
		if (customerDetails == null) {
			response = RestUtils.wrapObjectForFailure("Customer not found", WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.WEB_RESPONSE_NO_RECORD_FOUND);
		} else {
			response = RestUtils.wrapObjectForSuccess(customerDetails);
		}
		return response;
	}

	@RequestMapping(value = "/customer/getCustomerById/{id}", method = RequestMethod.GET)
	public CommonResponse getCustomerById(@PathVariable("id") int id) {
		CommonResponse response = null;
		CustomerDetailsVo customerDetails = null;

		try {
			customerDetails = customerDetailsService.getUserById(id);
			if (customerDetails == null) {
				response = RestUtils.wrapObjectForFailure("user not found", WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.WEB_RESPONSE_NO_RECORD_FOUND);
			} else {
				response = RestUtils.wrapObjectForSuccess(customerDetails);
			}
		} catch (BusinessException exception) {
			LOGGER.error("customer not found by " + id);
			response = RestUtils.wrapObjectForFailure(null, exception.getErrorCode(), exception.getErrorMsg());
		} catch (Exception exception) {
			LOGGER.error("customer not found by " + id + " message : " + exception.getMessage());
			response = RestUtils.wrapObjectForFailure(null, "Error", WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}

		return response;
	}

	/**
	 * Get top customer from last 7 days
	 * 
	 * @author naveen
	 * @param count
	 * @return
	 */
	@GetMapping(path = "/v1/customer/getTopCustomerForWeek")
	public CommonResponse getTopCustomerForWeek(@RequestParam(name = "count", required = false) Integer count) {
		CommonResponse response = null;
		if (count == null) {
			count = 5;
		}
		try {
			List<CustomerDetailsVo> customerDetailsVo = customerDetailsService.getTopCustomerForWeek(count);
			response = RestUtils.wrapObjectForSuccess(customerDetailsVo);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Internal error while fetching top customer for a week: " + e.getMessage());
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}
	
	
	@DeleteMapping("/customer/{id}")
	public CommonResponse deleteCustomer(@PathVariable(name = "id", required = true) int id,
										@RequestParam(name = "reason", required = true) String reason) {
		CommonResponse response = null;
		try {
			int deleted = customerDetailsService.deleteCustomer(id, reason);
			if(deleted == 1) {
				response = RestUtils.wrapObjectForSuccess("success");
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						"Customer not deleted");
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Internal error while deleting customer: " + e.getMessage());
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}
}

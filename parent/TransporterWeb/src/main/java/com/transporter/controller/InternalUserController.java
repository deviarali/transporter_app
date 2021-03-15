package com.transporter.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.constants.WebConstants;
import com.transporter.exceptions.BusinessException;
import com.transporter.response.CommonResponse;
import com.transporter.service.InternalUserService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.DriverDetailsVo;
import com.transporter.vo.InternalUserDetailsVo;

/**
 * @author Devappa.Arali
 *
 */

@RestController
public class InternalUserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InternalUserController.class);
	
	@Autowired
	private InternalUserService internalUserService;
	
	@RequestMapping(name = "user/createInternalUser", method = RequestMethod.POST)
	public CommonResponse createInternalUser(@RequestBody InternalUserDetailsVo internalUserDetailsVo) {
		CommonResponse response = null;
		try {
			String created = internalUserService.createInternalUser(internalUserDetailsVo);
			if(!StringUtils.isBlank(created)) {
				response = RestUtils.wrapObjectForSuccess(created);
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,"internal server error");
				LOGGER.error("Internal server error. Mobile number : "+internalUserDetailsVo.getUser().getMobileNumber());
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
			LOGGER.error("Internal user not created. Mobile number : "+internalUserDetailsVo.getUser().getMobileNumber());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,e.getMessage());
			//LOGGER.error("Internal server error. Mobile number : "+internalUserDetailsVo.getUser().getMobileNumber());
		}
		return response;
	}
	
	@GetMapping("employee/{id}/drivers")
	public CommonResponse getDriversForEmployee(@PathVariable(name = "id") int id) {
		CommonResponse response = null;
		List<DriverDetailsVo> driverDetailsList = internalUserService.getDriversForEmployee(id);
		response = RestUtils.wrapObjectForSuccess(driverDetailsList);
		return response;
	}
}

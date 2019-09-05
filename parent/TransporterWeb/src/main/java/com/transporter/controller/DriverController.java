package com.transporter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.jdbc.StringUtils;
import com.transporter.constants.WebConstants;
import com.transporter.exceptions.BusinessException;
import com.transporter.response.CommonResponse;
import com.transporter.service.DriverService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.DriverDetailsVo;

/**
 * @author Devappa.Arali
 *
 */

@RestController
public class DriverController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DriverController.class);
	
	@Autowired
	private DriverService driverService;
	
	@RequestMapping(value = "driver/registerDriver", method = RequestMethod.POST)
	public CommonResponse registerDriver(@RequestBody DriverDetailsVo driverDetailsVo) {
		CommonResponse response = null;

		try {
			String saved = driverService.registerDriver(driverDetailsVo);
			if(!StringUtils.isNullOrEmpty(saved)) {
				response = RestUtils.wrapObjectForSuccess(saved);
				LOGGER.info("Driver registered successfully");
			}
		} catch(BusinessException be) {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, be.getErrorCode(), be.getErrorMsg());
			LOGGER.error("Driver not registered for mobile number :"+driverDetailsVo.getUser().getMobileNumber() +" exception : "+be.getErrorMsg());
		} catch(Exception e) {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, null, e.getMessage());
			LOGGER.error("Driver not registered for mobile number :"+driverDetailsVo.getUser().getMobileNumber() +" exception : "+e.getMessage());
		}
		
		return response;
	}
}

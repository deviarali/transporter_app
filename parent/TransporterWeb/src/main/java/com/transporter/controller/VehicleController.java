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
import com.transporter.model.VehicleDetails;
import com.transporter.response.CommonResponse;
import com.transporter.service.DriverService;
import com.transporter.service.VehicleService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.DriverDetailsVo;
import com.transporter.vo.VehicleDetailsVo;

/**
 * @author Devappa.Arali
 *
 */

@RestController
public class VehicleController {

private static final Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);
	
	@Autowired
	private VehicleService vehicleService;
	
	@RequestMapping(value = "vehicle/registerVehicle", method = RequestMethod.POST)
	public CommonResponse registerVehicle(@RequestBody VehicleDetailsVo vehicleDetailsVo) {
		CommonResponse response = null;

		try {
			String saved = vehicleService.registerVehicle(vehicleDetailsVo);
			if(!StringUtils.isNullOrEmpty(saved)) {
				response = RestUtils.wrapObjectForSuccess(saved);
				LOGGER.info("Vehicle registered successfully");
			}
			else
			{
				response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR,WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
				LOGGER.error("Vehicle not registered"+vehicleDetailsVo.getVehicleNum());
			}
		} catch(BusinessException be) {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, be.getErrorCode(), be.getErrorMsg());
			LOGGER.error("Vehicle already exists :"+vehicleDetailsVo.getVehicleNum() +" exception : "+be.getErrorMsg());
		} catch(Exception e) {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, null, e.getMessage());
			LOGGER.error("Vehicle not registered"+vehicleDetailsVo.getVehicleNum() +" exception : "+e.getMessage());
		}
		
		return response;
	}
	
	@RequestMapping(value="vehicle/updateVehicle", method=RequestMethod.PATCH)
	public CommonResponse updateVehicleDetails(@RequestBody VehicleDetailsVo vehicleDetailsVo)
	{
		CommonResponse response = null;
		
			VehicleDetailsVo updateVechile = vehicleService.updateVehicleDetails(vehicleDetailsVo);
		 
			if (updateVechile == null) {
				response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.WEB_RESPONSE_NO_RECORD_FOUND);
			} else {
				response = RestUtils.wrapObjectForSuccess(updateVechile);
			}
			return response;
		
	}
}

package com.transporter.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.constants.WebConstants;
import com.transporter.exceptions.BusinessException;
import com.transporter.response.CommonResponse;
import com.transporter.service.VehicleService;
import com.transporter.utils.RestUtils;
import com.transporter.utils.Utils;
import com.transporter.vo.FetchSelectedVehiclesRequest;
import com.transporter.vo.FetchSelectedVehiclesResponse;
import com.transporter.vo.VehicleDetailsVo;
import com.transporter.vo.VehiclesByOrderRequest;
import com.transporter.vo.VehiclesByOrderResponse;

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
			if(!StringUtils.isBlank(saved)) {
				response = RestUtils.wrapObjectForSuccess(saved);
				LOGGER.info("Vehicle registered successfully");
			}
			else
			{
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
				LOGGER.error("Vehicle not registered"+vehicleDetailsVo.getVehicleNum());
			}
		} catch(BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
			LOGGER.error("Vehicle already exists :"+vehicleDetailsVo.getVehicleNum() +" exception : "+be.getErrorMsg());
		} catch(Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
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
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.WEB_RESPONSE_NO_RECORD_FOUND);
			} else {
				response = RestUtils.wrapObjectForSuccess(updateVechile);
			}
			return response;	
	}
	
	@RequestMapping(value="vehicle/fetchVehiclesByOrder", method = RequestMethod.POST)
	public CommonResponse fetchVehiclesByOrder(@RequestBody VehiclesByOrderRequest vehiclesByOrderRequest) {
    	CommonResponse response = null;
    	try {
	    	List<VehiclesByOrderResponse> orderResponse = vehicleService.fetchVehiclesByOrder(vehiclesByOrderRequest);
	    	if(!Utils.isNullOrEmpty(orderResponse)) {
	    		response = RestUtils.wrapObjectForSuccess(orderResponse);
	    	} else {
	    		response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, WebConstants.VEHICLES_NOT_AVAILABLE);
	    	}
    	} catch (Exception e) {
    		response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
    	}
    	return response;
    }
	
	@RequestMapping(value = "/vehicle/fetchSelectedVehicles", method = RequestMethod.POST)
	public CommonResponse fetchSelectedVehicles(@RequestBody FetchSelectedVehiclesRequest fetchSelectedVehiclesRequest) {
		CommonResponse response = null;
		try {
			List<FetchSelectedVehiclesResponse> responseList = vehicleService.fetchSelectedVehicles(fetchSelectedVehiclesRequest);
			if(!Utils.isNullOrEmpty(responseList)) {
				response = RestUtils.wrapObjectForSuccess(responseList);
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, WebConstants.VEHICLES_NOT_AVAILABLE);
			}
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}
}

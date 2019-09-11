package com.transporter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.constants.WebConstants;
import com.transporter.model.CancelReasons;
import com.transporter.model.VehicleType;
import com.transporter.response.CommonResponse;
import com.transporter.service.DisplayVehicleService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.VehicleTypeVo;

@RestController
public class DisplayVehicleController {

	@Autowired
	DisplayVehicleService displayVehicleService;

	@RequestMapping(value = "display/getAllDisplayVehicle")
	public CommonResponse getAllDisplayVehicle() {

		CommonResponse response = null;
		List<VehicleTypeVo> displayVehicleList = displayVehicleService.getAllDisplayVehicle();
		if (displayVehicleList != null && displayVehicleList.size() > 0) {
			response = RestUtils.wrapObjectForSuccess(displayVehicleList);
		} else {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR,WebConstants.VEHICLE_NOT_UPDATED);

		}
		return response;
	}
	

	@RequestMapping(value = "display/addDisplayVehicle",method = RequestMethod.POST)
	public CommonResponse addDisplayVehicle(@RequestBody VehicleTypeVo displayVehicleVo) {

		CommonResponse response = null;
		VehicleTypeVo displayVehicle = displayVehicleService.addDisplayVehicle(displayVehicleVo);
		if (displayVehicle != null) {
			response = RestUtils.wrapObjectForSuccess(displayVehicle);
		} else {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.VEHICLE_FAILED_TO_DISPLAY);

		}
		return response;
	}
	
	
	@RequestMapping(value = "display/updateisplayVehicle",method = RequestMethod.PUT)
	public CommonResponse updateDisplayVehicle(@RequestBody VehicleTypeVo displayVehicleVo) {

		CommonResponse response = null;
		VehicleTypeVo displayVehicle = displayVehicleService.updateDisplayVehicle(displayVehicleVo);
		if (displayVehicle != null) {
			response = RestUtils.wrapObjectForSuccess(displayVehicle);
		} else {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.VEHICLE_FAILED_TO_DISPLAY);

		}
		return response;
	}

}

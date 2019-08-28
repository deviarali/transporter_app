package com.transporter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.constants.WebConstants;
import com.transporter.model.CancelReasons;
import com.transporter.model.DisplayVehicle;
import com.transporter.response.CommonResponse;
import com.transporter.service.DisplayVehicleService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.DisplayVehicleVo;

@RestController
public class DisplayVehicleController {

	@Autowired
	DisplayVehicleService displayVehicleService;

	@RequestMapping(value = "display/getAllDisplayVehicle")
	public CommonResponse getAllDisplayVehicle() {

		CommonResponse response = null;
		List<DisplayVehicle> displayVehicleList = displayVehicleService.getAllDisplayVehicle();
		if (displayVehicleList != null && displayVehicleList.size() > 0) {
			response = RestUtils.wrapObjectForSuccess(displayVehicleList);
		} else {
			response = RestUtils.wrapObjectForFailure("Display vehicle not found", "error",
					WebConstants.WEB_RESPONSE_ERORR);

		}
		return response;
	}
	

	@RequestMapping(value = "display/addDisplayVehicle",method = RequestMethod.POST)
	public CommonResponse addDisplayVehicle(@RequestBody DisplayVehicleVo displayVehicleVo) {

		CommonResponse response = null;
		DisplayVehicle displayVehicle = displayVehicleService.addDisplayVehicle(displayVehicleVo);
		if (displayVehicle != null) {
			response = RestUtils.wrapObjectForSuccess(displayVehicle);
		} else {
			response = RestUtils.wrapObjectForFailure("Failed to add display ", "error",
					WebConstants.WEB_RESPONSE_ERORR);

		}
		return response;
	}
	
	
	@RequestMapping(value = "display/updateisplayVehicle",method = RequestMethod.PUT)
	public CommonResponse updateDisplayVehicle(@RequestBody DisplayVehicleVo displayVehicleVo) {

		CommonResponse response = null;
		DisplayVehicle displayVehicle = displayVehicleService.updateDisplayVehicle(displayVehicleVo);
		if (displayVehicle != null) {
			response = RestUtils.wrapObjectForSuccess(displayVehicle);
		} else {
			response = RestUtils.wrapObjectForFailure("Failed to add display ", "error",
					WebConstants.WEB_RESPONSE_ERORR);

		}
		return response;
	}

}

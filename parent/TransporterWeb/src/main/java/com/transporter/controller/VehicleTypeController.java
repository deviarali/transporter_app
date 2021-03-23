package com.transporter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.constants.WebConstants;
import com.transporter.exceptions.BusinessException;
import com.transporter.response.CommonResponse;
import com.transporter.service.VehicleTypeService;
import com.transporter.utils.RestUtils;
import com.transporter.utils.Utils;
import com.transporter.vo.FetchSelectedVehiclesResponse;
import com.transporter.vo.VehicleTypeVo;
import com.transporter.vo.VehiclesByOrderRequest;
import com.transporter.vo.VehiclesByOrderResponse;


@CrossOrigin("*")
@RestController
public class VehicleTypeController {

	@Autowired
	VehicleTypeService vehicleTypeService;

	@RequestMapping(value = "vehicleType/getAllVehicleTypes")
	public CommonResponse getAllDisplayVehicle() {

		CommonResponse response = null;
		List<VehicleTypeVo> displayVehicleList = vehicleTypeService.getAllVehicleTypes();
		if (displayVehicleList != null && displayVehicleList.size() > 0) {
			response = RestUtils.wrapObjectForSuccess(displayVehicleList);
		} else {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,WebConstants.VEHICLE_NOT_UPDATED);

		}
		return response;
	}
	
	@RequestMapping(value = "vehicleType/addVehicleType",method = RequestMethod.POST)
	public CommonResponse addVehicleType(@RequestBody VehicleTypeVo vehicleTypeVo) {

		CommonResponse response = null;
		try {
			String saved = vehicleTypeService.addVehicleType(vehicleTypeVo);
			if (!Utils.isNullOrEmpty(saved)) {
				response = RestUtils.wrapObjectForSuccess(saved);
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.VEHICLE_TYPE_NOT_SAVED);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.VEHICLE_TYPE_NOT_SAVED);
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}
	
	
	@RequestMapping(value = "display/updateisplayVehicle",method = RequestMethod.PUT)
	public CommonResponse updateDisplayVehicle(@RequestBody VehicleTypeVo displayVehicleVo) {

		CommonResponse response = null;
		VehicleTypeVo displayVehicle = vehicleTypeService.updateDisplayVehicle(displayVehicleVo);
		if (displayVehicle != null) {
			response = RestUtils.wrapObjectForSuccess(displayVehicle);
		} else {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.VEHICLE_TYPE_NOT_UPDATED);

		}
		return response;
	}

}

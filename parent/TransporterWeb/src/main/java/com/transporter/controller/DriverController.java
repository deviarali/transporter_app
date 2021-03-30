package com.transporter.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.transporter.constants.WebConstants;
import com.transporter.exceptions.BusinessException;
import com.transporter.response.CommonResponse;
import com.transporter.response.LatitudeLongitudeResponse;
import com.transporter.service.DriverService;
import com.transporter.service.VehicleService;
import com.transporter.utils.RestUtils;
import com.transporter.utils.Utils;
import com.transporter.vo.DriverDetailsVo;

/**
 * @author Devappa.Arali
 *
 */


@CrossOrigin(origins = "*")
@RestController
public class DriverController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DriverController.class);

	@Autowired
	private DriverService driverService;

	@Autowired
	private VehicleService vehicleService;

	@RequestMapping(value = "driver/registerDriver", method = RequestMethod.POST)
	public CommonResponse registerDriver(@RequestBody DriverDetailsVo driverDetailsVo) {
		CommonResponse response = null;

		try {
			String saved = driverService.registerDriver(driverDetailsVo);
			if (!StringUtils.isBlank(saved)) {
				response = RestUtils.wrapObjectForSuccess(saved);
				LOGGER.info("Driver registered successfully");	
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
			LOGGER.error("Driver not registered for mobile number :" + driverDetailsVo.getUser().getMobileNumber()
					+ " exception : " + be.getErrorMsg());
		} catch (Exception e) {
			e.getStackTrace();
			e.printStackTrace();
			response = RestUtils.wrapObjectForFailure(null, null, e.getMessage());
			LOGGER.error("Driver not registered for mobile number :" + driverDetailsVo.getUser().getMobileNumber()
					+ " exception : " + e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "driver/updateLattitudeAndLongitude", method = RequestMethod.POST)
	public CommonResponse updateLattitudeAndLongitude(@RequestParam(value = "id", required = true) int id,
			@RequestParam(value = "lattitude", required = true) String lattitude,
			@RequestParam(value = "longitude", required = true) String longitude) {
		CommonResponse response = null;
		try {
			String updated = driverService.updateLattitudeAndLongitude(id, lattitude, longitude);
			if (!Utils.isNullOrEmpty(updated)) {
				response = RestUtils.wrapObjectForSuccess(updated);
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.NOT_UPDATED);
			}
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}

	@PutMapping(value = "/driver/updateDocuments/{userId}")
	public CommonResponse updateDriverDocuments(@PathVariable("userId") int userId,
			@RequestParam(name = "adhar") MultipartFile adharMultiPart,
			@RequestParam(name = "dl") MultipartFile dlMultiPart) {
		CommonResponse response = null;
		try {
			String updated = driverService.updateDriverDocuments(userId, adharMultiPart, dlMultiPart);
			if (!StringUtils.isBlank(updated)) {
				response = RestUtils.wrapObjectForSuccess(updated);
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.NOT_UPDATED);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			LOGGER.error("Update Driver documents error, User id : " + userId + " exception is : " + e.getMessage());
		}
		return response;
	}

	@PutMapping(value = "/driver/onAndOffRoad/{driverId}")
	public CommonResponse updateDriverOnRoadAndOffRoad(@PathVariable("driverId") int driverId,
			@RequestBody DriverDetailsVo detailsVo) {
		CommonResponse response = null;
		try {
			DriverDetailsVo details = driverService.updateDriverOnRoadAndOffRoad(driverId, detailsVo);
			if (details != null) {
				response = RestUtils.wrapObjectForSuccess(details);
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.NOT_UPDATED);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		}
		return response;
	}

	@PutMapping(value = "/driver/updateDriverDetails")
	public CommonResponse updateDriverAddress(@RequestBody DriverDetailsVo driverDetailsVo) {
		CommonResponse response = null;
		DriverDetailsVo detailsVo = driverService.updateDriverAddress(driverDetailsVo);
		if (detailsVo == null) {
			response = RestUtils.wrapObjectForFailure("user not found", WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.WEB_RESPONSE_NO_RECORD_FOUND);
		} else {
			response = RestUtils.wrapObjectForSuccess(detailsVo);
		}
		return response;
	}

	@RequestMapping(value = "driver/checkVehicleAvailability", method = RequestMethod.POST)
	public CommonResponse checkVehicleAvailability(@RequestParam(name = "lattitude") String lattitude,
			@RequestParam(name = "longitude") String longitude) {
		CommonResponse response = null;
		String check = driverService.checkVehicleAvailability(lattitude, longitude);
		response = RestUtils.wrapObjectForSuccess(check);
		return response;
	}

	/*
	 * @RequestMapping(value="driver/fetchVehiclesByOrder") public CommonResponse
	 * fetchVehiclesByOrder(@RequestBody VehiclesByOrderRequest
	 * vehiclesByOrderRequest) { CommonResponse response = null; try {
	 * List<VehiclesByOrderResponse> orderResponse =
	 * driverService.fetchVehiclesByOrder(vehiclesByOrderRequest);
	 * if(!Utils.isNullOrEmpty(orderResponse)) { response =
	 * RestUtils.wrapObjectForSuccess(orderResponse); } else { response =
	 * RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
	 * WebConstants.VEHICLES_NOT_AVAILABLE); } } catch (Exception e) { response =
	 * RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
	 * WebConstants.INTERNAL_SERVER_ERROR_MESSAGE); } return response; }
	 */

	@PostMapping("/driver/generateOtp")
	public CommonResponse generateOtp(@RequestParam String mobileNumber) {
		CommonResponse response = null;
		try {
			int generated = driverService.generateOtp(mobileNumber);
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

	@PostMapping("/driver/validateOtp")
	public CommonResponse validateOtp(@RequestParam String mobileNumber, @RequestParam String otp) {
		CommonResponse response = null;
		try {
			DriverDetailsVo driverDetailsVo = driverService.validateOtp(mobileNumber, otp);
			if (driverDetailsVo != null)
				response = RestUtils.wrapObjectForSuccess(driverDetailsVo);
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

	@GetMapping("/driver/getDriverLocation/{driverId}")
	public CommonResponse getDriverLocation(@PathVariable("driverId") int driverId) {
		CommonResponse response = null;
		try {
			LatitudeLongitudeResponse details = vehicleService.getDriverLocations(driverId);
			if (details != null) {
				response = RestUtils.wrapObjectForSuccess(details);
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.NOT_UPDATED);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		}
		return response;
	}

	@GetMapping("/driver/getAllDrivers")
	public CommonResponse getAllDrivers(@RequestParam(name = "status", required = false, defaultValue = "1") int status) {
		CommonResponse commonResponse = null;
		try {
			List<DriverDetailsVo> driverDetailsVo = driverService.getAllDrivers(status);
			commonResponse = RestUtils.wrapObjectForSuccess(driverDetailsVo);
		} catch (BusinessException be) {
			commonResponse = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		}
		return commonResponse;
	}

	@GetMapping("/driver/getDriverById/{driverId}")
	public CommonResponse getDriverById(@PathVariable(required = true) int driverId) {
		CommonResponse commonResponse = null;
		try {
			DriverDetailsVo driverDetailsVo = driverService.getDriverById(driverId);
			commonResponse = RestUtils.wrapObjectForSuccess(driverDetailsVo);
		} catch (BusinessException be) {
			commonResponse = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		}
		return commonResponse;
	}

	/**
	 * Get top drivers from last 7 days
	 * 
	 * @author naveen
	 * @param count
	 * @return
	 */
	@GetMapping(path = "/v1/driver/getTopDriversForWeek")
	public CommonResponse getTopDriversForWeek(@RequestParam(name = "count", required = false) Integer count) {
		CommonResponse response = null;
		if (count == null) {
			count = 5;
		}
		try {
			List<DriverDetailsVo> driverDetailsVo = driverService.getTopDriversForWeek(count);
			response = RestUtils.wrapObjectForSuccess(driverDetailsVo);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Internal error while fetching top driver for a week: " + e.getMessage());
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}
	
	@DeleteMapping("/driver/{id}")
	public CommonResponse deleteCustomer(@PathVariable(name = "id", required = true) int id,
										@RequestParam(name = "reason", required = true) String reason) {
		CommonResponse response = null;
		try {
			int deleted = driverService.deleteDriver(id, reason);
			if(deleted == 1) {
				response = RestUtils.wrapObjectForSuccess("success");
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						"Driver not deleted");
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Internal error while deleting driver: " + e.getMessage());
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}
	
	@GetMapping("/drivers/registerVehicle/{userId}")
	public CommonResponse getDriverForVehicleRegistrationByUserId(@PathVariable(name = "userId") int userId) {
		CommonResponse response = null;
		List<DriverDetailsVo> drivers = driverService.getDriverForVehicleRegistrationByUserId(userId);
		response = RestUtils.wrapObjectForSuccess(drivers);
		return response;
	}
	
	
	@PutMapping("/driver/updateverificationstatus")
	public CommonResponse updateVerificationStatus(@RequestParam(name = "driverId") int driverId,
			 @RequestParam(name = "status") String status) {
		CommonResponse commonResponse = null;
		int updated = driverService.updateVerifcationStatus(driverId, status);
		commonResponse = RestUtils.wrapObjectForSuccess(updated);
		return commonResponse;
	}
}

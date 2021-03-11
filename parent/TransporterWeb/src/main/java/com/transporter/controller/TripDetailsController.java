package com.transporter.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.constants.WebConstants;
import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.model.TripDetails;
import com.transporter.response.CommonResponse;
import com.transporter.service.TripDetailsService;
import com.transporter.utils.RestUtils;
import com.transporter.utils.Utils;
import com.transporter.vo.DeliveryStatusVo;
import com.transporter.vo.DriverReachedVo;
import com.transporter.vo.TripCancelledVo;
import com.transporter.vo.TripDetailsConfirmResponse;
import com.transporter.vo.TripDetailsVo;

/**
 * @author Devappa.Arali
 *
 */

@RestController
public class TripDetailsController {

	private static Logger LOG = LoggerFactory.getLogger(TripDetailsController.class);

	@Autowired
	TripDetailsService tripDetailsService;

	@RequestMapping(value = "trip/tripHistory/{id}/{tripstatus}", method = RequestMethod.GET)
	public CommonResponse getHistoryDetails(@PathVariable("id") int id, @PathVariable("tripstatus") int tripstatus,
			@RequestParam(name = "fromDate", required = false) String fromDate,
			@RequestParam(name = "toDate", required = false) String toDate,
			@RequestParam(name = "userType", required = true) String userType) {
		CommonResponse response = null;

		List<TripDetailsVo> tripHistoryList = tripDetailsService.getTripHistory(id, tripstatus, fromDate, toDate);
		if (tripHistoryList != null && tripHistoryList.size() > 0) {
			response = RestUtils.wrapObjectForSuccess(tripHistoryList);
		} else {
			response = RestUtils.wrapObjectForFailure(null, "error", "Trip History not found");
		}

		return response;
	}

	@RequestMapping(value = "trip/tripHistoryOfPassenger/{id}/{tripstatus}", method = RequestMethod.GET)
	public CommonResponse getPassengerHistoryDetails(@PathVariable("id") int id,
			@PathVariable("tripstatus") int tripstatus,
			@RequestParam(name = "fromDate", required = false) String fromDate,
			@RequestParam(name = "toDate", required = false) String toDate,
			@RequestParam(name = "userType", required = true) String userType) {
		CommonResponse response = null;

		List<TripDetailsVo> tripHistoryList = tripDetailsService.getTripPassengerHistory(id, tripstatus, fromDate,
				toDate);
		if (tripHistoryList != null && tripHistoryList.size() > 0) {
			response = RestUtils.wrapObjectForSuccess(tripHistoryList);
		} else {
			response = RestUtils.wrapObjectForFailure(null, "error", "Trip History not found");
		}

		return response;
	}

	@RequestMapping(value = "trip/confirmBooking", method = RequestMethod.POST)
	public CommonResponse confirmBooking(@RequestBody TripDetailsVo tripDetailsVo) {
		CommonResponse response = null;
		try {
			TripDetailsConfirmResponse tripDetailsConfirmResponse = tripDetailsService.confirmBooking(tripDetailsVo);
			if (null != tripDetailsConfirmResponse) {
				response = RestUtils.wrapObjectForSuccess(tripDetailsConfirmResponse);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			LOG.error("Exception while confirmbooking " + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@PutMapping(value = "/trip/{tripId}/ratings/{ratings:.+}")
	public CommonResponse updateTripRating(@PathVariable("tripId") int tripId,
			@PathVariable("ratings") String ratings) {
		CommonResponse response = null;
		try {
			TripDetails updateTripRatings = tripDetailsService.updateTripRatings(tripId, ratings);
			if (updateTripRatings != null) {
				response = RestUtils.wrapObjectForSuccess("Rating updated sucessfully");
			} else {
				response = RestUtils.wrapObjectForFailure(null, "error", WebConstants.WEB_RESPONSE_ERROR);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		}
		return response;
	}

	@PutMapping(value = "/trip/{tripId}/status/{deliveryStatusId}")
	public CommonResponse updateTripStatus(@PathVariable("tripId") int tripId,
			@PathVariable("deliveryStatusId") int deliveryStatusId) {
		CommonResponse commonResponse = null;
		try {
			String response = tripDetailsService.updateTripStatus(tripId, deliveryStatusId);
			if (!Utils.isNullOrEmpty(response)) {
				commonResponse = RestUtils.wrapObjectForSuccess(response);
			} else {
				commonResponse = RestUtils.wrapObjectForFailure(null, "error", WebConstants.WEB_RESPONSE_ERROR);
			}
		} catch (BusinessException be) {
			commonResponse = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			commonResponse = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			LOG.error("Exception while update trip status " + e.getMessage());
			e.printStackTrace();
		}
		return commonResponse;
	}

	@PutMapping(value = "/trip/{tripId}/cancelStatus/{deliveryStatusId}")
	public CommonResponse updateTripCancelledStatus(@PathVariable("tripId") int tripId,
			@PathVariable("deliveryStatusId") int deliveryStatusId, @RequestBody DeliveryStatusVo deliveryStatusVo) {
		CommonResponse response = null;
		try {
			String updateTripstatus = tripDetailsService.updateTripCancelledStatus(tripId, deliveryStatusId,
					deliveryStatusVo);
			if (updateTripstatus != null) {
				response = RestUtils.wrapObjectForSuccess(updateTripstatus);
			} else {
				response = RestUtils.wrapObjectForFailure("Trip details not found", "error",
						WebConstants.WEB_RESPONSE_ERROR);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		}
		return response;
	}

	@RequestMapping(value = "trip/validateTripOtp", method = RequestMethod.POST)
	public CommonResponse validateTripOtp(@RequestParam int tripId, @RequestParam String otp,
			@RequestParam String status) {
		CommonResponse response = null;
		try {
			String tripStatus = tripDetailsService.validateStartEndOtp(tripId, otp, status);
			if (tripStatus.equals("Success")) {
				response = RestUtils.wrapObjectForSuccess(tripStatus);
			} else {
				response = RestUtils.wrapObjectForFailure(null, "error", WebConstants.INVALID_STATUS);

			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
			LOG.error("Trip Id Not Found in ValidateOTP:" + " exception : " + be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, null, e.getMessage());
			LOG.error("Trip Id Not Found In ValidateOTP :" + " exception : " + e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "trip/CancelledTrip", method = RequestMethod.PUT)
	public CommonResponse tripCancelledByDriver(@RequestBody TripCancelledVo tripCancelledVo) {
		CommonResponse response = null;
		try {
			String updateTripstatus = tripDetailsService.tripCancelledStatus(tripCancelledVo);
			if (updateTripstatus != null) {
				response = RestUtils.wrapObjectForSuccess(updateTripstatus);
			} else {
				response = RestUtils.wrapObjectForFailure(null, "error", WebConstants.WEB_RESPONSE_ERROR);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		}
		return response;
	}

	@RequestMapping(value = "trip/isDriverReachedLocation", method = RequestMethod.POST)
	public CommonResponse isDriverReachedLocation(@RequestBody DriverReachedVo driverReachedVo) {
		CommonResponse response = null;
		try {
			boolean isDriverReachedLocation = tripDetailsService.isDriverReachedLocation(driverReachedVo);
			if (isDriverReachedLocation) {
				response = RestUtils.wrapObjectForSuccess(WebConstants.SUCCESS);
			} else {
				response = RestUtils.wrapObjectForFailure(null, "error", WebConstants.DRIVER_NOT_REACHED_LOCATION);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		}
		return response;
	}

	@RequestMapping(value = "trip/sendTripInvoice/{tripId}", method = RequestMethod.POST)
	public CommonResponse sendTripInvoice(@PathVariable("tripId") int tripId) {
		CommonResponse response = null;
		try {
			if (tripId != 0) {
				boolean isMainSent = tripDetailsService.sendInvoiceToMail(tripId);
				if (isMainSent) {
					response = RestUtils.wrapObjectForSuccess(WebConstants.INVOICE_SENT_SUCCESSFULLY);
				} else {
					response = RestUtils.wrapObjectForFailure(null, "error", WebConstants.UNABLE_TO_SEND_EMAIL);
				}
			} else {
				throw new BusinessException(ErrorCodes.TRIPIDNOTFOUND.name(), ErrorCodes.TRIPIDNOTFOUND.value());
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		}
		return response;
	}

	/**
	 * Below api to get a total ongoing and completed ride in a day
	 * 
	 * @author naveen
	 * @return
	 */
	@GetMapping(path = "/v1/trip/getRideCount")
	public CommonResponse getTotalDayAllRideNumber() {
		CommonResponse response = null;
		try {
			Integer totalDayAllRideNumber = tripDetailsService.getTotalDayAllRideNumber();
			response = RestUtils.wrapObjectForSuccess(totalDayAllRideNumber);
		} catch (BusinessException be) {
			be.printStackTrace();
			LOG.error("Exception while fetching a no of rides for a day: " + be.getMessage());
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		}
		return response;
	}
}

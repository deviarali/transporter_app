package com.transporter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.constants.WebConstants;
import com.transporter.exceptions.BusinessException;
import com.transporter.model.TripDetails;
import com.transporter.response.CommonResponse;
import com.transporter.service.TripDetailsService;
import com.transporter.utils.RestUtils;
import com.transporter.utils.Utils;
import com.transporter.vo.DeliveryStatusVo;
import com.transporter.vo.DriverDetailsVo;
import com.transporter.vo.TripDetailsConfirmResponse;
import com.transporter.vo.TripDetailsHistoryVo;
import com.transporter.vo.TripDetailsVo;

/**
 * @author Devappa.Arali
 *
 */

@RestController
public class TripDetailsController {

	@Autowired
	TripDetailsService tripDetailsService;

	@RequestMapping(value = "trip/tripHistory/{id}/{tripstatus}", method = RequestMethod.GET)
	public CommonResponse getHistoryDetails(@PathVariable("id") int id, @PathVariable("tripstatus") int tripstatus,
			@RequestParam(name = "fromDate", required = false) String fromDate,
			@RequestParam(name = "toDate", required = false) String toDate) {
		CommonResponse response = null;

		List<TripDetailsHistoryVo> tripHistoryList = tripDetailsService.getTripHistory(id, tripstatus, fromDate,
				toDate);
		if (tripHistoryList != null && tripHistoryList.size() > 0) {
			response = RestUtils.wrapObjectForSuccess(tripHistoryList);
		} else {
			response = RestUtils.wrapObjectForFailure("Trip History not found", "error",
					WebConstants.WEB_RESPONSE_ERROR);
		}

		return response;
	}
	
	@RequestMapping(value = "trip/confirmBooking", method = RequestMethod.POST)
	public CommonResponse confirmBooking(@RequestBody TripDetailsVo tripDetailsVo) {
		CommonResponse response = null;
		try {
			TripDetailsConfirmResponse tripDetailsConfirmResponse = tripDetailsService.confirmBooking(tripDetailsVo);
			if(null != tripDetailsConfirmResponse) {
				response = RestUtils.wrapObjectForSuccess(tripDetailsConfirmResponse);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
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
				commonResponse = RestUtils.wrapObjectForFailure(null, "error",
						WebConstants.WEB_RESPONSE_ERROR);
			}
		} catch (BusinessException be) {
			commonResponse = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			commonResponse = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
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

}

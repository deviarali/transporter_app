package com.transporter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.constants.WebConstants;
import com.transporter.model.TripDetails;
import com.transporter.response.CommonResponse;
import com.transporter.service.TripDetailsService;
import com.transporter.utils.RestUtils;

/**
 * @author Devappa.Arali
 *
 */

@RestController
public class TripDetailsController {

	@Autowired
	TripDetailsService tripDetailsService;
	
	@RequestMapping(value = "trip/tripHistory/{id}/{tripstatus}", method = RequestMethod.GET)
	public CommonResponse getHistoryDetails(@PathVariable("id") int id ,@PathVariable("tripstatus") int tripstatus) {
		CommonResponse response = null;
		
		
		List<TripDetails> tripHistoryList = tripDetailsService.getTripHistory(id,tripstatus);
		if (tripHistoryList != null && tripHistoryList.size() > 0) {
			response = RestUtils.wrapObjectForSuccess(tripHistoryList);
		} else {
			response = RestUtils.wrapObjectForFailure("Trip History not found", "error", WebConstants.WEB_RESPONSE_ERROR);
		}

		return response;
	}

	
}

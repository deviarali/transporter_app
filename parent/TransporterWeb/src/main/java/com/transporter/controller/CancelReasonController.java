package com.transporter.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.constants.WebConstants;
import com.transporter.model.CancelReasons;
import com.transporter.response.CommonResponse;
import com.transporter.service.CancelReasonService;
import com.transporter.utils.RestUtils;

@RestController
public class CancelReasonController {

	private static final Logger LOG = LoggerFactory.getLogger(CancelReasonController.class);

	@Autowired
	private CancelReasonService cancelReasonService;

	@RequestMapping(value = "cancel/cancelReasons", method = RequestMethod.GET)
	public CommonResponse getAllCancelReasons() {
		LOG.debug("cancel reason called");
		CommonResponse response = null;
		List<CancelReasons> listOfCancelReasons = cancelReasonService.cancelReasons();
		if (listOfCancelReasons != null && listOfCancelReasons.size() > 0) {
			response = RestUtils.wrapObjectForSuccess(cancelReasonService.cancelReasons());
		} else {
			response = RestUtils.wrapObjectForFailure("No cancel reasons found", "error",
					WebConstants.WEB_RESPONSE_ERROR);

		}
		return response;
	}

	@RequestMapping(value = "cancel/saveCancelReasons", method = RequestMethod.POST)
	public CommonResponse addCancelReasons(@Valid @RequestBody CancelReasons reason) {
		LOG.debug("add cancel reason called");
		CommonResponse response = null;
		if(!reason.getCancelReason().isEmpty()){
			String status  = cancelReasonService.addCancelReaseon(reason);
			if (status != null && !status.isEmpty()) {
				response = RestUtils.wrapObjectForSuccess(status);
			} else {
				response = RestUtils.wrapObjectForFailure("Unanble to add reason", "error",
						WebConstants.WEB_RESPONSE_ERROR);
			}
		}
		return response;
	}
	
	@RequestMapping(value = "cancel/deleteCancelReasons", method = RequestMethod.DELETE)
	public CommonResponse deleteCancelReasons(@Valid @RequestBody CancelReasons reason) {
		LOG.debug("delete cancel reason called");
		CommonResponse response = null;
		if(reason.getId() != 0){
			int res  = cancelReasonService.deleteCancelReason(reason);
			if (res != 0) {
				response = RestUtils.wrapObjectForSuccess(WebConstants.SUCCESS);
			} else {
				response = RestUtils.wrapObjectForFailure("Unanble to delete reason", "error",
						WebConstants.WEB_RESPONSE_ERROR);

			}
		}
		return response;
	}

	
}

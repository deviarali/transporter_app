package com.transporter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.response.CommonResponse;
import com.transporter.service.CancelReasonService;
import com.transporter.utils.RestUtils;

@RestController
public class CancelReasonController {


	private static final Logger LOG = LoggerFactory
	        .getLogger(CancelReasonController.class);
	
	@Autowired
	private CancelReasonService cancelReasonService;
	
	@RequestMapping(value = "customer/cancelReasons", method = RequestMethod.GET)
	public CommonResponse getAllCancelReasons() {
		LOG.debug("cancel reason called");
		CommonResponse response = null;
		response = RestUtils.wrapObjectForSuccess(cancelReasonService.cancelReasons());		
		return response;
	}
	
}

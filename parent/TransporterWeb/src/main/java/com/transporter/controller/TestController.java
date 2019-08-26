package com.transporter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.response.CommonResponse;
import com.transporter.service.TestService;
import com.transporter.utils.RestUtils;

@RestController
public class TestController {

	@Autowired
	private TestService testService;
	
	@RequestMapping(name = "/", method = RequestMethod.GET)
	public CommonResponse getString()
	{
		System.out.println("Dev");
		CommonResponse commonResponse = null;
		String message = testService.getMessage();
		commonResponse = RestUtils.wrapObjectForSuccess(message);
		return commonResponse;
	}
}

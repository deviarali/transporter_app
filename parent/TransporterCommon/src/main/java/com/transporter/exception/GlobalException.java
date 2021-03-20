package com.transporter.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.transporter.constants.WebConstants;
import com.transporter.exceptions.BusinessException;
import com.transporter.response.CommonResponse;
import com.transporter.utils.RestUtils;

@RestControllerAdvice
public class GlobalException {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalException.class);
	
	@ExceptionHandler(Exception.class)
	public CommonResponse handleException(Exception e) {
		CommonResponse response =  null;
		e.printStackTrace();
		response = RestUtils.wrapObjectForFailure(null, WebConstants.INTERNAL_SERVER_ERROR_CODE, WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		return response;
	}
	
	@ExceptionHandler(BusinessException.class)
	public CommonResponse handleBusinessException(BusinessException be) {
		CommonResponse response =  null;
		LOGGER.error("Business exception " +be.getErrorMsg());
		response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		return response;
	}
}

package com.transporter.service;

import com.transporter.model.ExceptionMaster;
import com.transporter.model.ExceptionMasterExecution;

public interface ExceptionService {

	ExceptionMaster getExceptionMasterByType(String exceptionType);

	ExceptionMasterExecution putExceptionMasterIntoExecution(
	        ExceptionMasterExecution exceptionMasterExecution);

}

package com.transporter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.dao.ExceptionMasterDao;
import com.transporter.dao.ExceptionMasterExecutionDao;
import com.transporter.model.ExceptionMaster;
import com.transporter.model.ExceptionMasterExecution;
import com.transporter.service.ExceptionService;

/**
 * @author Devappa.Arali
 *
 */

@Service
public class ExceptionServiceImpl implements ExceptionService {

	@Autowired
	ExceptionMasterDao exceptionMasterDao;

	@Autowired
	ExceptionMasterExecutionDao exceptionMasterExecutionDao;

	@Override
	@Transactional(readOnly = true)
	public ExceptionMaster getExceptionMasterByType(String exceptionType) {
		return exceptionMasterDao.getExceptionMasterByType(exceptionType);
	}

	@Override
	@Transactional
	public ExceptionMasterExecution putExceptionMasterIntoExecution(
	        ExceptionMasterExecution exceptionMasterExecution) {
		Integer exceptionMasterExecutionId = (Integer) exceptionMasterExecutionDao
		        .save(exceptionMasterExecution);
		exceptionMasterExecution.setId(exceptionMasterExecutionId);
		return exceptionMasterExecution;

	}
}

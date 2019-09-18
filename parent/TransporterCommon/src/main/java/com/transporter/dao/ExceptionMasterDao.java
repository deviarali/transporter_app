package com.transporter.dao;

import com.transporter.model.ExceptionMaster;

/**
 * @author Devappa.Arali
 *
 */

public interface ExceptionMasterDao extends GenericDao {

	public ExceptionMaster getExceptionMasterByType(String exceptionType);
}

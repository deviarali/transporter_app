package com.transporter.vo;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the exceptionmasterexecution database table.
 * 
 */
public class ExceptionMasterExecutionVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String exceptionClass;

	private String exceptionMessage;

	private Date exceptionTime;

	private ExceptionMasterVo exceptionMasterVo;

	public ExceptionMasterExecutionVo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExceptionClass() {
		return this.exceptionClass;
	}

	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	public String getExceptionMessage() {
		return this.exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public Date getExceptionTime() {
		return this.exceptionTime;
	}

	public void setExceptionTime(Date exceptionTime) {
		this.exceptionTime = exceptionTime;
	}

	public ExceptionMasterVo getExceptionMasterVo() {
		return exceptionMasterVo;
	}

	public void setExceptionMasterVo(ExceptionMasterVo exceptionMasterVo) {
		this.exceptionMasterVo = exceptionMasterVo;
	}
	
}
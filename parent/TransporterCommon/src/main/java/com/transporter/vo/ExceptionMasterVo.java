package com.transporter.vo;

import java.io.Serializable;


/**
 * The persistent class for the exceptionmaster database table.
 * 
 */
public class ExceptionMasterVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String description;

	private String exceptionType;

	private String notifyEmail;

	public ExceptionMasterVo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExceptionType() {
		return this.exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getNotifyEmail() {
		return this.notifyEmail;
	}

	public void setNotifyEmail(String notifyEmail) {
		this.notifyEmail = notifyEmail;
	}
}
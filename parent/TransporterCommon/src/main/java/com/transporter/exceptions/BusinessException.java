package com.transporter.exceptions;

/**
 * @author Devappa.Arali
 *
 */

public class BusinessException extends RuntimeException
{
	private String errorCode;
	
	private String errorMsg;
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(String arg)
	{
		super(arg);
	}
	
	public BusinessException(Throwable arg)
	{
		super(arg);
	}
	
	public BusinessException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

	public BusinessException(String errorCode, String errorMsg)
	{
		setErrorCode(errorCode);
		setErrorMsg(errorMsg);
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}

package com.transporter.exceptions;

/**
 * @author Devappa.Arali
 *
 */

public class TechnicalException extends Exception
{
	private String errorCode;
	
	private String errorMsg;
	
	public TechnicalException() {
		super();
	}
	
	public TechnicalException(String arg)
	{
		super(arg);
	}
	
	public TechnicalException(Throwable arg)
	{
		super(arg);
	}
	
	public TechnicalException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

	public TechnicalException(String errorCode, String errorMsg)
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

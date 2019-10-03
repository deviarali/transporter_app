package com.transporter.exception;

/**
 * @author SHARAN A
 */
@SuppressWarnings("serial")
public class TikaServerCustomException extends RuntimeException {

	public TikaServerCustomException() {
		super();
	}

	public TikaServerCustomException(String message) {
		super(message);
	}

	public TikaServerCustomException(String message, Throwable thrw) {
		super(message, thrw);
	}
}

package com.transporter.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the exceptionmaster database table.
 * 
 */
@Entity
@Table(name="exceptionmaster")
public class ExceptionMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="description")
	private String description;

	@Column(name="exception_type")
	private String exceptionType;

	@Column(name="notify_email")
	private String notifyEmail;
	
	@Column(name = "enable_notification")
	private int enableNotification;

	public ExceptionMaster() {
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

	public final int getEnableNotification() {
		return enableNotification;
	}

	public final void setEnableNotification(int enableNotification) {
		this.enableNotification = enableNotification;
	}
}
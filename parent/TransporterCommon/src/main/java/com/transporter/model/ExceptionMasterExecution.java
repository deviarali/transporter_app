package com.transporter.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the exceptionmasterexecution database table.
 * 
 */
@Entity
@Table(name="exceptionmasterexecution")
public class ExceptionMasterExecution implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;

	@Column(name="exception_class")
	private String exceptionClass;

	@Column(name="exception_message")
	private String exceptionMessage;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="exception_time")
	private Date exceptionTime;

	//bi-directional many-to-one association to Exceptionmaster
	@ManyToOne
	@JoinColumn(name="exception_master_id")
	private ExceptionMaster exceptionMaster;

	public ExceptionMasterExecution() {
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

	public ExceptionMaster getExceptionMaster() {
		return exceptionMaster;
	}

	public void setExceptionMaster(ExceptionMaster exceptionMaster) {
		this.exceptionMaster = exceptionMaster;
	}
	
}
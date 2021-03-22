package com.transporter.vo;

import java.util.Date;

public class DocumentsVo {
	
	private int id;
	private String documentType;
	private String documentUrl;
	private DriverDetailsVo driverDetailsVo;
	private Date createdOn;
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDocumentType() {
		return documentType;
	}
	
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	public String getDocumentUrl() {
		return documentUrl;
	}
	
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}
	
	
	public DriverDetailsVo getDriverDetailsVo() {
		return driverDetailsVo;
	}
	
	public void setDriverDetailsVo(DriverDetailsVo driverDetailsVo) {
		this.driverDetailsVo = driverDetailsVo;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	
}

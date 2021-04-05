package com.transporter.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.transporter.vo.DocumentsVo;

@Entity
@Table(name = "documents")
public class Documents {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "document_type")
	private String documentType;

	@Column(name = "document_url")
	private String documentUrl;

	@ManyToOne
	@JoinColumn(name = "driver_id")
	private DriverDetails driverDetails;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_on")
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

	public DriverDetails getDriverDetails() {
		return driverDetails;
	}

	public void setDriverDetails(DriverDetails driverDetails) {
		this.driverDetails = driverDetails;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public static DocumentsVo convertModelToVo(Documents documents) {
		if (documents == null)
			return null;
		DocumentsVo documentsVo = new DocumentsVo();
		documentsVo.setId(documents.getId());
		documentsVo.setDocumentType(documents.getDocumentType());
		documentsVo.setDocumentUrl(documents.getDocumentUrl());
		documentsVo.setDriverDetailsVo(DriverDetails.convertModelToVo(documents.getDriverDetails()));
		documentsVo.setCreatedOn(documents.getCreatedOn());
		return documentsVo;
	}
}

package com.transporter.vo;

import java.io.Serializable;

/**
 * The persistent class for the deliverystatus database table.
 * 
 */
public class DeliveryStatusVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String deliverystatus;

	private String description;

	public DeliveryStatusVo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeliverystatus() {
		return this.deliverystatus;
	}

	public void setDeliverystatus(String deliverystatus) {
		this.deliverystatus = deliverystatus;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
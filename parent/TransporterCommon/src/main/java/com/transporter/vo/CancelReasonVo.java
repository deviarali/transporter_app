package com.transporter.vo;

import java.io.Serializable;

public class CancelReasonVo implements Serializable {

	private int id;
	private String cancelReason;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

}

package com.transporter.vo;

import java.math.BigDecimal;

/**
 * @author SHARAN A
 */
public class ApplyCouponRequestVo {

	private Integer userId;
	private String code;
	private BigDecimal amount;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}

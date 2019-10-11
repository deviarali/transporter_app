package com.transporter.vo;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author SHARAN A
 */
public class CouponResponseVO {

	private String code;
	private BigDecimal doscountAmount;
	private BigDecimal calculatedAmount;
	private String status;

	private Map<String, Object> additionalValues;

	public CouponResponseVO(String code, BigDecimal doscountAmount, BigDecimal calculatedAmount, String status) {
		super();
		this.code = code;
		this.doscountAmount = doscountAmount;
		this.calculatedAmount = calculatedAmount;
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getDoscountAmount() {
		return doscountAmount;
	}

	public void setDoscountAmount(BigDecimal doscountAmount) {
		this.doscountAmount = doscountAmount;
	}

	public BigDecimal getCalculatedAmount() {
		return calculatedAmount;
	}

	public void setCalculatedAmount(BigDecimal calculatedAmount) {
		this.calculatedAmount = calculatedAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, Object> getAdditionalValues() {
		return additionalValues;
	}

	public void setAdditionalValues(Map<String, Object> additionalValues) {
		this.additionalValues = additionalValues;
	}

}

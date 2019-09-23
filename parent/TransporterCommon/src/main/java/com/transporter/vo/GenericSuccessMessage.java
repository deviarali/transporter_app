package com.transporter.vo;

public class GenericSuccessMessage {

	private final String code;
	private final String status;
	private final String Message;

	public GenericSuccessMessage(Builder builder) {
		super();
		this.code = builder.code;
		this.status = builder.status;
		this.Message = builder.Message;
	}

	public String getCode() {
		return code;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return Message;
	}

	public static class Builder {
		private String code;
		private String status;
		private String Message;

		public static Builder newInstance() {
			return new Builder();
		}

		private Builder() {
		}

		public Builder setCode(String code) {
			this.code = code;
			return this;
		}

		public Builder setStatus(String status) {
			this.status = status;
			return this;
		}

		public Builder setMessage(String Message) {
			this.Message = Message;
			return this;
		}

		public GenericSuccessMessage build() {
			return new GenericSuccessMessage(this);
		}

	}

}

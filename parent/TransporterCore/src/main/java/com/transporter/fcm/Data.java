package com.transporter.fcm;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class Data {

	@Expose
	private String body;
	@Expose
	private String message;
	@Expose
	private String notificationType;
	@Expose
	private String title;

	public String getBody() {
		return body;
	}

	public String getMessage() {
		return message;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public String getTitle() {
		return title;
	}

	public static class Builder {

		private String body;
		private String message;
		private String notificationType;
		private String title;

		public Data.Builder withBody(String body) {
			this.body = body;
			return this;
		}

		public Data.Builder withMessage(String message) {
			this.message = message;
			return this;
		}

		public Data.Builder withNotificationType(String notificationType) {
			this.notificationType = notificationType;
			return this;
		}

		public Data.Builder withTitle(String title) {
			this.title = title;
			return this;
		}

		public Data build() {
			Data data = new Data();
			data.body = body;
			data.message = message;
			data.notificationType = notificationType;
			data.title = title;
			return data;
		}

	}

}

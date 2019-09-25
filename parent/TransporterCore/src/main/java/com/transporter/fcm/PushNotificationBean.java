package com.transporter.fcm;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class PushNotificationBean {

	@Expose
	private Data data;
	@Expose
	private Notification notification;

	public Data getData() {
		return data;
	}

	public Notification getNotification() {
		return notification;
	}

	public static class Builder {

		private Data data;
		private Notification notification;

		public PushNotificationBean.Builder withData(Data data) {
			this.data = data;
			return this;
		}

		public PushNotificationBean.Builder withNotification(Notification notification) {
			this.notification = notification;
			return this;
		}

		public PushNotificationBean build() {
			PushNotificationBean pushNotificationBean = new PushNotificationBean();
			pushNotificationBean.data = data;
			pushNotificationBean.notification = notification;
			return pushNotificationBean;
		}

	}

}

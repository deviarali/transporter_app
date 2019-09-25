package com.transporter.fcm;

public class NotificationBuilder {

	public static PushNotificationBean buildPayloadNotification(NotificationType notificationType, String title,
			String message, Object body) {

		Data data = new Data.Builder()
				.withNotificationType(notificationType.getValue())
				.withTitle(title)
				.withMessage(message)
				.withBody(body.toString())
				.build();

		PushNotificationBean bean = new PushNotificationBean.Builder()
				.withData(data)
				.build();

		return bean;

	}

	public static PushNotificationBean buildGenericNotification(String title, NotificationType notificationType,
			String message) {

		Notification notification = new Notification.Builder()
				.withTitle(title)
				.withNotificationType(notificationType.getValue())
				.withMessage(message)
				.build();

		PushNotificationBean bean = new PushNotificationBean.Builder()
				.withNotification(notification)
				.build();

		return bean;

	}

	public static PushNotificationBean buildGenericPayloadNotification(NotificationType notificationType, String title,
			String message, Object body) {

		Notification notification = new Notification.Builder()
				.withTitle(title)
				.withNotificationType(notificationType.getValue())
				.withMessage(message)
				.build();

		Data data = new Data.Builder()
				.withNotificationType(notificationType.getValue()).withTitle(title)
				.withMessage(message)
				.withBody(body.toString())
				.build();

		PushNotificationBean bean = new PushNotificationBean.Builder()
				.withData(data)
				.withNotification(notification)
				.build();

		return bean;

	}

}

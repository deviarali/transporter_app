package com.transporter.fcm;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class Notification {

    @Expose
    private String message;
    @Expose
    private String notificationType;
    @Expose
    private String title;

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

        private String message;
        private String notificationType;
        private String title;

        public Notification.Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Notification.Builder withNotificationType(String notificationType) {
            this.notificationType = notificationType;
            return this;
        }

        public Notification.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Notification build() {
            Notification notification = new Notification();
            notification.message = message;
            notification.notificationType = notificationType;
            notification.title = title;
            return notification;
        }

    }

}

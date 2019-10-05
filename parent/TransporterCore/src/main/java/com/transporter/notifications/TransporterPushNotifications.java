package com.transporter.notifications;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.transporter.fcm.PushNotificationBean;

@Service
public class TransporterPushNotifications {

	@Value("${android.fcm.key}")
	private String androidFcmKey;

	@Value("${android.fcm.url}")
	private String androidFcmUrl;
	
	private static final Logger LOG = LoggerFactory.getLogger(TransporterPushNotifications.class);

	public String pushNotifications(Object devicesToken) {
		String response = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set("Authorization", "key=" + androidFcmKey);
			httpHeaders.set("Content-Type", "application/json");
			JSONObject msg = new JSONObject();
			JSONObject json = new JSONObject();

			msg.put("title", "Test Purpose");
			msg.put("body", "Dev I Arali");
			msg.put("notificationType", "Ganesh");

			json.put("data", msg);
			json.put("to", devicesToken);

			HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(), httpHeaders);
			response = restTemplate.postForObject(androidFcmUrl, httpEntity, String.class);
			System.out.println(response);
		} catch (JSONException e) {
			System.out.println("Log json"+e.getMessage());
			System.out.println("Response "+response);
		} catch (Exception e) {
			System.out.println("Log Exception "+e.getMessage());
			System.out.println("Response "+response);
		}
		return response;
	}

	public String sendPushNotification(Object devicesToken, PushNotificationBean bean) {
		String response = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set("Authorization", "key=" + androidFcmKey);
			httpHeaders.set("Content-Type", "application/json");
			JSONObject json = new JSONObject();
			if(null != bean.getData()) {
				json.put("data", buildDataObject(bean));
			}		
			if(null != bean.getNotification()) {
				json.put("notification", buildNotificationObject(bean));
			}
			json.put("to", devicesToken);

			HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(), httpHeaders);
			response = restTemplate.postForObject(androidFcmUrl, httpEntity, String.class);
			System.out.println(response);
		} catch (JSONException e) {
			LOG.error("Exception in push notification json "+e.getMessage());
		}
		return response;
	}

	private JSONObject buildDataObject(PushNotificationBean bean) {
		JSONObject dataObject = new JSONObject();
		try {
			dataObject.put("title", bean.getData().getTitle());
			dataObject.put("body", bean.getData().getBody());
			dataObject.put("notificationType", bean.getData().getNotificationType());
			dataObject.put("message", bean.getData().getMessage());
		} catch (JSONException e) {
			LOG.error("Exception in push notification data object "+e.getMessage());
		}
		return dataObject;
	}
	
	private JSONObject buildNotificationObject(PushNotificationBean bean) {
		JSONObject notificationObject = new JSONObject();
		try {
			notificationObject.put("title", bean.getNotification().getTitle());
			notificationObject.put("notificationType", bean.getNotification().getNotificationType());
			notificationObject.put("message", bean.getNotification().getMessage());
		} catch (JSONException e) {
			LOG.error("Exception in push notification notification object "+e.getMessage());
		}
		return notificationObject;
	}
}

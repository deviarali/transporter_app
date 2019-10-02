package com.transporter.notifications;

import org.json.JSONException;
import org.json.JSONObject;
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
			json.put("registration_ids", devicesToken);

			HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(), httpHeaders);
			response = restTemplate.postForObject(androidFcmUrl, httpEntity, String.class);
			System.out.println(response);
		} catch (JSONException e) {
			e.printStackTrace();
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
			json.put("data", bean.toString());
			json.put("registration_ids", devicesToken);

			HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(), httpHeaders);
			response = restTemplate.postForObject(androidFcmUrl, httpEntity, String.class);
			System.out.println(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
}

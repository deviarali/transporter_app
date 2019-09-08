package com.transporter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transporter.model.TripDetails;
import com.transporter.notifications.TransporterPushNotifications;
import com.transporter.repo.TripDetailsRepo;
import com.transporter.service.TripDetailsService;

/**
 * @author Devappa.Arali
 *
 */

@Service
public class TripDetailsServiceImpl implements TripDetailsService{

	@Autowired
	TripDetailsRepo  tripDetailsRepo;
	
	@Autowired
	private TransporterPushNotifications transporterPushNotifications;
	
	@Override
	public List<TripDetails> getTripHistory(int id, int tripstatus) 
	{
		List<TripDetails> history = tripDetailsRepo.getHistory(id,tripstatus);
		return history;
	}

	@Override
	public String checkVehicleAvailability(String lattitude, String longitude) {
		
		String devicesToken[] = {"euRR9XSIZCE:APA91bEWht_WfZjySzUCiKDTaQbNNP8smnx7snHy3jeIAFvxVVZqRmsU1ffvjuDBhFEOiXSjMW-UteYXPeECuv8Il6h4SfGWdGnj3KJbgYJLgscs-4f_N7Lxbp72umt_JYak_Q20Bh7V"};
		String response  = transporterPushNotifications.pushNotifications(devicesToken);
		return response;
	}

}

package com.transporter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.fcm.NotificationBuilder;
import com.transporter.fcm.NotificationType;
import com.transporter.fcm.PushNotificationBean;
import com.transporter.model.DeliveryStatus;
import com.transporter.model.TripDetails;
import com.transporter.notifications.TransporterPushNotifications;
import com.transporter.repo.TripDetailsRepo;
import com.transporter.service.TripDetailsService;
import com.transporter.utils.DateTimeUtils;
import com.transporter.vo.DeliveryStatusVo;
import com.transporter.vo.TripDetailsHistoryVo;

/**
 * @author Devappa.Arali
 *
 */

@Service
public class TripDetailsServiceImpl implements TripDetailsService {

	@Autowired
	TripDetailsRepo tripDetailsRepo;
	
	@Autowired
	TransporterPushNotifications transporterPushNotifications;

	@Override
	public List<TripDetailsHistoryVo> getTripHistory(int id, int tripstatus, String fromDate, String toDate) {
		String fromTripStart = null;
		String toTripStart = null;
		List<TripDetails> history = null;
		if (!(StringUtils.isBlank(fromDate)) && !(StringUtils.isBlank(toDate))) {
			fromTripStart = DateTimeUtils.convertToTimestamp(fromDate);
			toTripStart = DateTimeUtils.convertToTimestamp(toDate);
			history = tripDetailsRepo.getHistory(id, tripstatus, fromTripStart, toTripStart);
		} else {
			history = tripDetailsRepo.getHistoryByStatus(id, tripstatus);
		}

		List<TripDetailsHistoryVo> tripDetailsHistoryVos = new ArrayList<>();
		history.forEach(data -> {
			TripDetailsHistoryVo tripDetailsHistory = new TripDetailsHistoryVo();

			tripDetailsHistory.setId(data.getId());
			tripDetailsHistory.setAmount(data.getAmount());
			tripDetailsHistory.setAmountToApp(data.getAmountToApp());
			tripDetailsHistory.setAmountToDriver(data.getAmountToDriver());
			tripDetailsHistory.setCanceledReason(data.getCanceledReason());
			tripDetailsHistory.setCancelledamountFromCustomer(data.getCancelledamountFromCustomer());
			tripDetailsHistory.setCancelledamountFromDriver(data.getCancelledamountFromDriver());
			tripDetailsHistory.setCancelledamountStatus(data.getCancelledamountStatus());
			tripDetailsHistory.setCashMode(data.getCashMode());
			tripDetailsHistory.setDeliverypersonMobile(data.getDeliverypersonMobile());
			tripDetailsHistory.setDeliverypersonName(data.getDeliverypersonName());
			tripDetailsHistory.setDestinationLocation(data.getDestinationLocation());
			tripDetailsHistory.setGoodsType(data.getGoodsType());
			tripDetailsHistory.setGoodsSize(data.getGoodsSize());
			tripDetailsHistory.setPickupLocation(data.getPickupLocation());
			tripDetailsHistory.setRatings(data.getRatings());
			tripDetailsHistory.setTripTime(data.getTripTime());
			tripDetailsHistory.setTripStarttime(data.getTripStarttime());
			tripDetailsHistory.setTripEndtime(data.getTripEndtime());
			tripDetailsHistory.setDriverName(data.getDriverDetails().getDriverName());
			tripDetailsHistory
					.setVehicleName(data.getDriverDetails().getVehicleDetails().getVehicleType().getVehicleName());
			tripDetailsHistoryVos.add(tripDetailsHistory);
		});
		return tripDetailsHistoryVos;
	}

	@Override
	@Transactional
	public TripDetails updateTripRatings(int tripId, String ratings) {

		if (ratings.equals("0.00") || ratings.equals("0.0") || ratings.equals("0")) {
			throw new BusinessException(ErrorCodes.INVALIDRATING.toString());
		}
		TripDetails tripDetails = tripDetailsRepo.findOne(tripId);
		if (tripDetails != null) {
			tripDetails.setRatings(ratings);
			tripDetails = tripDetailsRepo.save(tripDetails);
		}
		return tripDetails;
	}

	@Override
	@Transactional
	public TripDetails updateTripStatus(int tripId, int deliveryStatusId) {
		TripDetails tripDetails = tripDetailsRepo.findOne(tripId);
		if (tripDetails != null) {
			DeliveryStatus deliveryStatus = new DeliveryStatus();
			deliveryStatus.setId(deliveryStatusId);
			tripDetails.setDeliveryStatus(deliveryStatus);
			tripDetails = tripDetailsRepo.save(tripDetails);
		} else {
			throw new BusinessException(ErrorCodes.TRIPDETAILSNOTFOUND.toString());
		}
		return tripDetails;
	}

	@Override
	public String updateTripCancelledStatus(int tripId, int deliveryStatusId, DeliveryStatusVo deliveryStatusVo) {
		TripDetails tripDetails = tripDetailsRepo.findOne(tripId);
		if (tripDetails != null) {
			DeliveryStatus deliveryStatus = new DeliveryStatus();
			deliveryStatus.setId(deliveryStatusId);
			tripDetails.setCanceledReason(deliveryStatusVo.getDeliverystatus());
			tripDetails.setDeliveryStatus(deliveryStatus);
			tripDetails = tripDetailsRepo.save(tripDetails);
			
			if (tripDetails != null) {
				if (deliveryStatusId == 3) {
					PushNotificationBean bean = NotificationBuilder.buildGenericPayloadNotification(NotificationType.BOOKING_CANCELLED, "Booking Cancelled", "Booking Cancelled", "Trip cancled by customer");
					transporterPushNotifications.sendPushNotification(tripDetails.getDriverDetails().getUser().getFcmToken(), bean);
					return "Trip cancled by customer";
				} else {
					PushNotificationBean bean = NotificationBuilder.buildGenericPayloadNotification(NotificationType.BOOKING_CANCELLED, "Booking Cancelled", "Booking Cancelled", "Trip cancled by driver");
					transporterPushNotifications.sendPushNotification(tripDetails.getCustomerDetails().getUser().getFcmToken(), bean);
					return "Trip cancled by driver";
				}
			}
		} else {
			throw new BusinessException(ErrorCodes.TRIPDETAILSNOTFOUND.toString());
		}
		return "Trip details not found";
	}
}

package com.transporter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.Period;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.transporter.dao.TripDetailsDao;
import com.transporter.enums.RidingStatusEnum;
import com.transporter.enums.TripStatusEnum;
import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.fcm.NotificationBuilder;
import com.transporter.fcm.NotificationType;
import com.transporter.fcm.PushNotificationBean;
import com.transporter.model.CustomerDetails;
import com.transporter.model.DeliveryStatus;
import com.transporter.model.DriverDetails;
import com.transporter.model.TripDetails;
import com.transporter.model.User;
import com.transporter.model.VehicleDetails;
import com.transporter.notifications.TransporterPushNotifications;
import com.transporter.repo.TripDetailsRepo;
import com.transporter.service.CustomerDetailsService;
import com.transporter.service.DriverService;
import com.transporter.service.TripDetailsService;
import com.transporter.service.UserService;
import com.transporter.service.VehicleService;
import com.transporter.utils.DateTimeUtils;
import com.transporter.utils.Utils;
import com.transporter.vo.DeliveryStatusVo;
import com.transporter.vo.FetchSelectedVehiclesRequest;
import com.transporter.vo.TripCancelledVo;
import com.transporter.vo.TripDetailsConfirmResponse;
import com.transporter.vo.TripDetailsHistoryVo;
import com.transporter.vo.TripDetailsVo;

/**
 * @author Devappa.Arali
 *
 */

@Service
public class TripDetailsServiceImpl implements TripDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(TripDetailsServiceImpl.class);

	@Autowired
	TripDetailsRepo tripDetailsRepo;

	@Autowired
	TripDetailsDao tripDetailsDao;

	@Autowired
	VehicleService vehicleService;

	@Autowired
	CustomerDetailsService customerDetailsService;

	@Autowired
	DriverService driverService;

	@Autowired
	UserService userService;

	@Autowired
	TransporterPushNotifications transporterPushNotifications;

	@Value("${surrounding.area}")
	private double surroundingDistance;

	@Value("${cgst}")
	private Double cgst;

	@Value("${sgst}")
	private Double sgst;

	@Override
	public List<TripDetailsVo> getTripHistory(int id, int tripStatus, String fromDate, String toDate) {
		String fromTripStart = null;
		String toTripStart = null;
		Gson gson = new Gson();
		List<TripDetails> tripDetailsList = null;
		if (!(StringUtils.isBlank(fromDate)) && !(StringUtils.isBlank(toDate))) {
			fromTripStart = DateTimeUtils.convertToTimestamp(fromDate);
			toTripStart = DateTimeUtils.convertToTimestamp(toDate);
			tripDetailsList = tripDetailsRepo.getHistory(id, tripStatus, fromTripStart, toTripStart);
		} else {
			if (tripStatus == TripStatusEnum.CANCELLED.getTripStatusId()) {
				tripDetailsList = tripDetailsRepo.getCancelledHistory(id);
			} else {
				tripDetailsList = tripDetailsRepo.getHistoryByStatus(id, tripStatus);
			}
		}

		List<TripDetailsVo> tripDetailsVoList = new ArrayList<>();
		tripDetailsList.forEach(data -> {
			TripDetailsVo tripDetailsVo = TripDetails.convertEntityTOVo(data);
			TripDetailsHistoryVo tripDetailsHistoryVo = gson.fromJson(tripDetailsVo.getTripHistoryJson(),
					TripDetailsHistoryVo.class);
			if (null != tripDetailsHistoryVo) {
				if (null != tripDetailsVo.getTripStarttime() && null != tripDetailsVo.getTripEndtime()) {
					Period p = new Period(tripDetailsVo.getTripStarttime().getTime(),
							tripDetailsVo.getTripEndtime().getTime());
					if (p.getMinutes() < 10) {
						tripDetailsHistoryVo
								.setTripHours(String.valueOf(p.getHours()) + ":0" + String.valueOf(p.getMinutes()));
					} else {
						tripDetailsHistoryVo
								.setTripHours(String.valueOf(p.getHours()) + ":" + String.valueOf(p.getMinutes()));
					}
				}
				if (null != tripDetailsHistoryVo.getCgstPercentage()
						&& null != tripDetailsHistoryVo.getSgstPercentage()) {
					Double cgstAmount = Double.valueOf(tripDetailsVo.getAmount())
							* tripDetailsHistoryVo.getCgstPercentage() / 100;
					Double sgstAmount = Double.valueOf(tripDetailsVo.getAmount())
							* tripDetailsHistoryVo.getSgstPercentage() / 100;
					Double rideFare = Double.valueOf(tripDetailsVo.getAmount()) - (cgstAmount + sgstAmount);
					tripDetailsVo.setCgst(cgstAmount);
					tripDetailsVo.setSgst(sgstAmount);
					tripDetailsVo.setRideFare(rideFare);
					tripDetailsHistoryVo.setCgst(10.00);
					tripDetailsHistoryVo.setSgst(10.00);
				}
			}
			tripDetailsVo.setTripDetailsHistory(tripDetailsHistoryVo);
			tripDetailsVoList.add(tripDetailsVo);
		});
		return tripDetailsVoList;
	}

	/*
	 * TripHistoryOfPassenger
	 * 
	 */
	@Override
	public List<TripDetailsVo> getTripPassengerHistory(int id, int tripStatus, String fromDate, String toDate) {
		String fromTripStart = null;
		String toTripStart = null;
		Gson gson = new Gson();
		List<TripDetails> tripDetailsList = null;
		if (!(StringUtils.isBlank(fromDate)) && !(StringUtils.isBlank(toDate))) {
			fromTripStart = DateTimeUtils.convertToTimestamp(fromDate);
			toTripStart = DateTimeUtils.convertToTimestamp(toDate);
			tripDetailsList = tripDetailsRepo.getHistoryOfPassenger(id, tripStatus, fromTripStart, toTripStart);
		} else {
			if (tripStatus == TripStatusEnum.CANCELLED.getTripStatusId()) {
				tripDetailsList = tripDetailsRepo.getCancelledHistory(id);
			}
			tripDetailsList = tripDetailsRepo.getHistoryByStatusOfPassenger(id, tripStatus);
		}

		List<TripDetailsVo> tripDetailsVoList = new ArrayList<>();
		tripDetailsList.forEach(data -> {
			TripDetailsVo tripDetailsVo = TripDetails.convertEntityTOVo(data);
			TripDetailsHistoryVo tripDetailsHistoryVo = gson.fromJson(tripDetailsVo.getTripHistoryJson(),
					TripDetailsHistoryVo.class);
			if (null != tripDetailsHistoryVo) {
				if (null != tripDetailsVo.getTripStarttime() && null != tripDetailsVo.getTripEndtime()) {
					Period p = new Period(tripDetailsVo.getTripStarttime().getTime(),
							tripDetailsVo.getTripEndtime().getTime());
					if (p.getMinutes() < 10) {
						tripDetailsHistoryVo
								.setTripHours(String.valueOf(p.getHours()) + ":0" + String.valueOf(p.getMinutes()));
					} else {
						tripDetailsHistoryVo
								.setTripHours(String.valueOf(p.getHours()) + ":" + String.valueOf(p.getMinutes()));
					}
				}
				Double cgstAmount = Double.valueOf(tripDetailsVo.getAmount()) * tripDetailsHistoryVo.getCgstPercentage()
						/ 100;
				Double sgstAmount = Double.valueOf(tripDetailsVo.getAmount()) * tripDetailsHistoryVo.getSgstPercentage()
						/ 100;
				Double rideFare = Double.valueOf(tripDetailsVo.getAmount()) - (cgstAmount + sgstAmount);
				tripDetailsVo.setCgst(cgstAmount);
				tripDetailsVo.setSgst(sgstAmount);
				tripDetailsVo.setRideFare(rideFare);
				tripDetailsHistoryVo.setCgst(10.00);
				tripDetailsHistoryVo.setSgst(10.00);
			}
			tripDetailsVo.setTripDetailsHistory(tripDetailsHistoryVo);
			tripDetailsVoList.add(tripDetailsVo);
		});
		return tripDetailsVoList;
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
	public String updateTripStatus(int tripId, int deliveryStatusId) {
		String response = "success";
		TripDetails tripDetails = tripDetailsRepo.findOne(tripId);
		if (tripDetails != null) {

			int updatedRows = tripDetailsRepo.updateTripStatus(tripDetails.getId(), deliveryStatusId);

			if (deliveryStatusId == 3 || deliveryStatusId == 4) {
				// driverService.updateRidingStatus(tripDetails.getDriverDetails().getId(),
				// RidingStatusEnum.OFFRIDING.getRidingStatusId());
			}

			if (deliveryStatusId == 3) {
				JSONObject cancelByCustomer = new JSONObject();
				try {
					cancelByCustomer.put("message", "trip has been cancelled by customer");
				} catch (JSONException e) {
					LOG.error("Exception while creating cancelled by customer json object " + e.getMessage());
				}
				PushNotificationBean bean = NotificationBuilder.buildPayloadNotification(
						NotificationType.BOOKING_CANCELLED, "Booking Cancelled", "Booking Cancelled",
						cancelByCustomer.toString());
				transporterPushNotifications
						.sendPushNotification(tripDetails.getDriverDetails().getUser().getFcmToken(), bean, "driver");
			} else if (deliveryStatusId == 4) {
				JSONObject cancelByDriver = new JSONObject();
				try {
					cancelByDriver.put("message", "trip has been cancelled by driver");
				} catch (JSONException e) {
					LOG.error("Exception while creating cancelled by driver json object " + e.getMessage());
				}
				PushNotificationBean bean = NotificationBuilder.buildPayloadNotification(
						NotificationType.BOOKING_CANCELLED, "Booking Cancelled", "Booking Cancelled",
						cancelByDriver.toString());
				transporterPushNotifications.sendPushNotification(
						tripDetails.getCustomerDetails().getUser().getFcmToken(), bean, "customer");
			}

		} else {
			throw new BusinessException(ErrorCodes.TRIPDETAILSNOTFOUND.name(), ErrorCodes.TRIPDETAILSNOTFOUND.value());
		}
		return response;
	}

	@Override
	public String updateTripCancelledStatus(int tripId, int deliveryStatusId, DeliveryStatusVo deliveryStatusVo) {
		TripDetails tripDetails = tripDetailsRepo.findOne(tripId);
		if (tripDetails != null) {
			DeliveryStatus deliveryStatus = new DeliveryStatus();
			deliveryStatus.setId(deliveryStatusId);
			tripDetails.setCancelledReason(deliveryStatusVo.getDeliverystatus());
			tripDetails.setDeliveryStatus(deliveryStatus);
			tripDetails = tripDetailsRepo.save(tripDetails);

			if (deliveryStatusId == 3 || deliveryStatusId == 4) {
				driverService.updateRidingStatus(tripDetails.getDriverDetails().getId(),
						RidingStatusEnum.OFFRIDING.getRidingStatusId());
			}

			if (tripDetails != null) {
				if (deliveryStatusId == 3) {
					PushNotificationBean bean = NotificationBuilder.buildGenericPayloadNotification(
							NotificationType.BOOKING_CANCELLED, "Booking Cancelled", "Booking Cancelled",
							"Trip cancled by customer");
					transporterPushNotifications.sendPushNotification(
							tripDetails.getDriverDetails().getUser().getFcmToken(), bean, "driver");
					return "Trip cancled by customer";
				} else if (deliveryStatusId == 4) {
					PushNotificationBean bean = NotificationBuilder.buildGenericPayloadNotification(
							NotificationType.BOOKING_CANCELLED, "Booking Cancelled", "Booking Cancelled",
							"Trip cancled by driver");
					transporterPushNotifications.sendPushNotification(
							tripDetails.getCustomerDetails().getUser().getFcmToken(), bean, "customer");
					return "Trip cancled by driver";
				}
			}
		} else {
			throw new BusinessException(ErrorCodes.TRIPDETAILSNOTFOUND.toString());
		}
		return "Trip details not found";
	}

	@Override
	@Transactional
	public TripDetailsConfirmResponse confirmBooking(TripDetailsVo tripDetailsVo) {
		TripDetailsConfirmResponse tripDetailsConfirmResponse = null;
		Gson gson = new Gson();
		FetchSelectedVehiclesRequest fetchSelectedVehiclesRequest = new FetchSelectedVehiclesRequest();
		fetchSelectedVehiclesRequest.setLattitude(tripDetailsVo.getSourceLattitude());
		fetchSelectedVehiclesRequest.setLongitude(tripDetailsVo.getSourceLongitude());
		fetchSelectedVehiclesRequest.setSurroundingDistance(surroundingDistance);
		fetchSelectedVehiclesRequest.setVehicleType(tripDetailsVo.getVehicleType());
		List<VehicleDetails> vehicleDetailsList = vehicleService
				.fetchSelectedVehiclesToConfirmOrder(fetchSelectedVehiclesRequest);
		if (Utils.isNullOrEmpty(vehicleDetailsList)) {
			throw new BusinessException(ErrorCodes.VEHICLENOTFOUND.name(), ErrorCodes.VEHICLENOTFOUND.value());
		}

		DriverDetails driverDetails = driverService
				.findDriverById(vehicleDetailsList.get(0).getDriverDetails().getId());
		CustomerDetails customerDetails = customerDetailsService.findCustomerById(tripDetailsVo.getCustomerId());

		TripDetails tripDetails = gson.fromJson(gson.toJson(tripDetailsVo), TripDetails.class);
		tripDetails.setCustomerDetails(customerDetails);
		tripDetails.setDriverDetails(driverDetails);
		DeliveryStatus deliveryStatus = new DeliveryStatus();
		deliveryStatus.setId(TripStatusEnum.PENDING.getTripStatusId());
		tripDetails.setDeliveryStatus(deliveryStatus);
		tripDetails.setTripTime(new Date());
		tripDetails.setTripStartOtp(userService.generateOtp());
		tripDetails.setTripEndOtp(userService.generateOtp());
		TripDetailsHistoryVo historyVo = new TripDetailsHistoryVo();
		historyVo.setDriverName(driverDetails.getDriverName());
		historyVo.setVehicleImage(vehicleDetailsList.get(0).getVehicleType().getSelectedVehicleUrl());
		historyVo.setVehicleName(vehicleDetailsList.get(0).getVehicleType().getVehicleName());
		historyVo.setVehicleNumber(vehicleDetailsList.get(0).getVehicleNum());
		historyVo.setVehicleType(vehicleDetailsList.get(0).getVehicleType().getId());
		historyVo.setVehicleModel(vehicleDetailsList.get(0).getVehicleModel());
		historyVo.setCgst(cgst);
		historyVo.setSgst(sgst);
		tripDetails.setTripHistoryJson(gson.toJson(historyVo));
		tripDetails = tripDetailsRepo.save(tripDetails);
		LOG.info("Booking confirmed for the customer : " + customerDetails.getUser().getMobileNumber());
		if (tripDetails.getId() == 0) {
			throw new BusinessException(ErrorCodes.TRIPDETAILSNOTSAVED.name(), ErrorCodes.TRIPDETAILSNOTSAVED.value());
		}

		LOG.info("Driver details for trip " + "driver id " + driverDetails.getId() + " User id "
				+ driverDetails.getUser().getId() + " Driver fcm " + driverDetails.getUser().getFcmToken());
		if (driverDetails.getUser().getFcmToken() == null) {
			throw new BusinessException(ErrorCodes.FCMTOKEN.name(), ErrorCodes.FCMTOKEN.value());
		}
		// String bookingBody = "Customer name : "+details.getUser().getFirstName() +"
		// "+" Customer mobile number : "+details.getUser().getMobileNumber();
		JSONObject driverJsonObject = new JSONObject();
		try {
			driverJsonObject.put("customerName", customerDetails.getUser().getFirstName());
			driverJsonObject.put("customerMobileNumber", customerDetails.getUser().getMobileNumber());
			driverJsonObject.put("sourceLattitude", tripDetailsVo.getSourceLattitude());
			driverJsonObject.put("sourceLongitude", tripDetailsVo.getSourceLongitude());
			driverJsonObject.put("destinationLattitude", tripDetailsVo.getDestinationLattitude());
			driverJsonObject.put("destinationLongitude", tripDetailsVo.getDestinationLongitude());
			driverJsonObject.put("sourceLandmark", tripDetailsVo.getSourceLandmark());
			driverJsonObject.put("destinationLandmark", tripDetailsVo.getDestinationLandmark());
			driverJsonObject.put("sourceLocation", tripDetailsVo.getSourceLocation());
			driverJsonObject.put("destinationLocation", tripDetailsVo.getDestinationLocation());
			driverJsonObject.put("customerId", customerDetails.getId());
			driverJsonObject.put("tripId", tripDetails.getId());
			driverJsonObject.put("goodsType", tripDetailsVo.getGoodsType());
			driverJsonObject.put("capacity", tripDetailsVo.getCapacity());
			driverJsonObject.put("goodsSize", tripDetailsVo.getGoodsSize());
			driverJsonObject.put("amount", tripDetailsVo.getAmount());
			driverJsonObject.put("cashMode", tripDetailsVo.getCashMode());

		} catch (JSONException e) {
			LOG.error("Exception while creating driver json object " + e.getMessage());
		}
		PushNotificationBean bean = NotificationBuilder.buildPayloadNotification(NotificationType.BOOKING_CONFIRMED,
				"Booking confirmed", "Booking confirmed", driverJsonObject.toString());
		String dnResponse = transporterPushNotifications.sendPushNotification(driverDetails.getUser().getFcmToken(),
				bean, "driver");
		if (Utils.isNullOrEmpty(dnResponse)) {
			throw new BusinessException(ErrorCodes.DRIVERPUSHNOTIFICATIONERRORWHILEBOOKING.name(),
					ErrorCodes.DRIVERPUSHNOTIFICATIONERRORWHILEBOOKING.value());
		}

		// String bookingCustomerBody = "Driver name :
		// "+driverDetails.getUser().getFirstName() +" "+" Driver mobile number :
		// "+driverDetails.getUser().getMobileNumber();
		JSONObject customerJsonObject = new JSONObject();
		try {
			customerJsonObject.put("driverName", driverDetails.getUser().getFirstName());
			customerJsonObject.put("driverMobileNumber", driverDetails.getUser().getMobileNumber());
			customerJsonObject.put("vehicleName", vehicleDetailsList.get(0).getVehicleModel());
			customerJsonObject.put("vehicleNumber", vehicleDetailsList.get(0).getVehicleNum());
			customerJsonObject.put("vehicleImage", vehicleDetailsList.get(0).getVehicleType().getSelectedVehicleUrl());
			customerJsonObject.put("tripStartOtp", tripDetails.getTripStartOtp());
			customerJsonObject.put("driverId", driverDetails.getId());
			customerJsonObject.put("tripId", tripDetails.getId());

		} catch (JSONException e) {
			LOG.error("Exception while creating customer json object " + e.getMessage());
		}

		PushNotificationBean customerBean = NotificationBuilder.buildPayloadNotification(
				NotificationType.BOOKING_CONFIRMED, "Booking confirmed", "Booking confirmed",
				customerJsonObject.toString());
		String cnResponse = transporterPushNotifications.sendPushNotification(customerDetails.getUser().getFcmToken(),
				customerBean, "customer");
		if (Utils.isNullOrEmpty(cnResponse)) {
			LOG.error("Notification error for customer, while booking");
		}

		// driverService.updateRidingStatus(driverDetails.getId(),
		// RidingStatusEnum.ONRIDING.getRidingStatusId());
		tripDetailsConfirmResponse = new TripDetailsConfirmResponse();
		tripDetailsConfirmResponse.setDriverId(driverDetails.getId());
		tripDetailsConfirmResponse.setDriverName(driverDetails.getUser().getFirstName());
		tripDetailsConfirmResponse.setDriverMobileNumber(driverDetails.getUser().getMobileNumber());
		tripDetailsConfirmResponse.setTripStartOtp(tripDetails.getTripStartOtp());
		tripDetailsConfirmResponse.setVehicleName(vehicleDetailsList.get(0).getVehicleModel());
		tripDetailsConfirmResponse.setVehicleNumber(vehicleDetailsList.get(0).getVehicleNum());
		tripDetailsConfirmResponse.setVehicleImage(vehicleDetailsList.get(0).getVehicleType().getSelectedVehicleUrl());
		tripDetailsConfirmResponse.setTripId(tripDetails.getId());

		return tripDetailsConfirmResponse;
	}

	public Integer getTotalDayRideNumber(Integer userId, Date calendar) {
		return tripDetailsDao.getTotalDayRideNumber(userId, calendar);
	}

	@Override
	public Integer getTotalRideNumber(Integer userId) {
		return tripDetailsDao.getTotalRideNumber(userId);
	}

	@Override
	public String validateStartEndOtp(int tripId, String otp, String status) {

		TripDetails tripDetailsData = tripDetailsRepo.findOne(tripId);
		if (tripDetailsData == null) {
			throw new BusinessException(ErrorCodes.TRIPIDNOTFOUND.name(), ErrorCodes.TRIPIDNOTFOUND.value());
		}

		if (status.equals("start")) {
			TripDetails tripDetails = tripDetailsRepo.validateStartEndOtp(tripId, otp);
			if (tripDetails == null) {
				throw new BusinessException(ErrorCodes.INVALIDOTP.name(), ErrorCodes.INVALIDOTP.value());

			}
			JSONObject tripStartedToCustomer = new JSONObject();
			try {
				tripStartedToCustomer.put("message", "Trip started");
			} catch (JSONException e) {
				LOG.error("Exception while trip started json object " + e.getMessage());
			}
			PushNotificationBean bean = NotificationBuilder.buildPayloadNotification(NotificationType.TRIP_STARTED,
					"Trip started", "Trip started", tripStartedToCustomer.toString());
			transporterPushNotifications.sendPushNotification(tripDetails.getCustomerDetails().getUser().getFcmToken(),
					bean, "customer");
			return "Success";
		} else if (status.equals("end")) {
			TripDetails tripDetails = tripDetailsRepo.validateEndOtp(tripId, otp);
			if (tripDetails == null) {
				throw new BusinessException(ErrorCodes.INVALIDOTP.name(), ErrorCodes.INVALIDOTP.value());

			}
			JSONObject goodsDeliveredToCustomer = new JSONObject();
			try {
				goodsDeliveredToCustomer.put("message", "Goods Delivered");
			} catch (JSONException e) {
				LOG.error("Exception while goods delivered json object " + e.getMessage());
			}
			PushNotificationBean bean = NotificationBuilder.buildPayloadNotification(NotificationType.GOODS_DELIVERED,
					"Goods Delivered", "Goods Delivered", goodsDeliveredToCustomer.toString());
			transporterPushNotifications.sendPushNotification(tripDetails.getCustomerDetails().getUser().getFcmToken(),
					bean, "customer");
			return "Success";
		} else {
			return "Failure";
		}

	}

	@Override
	public String tripCancelledStatus(TripCancelledVo tripCancelledVo) {
		int tripId = tripCancelledVo.getTripId();
		TripDetails tripDetails = tripDetailsRepo.findOne(tripId);
		DeliveryStatus deliveryStatus = new DeliveryStatus();
		if (tripDetails != null) {
			deliveryStatus.setId(tripCancelledVo.getDeliveryStatusId());
			tripDetails.setCancelledReason(tripCancelledVo.getCancelledReason());
			tripDetails.setDeliveryStatus(deliveryStatus);
			tripDetails = tripDetailsRepo.save(tripDetails);
			if (tripDetails != null) {
				return "Success";
			}
		}
		return "Failure";
	}

}

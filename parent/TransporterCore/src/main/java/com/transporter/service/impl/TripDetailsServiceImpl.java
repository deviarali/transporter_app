package com.transporter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.Period;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.transporter.dao.TripDetailsDao;
import com.transporter.enums.DeliveryStatusEnum;
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
import com.transporter.vo.DriverReachedVo;
import com.transporter.vo.FetchSelectedVehiclesRequest;
import com.transporter.vo.PropertiesVo;
import com.transporter.vo.TripCancelledVo;
import com.transporter.vo.TripDetailsConfirmResponse;
import com.transporter.vo.TripDetailsHistoryVo;
import com.transporter.vo.TripDetailsVo;

/**
 * @author Devappa.Arali
 *
 */

@Service
@Transactional
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

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private PropertiesManager propertiesManager;

	@Override
	public List<TripDetailsVo> getTripHistory(int id, int tripStatus, String fromDate, String toDate, String userType) {
		String fromTripStart = null;
		String toTripStart = null;
		Gson gson = new Gson();
		List<TripDetails> tripDetailsList = null;
		if(userType.equalsIgnoreCase("customer")) {
			if (!(StringUtils.isBlank(fromDate)) && !(StringUtils.isBlank(toDate))) {
				fromTripStart = DateTimeUtils.convertToTimestamp(fromDate);
				toTripStart = DateTimeUtils.convertToTimestamp(toDate);
				tripDetailsList = tripDetailsRepo.getHistoryOfPassanger(id, tripStatus, fromTripStart, toTripStart);
			} else {
				if (tripStatus == DeliveryStatusEnum.CANCELLED.getId()) {
					tripDetailsList = tripDetailsRepo.getPassangerCancelledHistory(id);
				} else {
					tripDetailsList = tripDetailsDao.getPassangerHistoryByStatus(id, tripStatus, "customer");
				}
			}
		} else if(userType.equalsIgnoreCase("driver")) {
			if (!(StringUtils.isBlank(fromDate)) && !(StringUtils.isBlank(toDate))) {
				fromTripStart = DateTimeUtils.convertToTimestamp(fromDate);
				toTripStart = DateTimeUtils.convertToTimestamp(toDate);
				tripDetailsList = tripDetailsRepo.getHistoryOfDriver(id, tripStatus, fromTripStart, toTripStart);
			} else {
				if (tripStatus == DeliveryStatusEnum.CANCELLED.getId()) {
					tripDetailsList = tripDetailsRepo.getDriverCancelledHistory(id);
				} else {
					tripDetailsList = tripDetailsDao.getPassangerHistoryByStatus(id, tripStatus, "driver");
				}
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
		/*if (!(StringUtils.isBlank(fromDate)) && !(StringUtils.isBlank(toDate))) {
			fromTripStart = DateTimeUtils.convertToTimestamp(fromDate);
			toTripStart = DateTimeUtils.convertToTimestamp(toDate);
			tripDetailsList = tripDetailsRepo.getHistoryOfPassenger(id, tripStatus, fromTripStart, toTripStart);
		} else {
			if (tripStatus == TripStatusEnum.CANCELLED.getTripStatusId()) {
				tripDetailsList = tripDetailsRepo.getCancelledHistory(id);
			}
			tripDetailsList = tripDetailsRepo.getHistoryByStatusOfPassenger(id, tripStatus);
		}*/

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
				if(null != tripDetailsHistoryVo.getSgstPercentage() && null != tripDetailsHistoryVo.getCgstPercentage()) {
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
			}
			tripDetailsVo.setTripDetailsHistory(tripDetailsHistoryVo);
			tripDetailsVoList.add(tripDetailsVo);
		});
		return tripDetailsVoList;
	}

	@Override
	@Transactional
	public TripDetails updateTripRatings(int tripId, String ratings, String userType, String feedback) {

		if (ratings.equals("0.00") || ratings.equals("0.0") || ratings.equals("0")) {
			throw new BusinessException(ErrorCodes.INVALIDRATING.toString());
		}
		TripDetails tripDetails = tripDetailsRepo.findOne(tripId);
		if (tripDetails != null) {
			//tripDetails.setRatings(ratings);
			tripDetailsDao.saveTripRatings(tripId, ratings, userType, feedback);
		}
		return tripDetails;
	}

	@Override
	@Transactional
	public String updateTripStatus(int tripId, int deliveryStatusId) {
		String response = "success";
		TripDetails tripDetails = tripDetailsRepo.findOne(tripId);
		if (tripDetails != null) {
			int updatedRows = tripDetailsDao.updateTripStatusWithTime(tripDetails.getId(), deliveryStatusId);
			if (deliveryStatusId == DeliveryStatusEnum.CANCELLEDBYCUSTOMER.getId() || deliveryStatusId == DeliveryStatusEnum.CANCELLEDBYDRIVER.getId()) {
				 driverService.updateRidingStatus(tripDetails.getDriverDetails().getId(),
						 RidingStatusEnum.OFFRIDING.getRidingStatusId());
			}

			if (deliveryStatusId == DeliveryStatusEnum.CANCELLEDBYCUSTOMER.getId()) {
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
			} 
			else if (deliveryStatusId == DeliveryStatusEnum.CANCELLEDBYDRIVER.getId()) {
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
		//fetchSelectedVehiclesRequest.setSurroundingDistance(surroundingDistance);
		fetchSelectedVehiclesRequest.setVehicleType(tripDetailsVo.getVehicleType());
		PropertiesVo properties = propertiesManager.getPropertiesByName("surrounding.area");
		String[] property = properties.getPropertyValue().split(",");
		List<VehicleDetails> vehicleDetailsList = null;
		for(String surrondingArea : property) {
			fetchSelectedVehiclesRequest.setSurroundingDistance(Double.valueOf(surrondingArea));
			vehicleDetailsList = vehicleService
					.fetchSelectedVehiclesToConfirmOrder(fetchSelectedVehiclesRequest);
			if(!CollectionUtils.isEmpty(vehicleDetailsList))
				break;
		}
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
		tripDetails.setTripCreatedTime(new Date());
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
			
			customerJsonObject.put("sourceLattitude", tripDetailsVo.getSourceLattitude());
			customerJsonObject.put("sourceLongitude", tripDetailsVo.getSourceLongitude());
			customerJsonObject.put("destinationLattitude", tripDetailsVo.getDestinationLattitude());
			customerJsonObject.put("destinationLongitude", tripDetailsVo.getDestinationLongitude());
			customerJsonObject.put("sourceLandmark", tripDetailsVo.getSourceLandmark());
			customerJsonObject.put("destinationLandmark", tripDetailsVo.getDestinationLandmark());
			customerJsonObject.put("sourceLocation", tripDetailsVo.getSourceLocation());
			customerJsonObject.put("destinationLocation", tripDetailsVo.getDestinationLocation());
			customerJsonObject.put("customerId", customerDetails.getId());
			customerJsonObject.put("goodsType", tripDetailsVo.getGoodsType());
			customerJsonObject.put("capacity", tripDetailsVo.getCapacity());
			customerJsonObject.put("goodsSize", tripDetailsVo.getGoodsSize());
			customerJsonObject.put("amount", tripDetailsVo.getAmount());
			customerJsonObject.put("cashMode", tripDetailsVo.getCashMode());
			customerJsonObject.put("driverCurrentLongitude", driverDetails.getVehicleDetails().getCurrentLongitude());
			customerJsonObject.put("driverCurrentLattitude", driverDetails.getVehicleDetails().getCurrentLattitude());
			

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

		driverService.updateRidingStatus(driverDetails.getId(), RidingStatusEnum.ONRIDING.getRidingStatusId());
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
			this.updateTripStatusWithTime(tripId, DeliveryStatusEnum.ONGOING.getId());
			JSONObject tripStartedToCustomer = new JSONObject();
			try {
				tripStartedToCustomer.put("message", "Trip started");
				tripStartedToCustomer.put("tripId", tripDetails.getId());
				tripStartedToCustomer.put("sourceLattitude", tripDetails.getSourceLattitude());
				tripStartedToCustomer.put("sourceLongitude", tripDetails.getSourceLongitude());
				tripStartedToCustomer.put("destinationLattitude", tripDetails.getDestinationLattitude());
				tripStartedToCustomer.put("destinationLongitude", tripDetails.getDestinationLongitude());
				tripStartedToCustomer.put("sourceLandmark", tripDetails.getSourceLandmark());
				tripStartedToCustomer.put("destinationLandmark", tripDetails.getDestinationLandmark());
				tripStartedToCustomer.put("sourceLocation", tripDetails.getSourceLocation());
				tripStartedToCustomer.put("destinationLocation", tripDetails.getDestinationLocation());
				tripStartedToCustomer.put("goodsType", tripDetails.getGoodsType());
				tripStartedToCustomer.put("capacity", tripDetails.getCapacity());
				tripStartedToCustomer.put("goodsSize", tripDetails.getGoodsSize());
				tripStartedToCustomer.put("amount", tripDetails.getAmount());
				tripStartedToCustomer.put("cashMode", tripDetails.getCashMode());
			} catch (JSONException e) {
				LOG.error("Exception while trip started json object " + e.getMessage());
			}
			PushNotificationBean bean = NotificationBuilder.buildPayloadNotification(NotificationType.TRIP_STARTED,
					"Trip started", "Trip started", tripStartedToCustomer.toString());
			transporterPushNotifications.sendPushNotification(tripDetails.getCustomerDetails().getUser().getFcmToken(),
					bean, "customer");
			this.updateTripStatus(tripId, DeliveryStatusEnum.ONGOING.getId());
			return "Success";
		} else if (status.equals("end")) {
			TripDetails tripDetails = tripDetailsRepo.validateEndOtp(tripId, otp);
			if (tripDetails == null) {
				throw new BusinessException(ErrorCodes.INVALIDOTP.name(), ErrorCodes.INVALIDOTP.value());

			}
			JSONObject goodsDeliveredToCustomer = new JSONObject();
			try {
				goodsDeliveredToCustomer.put("message", "Goods Delivered");
				goodsDeliveredToCustomer.put("amount", tripDetails.getAmount());
				goodsDeliveredToCustomer.put("paymentType", tripDetails.getCashMode());
				goodsDeliveredToCustomer.put("tripId", tripDetails.getId());
			} catch (JSONException e) {
				LOG.error("Exception while goods delivered json object " + e.getMessage());
			}
			PushNotificationBean bean = NotificationBuilder.buildPayloadNotification(NotificationType.GOODS_DELIVERED,
					"Goods Delivered", "Goods Delivered", goodsDeliveredToCustomer.toString());
			transporterPushNotifications.sendPushNotification(tripDetails.getCustomerDetails().getUser().getFcmToken(),
					bean, "customer");
			this.updateTripStatusWithTime(tripId, DeliveryStatusEnum.TRIPENDED.getId());
			driverService.updateRidingStatus(tripDetails.getDriverDetails().getId(), 0);
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

	@Override
	public boolean isDriverReachedLocation(DriverReachedVo driverReachedVo) {
		boolean isDriverReachedLocation = false;
		int tripId = driverReachedVo.getTripId();
		TripDetails tripDetails = tripDetailsRepo.findOne(tripId);
		if (tripDetails != null) {
			double distance = Utils.distance(driverReachedVo.getLocationLatitude(),
					driverReachedVo.getDriverLocationLatitude(), driverReachedVo.getLocationLongitude(),
					driverReachedVo.getDriverLocationLongitude());
			if (distance <= 0.6) {
				isDriverReachedLocation = true;
				if (!driverReachedVo.getLocationType().isEmpty()) {
					switch (driverReachedVo.getLocationType()) {
					case "pickup":
						JSONObject driverReachedPickLocation = new JSONObject();
						try {
							driverReachedPickLocation.put("message", "driver reached pick up point");
						} catch (JSONException e) {
							LOG.error("Exception while sending push notificatin " + e.getMessage());
						}
						PushNotificationBean pickUpBean = NotificationBuilder.buildPayloadNotification(
								NotificationType.DRIVER_REACHED_PICK_UP_POINT, "Driver reached pickup point",
								"Driver has arrived in pickup location", driverReachedPickLocation.toString());
						transporterPushNotifications.sendPushNotification(
								tripDetails.getCustomerDetails().getUser().getFcmToken(), pickUpBean, "customer");
						this.updateTripStatusWithTime(tripId, DeliveryStatusEnum.DRIVERREACHEDPICKUPLOCATION.getId());
						break;
					case "drop":
						JSONObject driverReachedDropLocation = new JSONObject();
						try {
							driverReachedDropLocation.put("message", "driver has reached destination point");
						} catch (JSONException e) {
							LOG.error("Exception while sending push notificatin " + e.getMessage());
						}
						PushNotificationBean bean = NotificationBuilder.buildPayloadNotification(
								NotificationType.DRIVER_REACHED_DESTINATION, "Driver reached destination point",
								"Driver has reached destination", driverReachedDropLocation.toString());
						transporterPushNotifications.sendPushNotification(
								tripDetails.getCustomerDetails().getUser().getFcmToken(), bean, "customer");
						this.updateTripStatusWithTime(tripId, DeliveryStatusEnum.DRIVERREACHEDDESTINATIONLOCATION.getId());
						break;
					default:
						throw new BusinessException(ErrorCodes.IN_VALID_LOCATION_TYPE.name(),
								ErrorCodes.IN_VALID_LOCATION_TYPE.value());
					}
				}
			} else {
				isDriverReachedLocation = false;
				throw new BusinessException(ErrorCodes.DRIVER_NOT_REACHED_LOCATION.name(),
						ErrorCodes.DRIVER_NOT_REACHED_LOCATION.value());
			}
		} else {
			isDriverReachedLocation = false;
			throw new BusinessException(ErrorCodes.TRIPDETAILSNOTFOUND.name(), ErrorCodes.TRIPDETAILSNOTFOUND.value());
		}

		return isDriverReachedLocation;
	}

	@Override
	public boolean sendInvoiceToMail(int tripId) {
		boolean isMainSent = false;
		/*
		 * TripDetails tripDetails = tripDetailsRepo.findOne(tripId); if(tripDetails !=
		 * null) {
		 * 
		 * 
		 * }else { isMainSent = false; throw new
		 * BusinessException(ErrorCodes.TRIPDETAILSNOTFOUND.name(),
		 * ErrorCodes.TRIPDETAILSNOTFOUND.value()); }
		 */
		try {
			sendMail("bnavi1992@gmail.com", "new email",
					"You are receiving this email just becasue of you are part of transol family");
			isMainSent = true;
		} catch (Exception e) {
			isMainSent = false;
			LOG.error(e.getMessage());
		}

		return isMainSent;
	}

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(String toEmail, String subject, String message) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(toEmail);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);

		mailMessage.setFrom("bnavi1992@gmail.com");

		javaMailSender.send(mailMessage);
	}

	@Override
	@Transactional
	public List<TripDetails> getTripHistoryByUserId(int userId) {
		List<TripDetails> details = null;
		details = tripDetailsRepo.getTripHistoryByUserId(userId);
		return details;
	}

	@Override
	public Integer getTotalDayAllRideNumber() {
		Date startTime = DateTimeUtils.getZeroTimeDate(DateTimeUtils.getCurrentDate());
		Date endTime = DateTimeUtils.getLastMinTimeDate(DateTimeUtils.getCurrentDate());
		return tripDetailsDao.getTotalDayAllRideNumber(startTime, endTime);
	}

	@Override
	public Map<Integer, Long> getTopDriversForWeek(int limit) {
		Date startTime = DateTimeUtils.getZeroTimeDate(DateTimeUtils.getBackDate(7));
		Date endTime = DateTimeUtils.getLastMinTimeDate(DateTimeUtils.getCurrentDate());
		return tripDetailsDao.getTopDriversForWeek(limit, startTime, endTime);
	}

	@Override
	public Map<Integer, Long> getTopCustomerForWeek(Integer limit) {
		Date startTime = DateTimeUtils.getZeroTimeDate(DateTimeUtils.getBackDate(7));
		Date endTime = DateTimeUtils.getLastMinTimeDate(DateTimeUtils.getCurrentDate());
		return tripDetailsDao.getTopCustomerForWeek(limit, startTime, endTime);
	}

	@Override
	public List<TripDetailsVo> getDriversLastTripDetails(int driverId) {
		List<TripDetails> tripDetailsList = tripDetailsDao.getDriversLastTripDetails(driverId);	
		List<TripDetailsVo> tripDetailsVos = new ArrayList<>();
		tripDetailsList.forEach( data -> {
			TripDetailsVo tripDetailsVo = TripDetails.convertEntityTOVo(data);
			tripDetailsVo.setCustomerDetails(CustomerDetails.convertModelToVO(data.getCustomerDetails()));
			tripDetailsVos.add(tripDetailsVo);
		});
		return tripDetailsVos;
	}

	@Override
	public List<TripDetailsVo> getCustomerLastTripDetails(int customerId) {
		List<TripDetails> tripDetails = tripDetailsDao.getCustomerLastTripDetails(customerId);
		List<TripDetailsVo> tripDetailsVos = new ArrayList<>();
		tripDetails.forEach(data -> {
			TripDetailsVo tripDetailsVo = TripDetails.convertEntityTOVo(data);
			tripDetailsVo.setCustomerDetails(CustomerDetails.convertModelToVO(data.getCustomerDetails()));
			tripDetailsVos.add(tripDetailsVo);
		});
		return tripDetailsVos;
	}
	
	@Override
	public List<TripDetailsVo> getTripDetailsByCustomer(int customerId,int status) {
		CustomerDetails customerDetails = customerDetailsService.findCustomerById(customerId);
		if (customerDetails == null) {
			throw new BusinessException(ErrorCodes.CNFOUND.name(), ErrorCodes.CNFOUND.value());
		}
		List<TripDetails> tripDetails = tripDetailsDao.getTripDetailsByCustomer(customerId,status);
		if(CollectionUtils.isEmpty(tripDetails)) {
			throw new BusinessException(ErrorCodes.NODATAFOUND.name(), ErrorCodes.NODATAFOUND.value());
		}
		List<TripDetailsVo> tripDetailsVos = new ArrayList<TripDetailsVo>();
		tripDetails.forEach(data -> {
			TripDetailsVo tripDetailsVo = TripDetails.convertEntityTOVo(data);
			tripDetailsVo.setCustomerDetails(CustomerDetails.convertModelToVO(data.getCustomerDetails()));
			tripDetailsVos.add(tripDetailsVo);

		});
		return tripDetailsVos;
	}

	@Override
	@Transactional
	public int updateTripAcceptOrReject(int tripId, int status) {
		if(status == 1)
			status = DeliveryStatusEnum.TRIPACCEPTED.getId();
		else
			status = DeliveryStatusEnum.TRIPREJECTED.getId();
		return updateTripStatusWithTime(tripId, status);
	}
	
	
	
	private int updateTripStatusWithTime(int tripId, int status) {
		int updated = tripDetailsDao.updateTripStatusWithTime(tripId, status);	
		return updated;
	}

	

}

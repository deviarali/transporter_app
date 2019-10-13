package com.transporter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
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
			tripDetailsHistory.setCancelledamountFromCustomer(data.getCancelledAmountFromCustomer());
			tripDetailsHistory.setCancelledamountFromDriver(data.getCancelledAmountFromDriver());
			tripDetailsHistory.setCancelledamountStatus(data.getCancelledAmountStatus());
			tripDetailsHistory.setCashMode(data.getCashMode());
			tripDetailsHistory.setDeliverypersonMobile(data.getDeliveryPersonMobile());
			tripDetailsHistory.setDeliverypersonName(data.getDeliveryPersonName());
			tripDetailsHistory.setDestinationLocation(data.getDestinationLocation());
			tripDetailsHistory.setGoodsType(data.getGoodsType());
			tripDetailsHistory.setGoodsSize(data.getGoodsSize());
			tripDetailsHistory.setPickupLocation(data.getSourceLocation());
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
	public String updateTripStatus(int tripId, int deliveryStatusId) {
		String response = "success";
		TripDetails tripDetails = tripDetailsRepo.findOne(tripId);
		if (tripDetails != null) {
			
			int updatedRows = tripDetailsRepo.updateTripStatus(tripDetails.getId(),deliveryStatusId);
			
			if(deliveryStatusId == 3 || deliveryStatusId == 4) {
				//driverService.updateRidingStatus(tripDetails.getDriverDetails().getId(), RidingStatusEnum.OFFRIDING.getRidingStatusId());
			}
			
			if (deliveryStatusId == 3) {
				JSONObject cancelByCustomer = new JSONObject();
				try {
					cancelByCustomer.put("message", "trip has been cancelled by customer");
				} catch (JSONException e) {
					LOG.error("Exception while creating cancelled by customer json object "+e.getMessage());
				}	
				PushNotificationBean bean = NotificationBuilder.buildPayloadNotification(NotificationType.BOOKING_CANCELLED, "Booking Cancelled", "Booking Cancelled", cancelByCustomer.toString());
				transporterPushNotifications.sendPushNotification(tripDetails.getDriverDetails().getUser().getFcmToken(), bean, "driver");
			} else if(deliveryStatusId == 4) {
				JSONObject cancelByDriver = new JSONObject();
				try {
					cancelByDriver.put("message", "trip has been cancelled by driver");
				} catch (JSONException e) {
					LOG.error("Exception while creating cancelled by driver json object "+e.getMessage());
				}
				PushNotificationBean bean = NotificationBuilder.buildPayloadNotification(NotificationType.BOOKING_CANCELLED, "Booking Cancelled", "Booking Cancelled", cancelByDriver.toString());
				transporterPushNotifications.sendPushNotification(tripDetails.getCustomerDetails().getUser().getFcmToken(), bean, "customer");
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
			tripDetails.setCanceledReason(deliveryStatusVo.getDeliverystatus());
			tripDetails.setDeliveryStatus(deliveryStatus);
			tripDetails = tripDetailsRepo.save(tripDetails);
			
			if(deliveryStatusId == 3 || deliveryStatusId == 4) {
				driverService.updateRidingStatus(tripDetails.getDriverDetails().getId(), RidingStatusEnum.OFFRIDING.getRidingStatusId());
			}
			
			if (tripDetails != null) {
				if (deliveryStatusId == 3) {
					PushNotificationBean bean = NotificationBuilder.buildGenericPayloadNotification(NotificationType.BOOKING_CANCELLED, "Booking Cancelled", "Booking Cancelled", "Trip cancled by customer");
					transporterPushNotifications.sendPushNotification(tripDetails.getDriverDetails().getUser().getFcmToken(), bean, "driver");
					return "Trip cancled by customer";
				} else if(deliveryStatusId == 4) {
					PushNotificationBean bean = NotificationBuilder.buildGenericPayloadNotification(NotificationType.BOOKING_CANCELLED, "Booking Cancelled", "Booking Cancelled", "Trip cancled by driver");
					transporterPushNotifications.sendPushNotification(tripDetails.getCustomerDetails().getUser().getFcmToken(), bean, "customer");
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
		List<VehicleDetails> vehicleDetailsList = vehicleService.fetchSelectedVehiclesToConfirmOrder(fetchSelectedVehiclesRequest);
		if(Utils.isNullOrEmpty(vehicleDetailsList)) {
			throw new BusinessException(ErrorCodes.VEHICLENOTFOUND.name(), ErrorCodes.VEHICLENOTFOUND.value());
		}
		
		DriverDetails driverDetails = driverService.findDriverById(vehicleDetailsList.get(0).getDriverDetails().getId());
		CustomerDetails customerDetails = customerDetailsService.findCustomerById(tripDetailsVo.getCustomerId());
		
		TripDetails tripDetails = gson.fromJson(gson.toJson(tripDetailsVo), TripDetails.class);			
		tripDetails.setCustomerDetails(customerDetails);
		tripDetails.setDriverDetails(driverDetails);
		DeliveryStatus deliveryStatus = new DeliveryStatus();
		deliveryStatus.setId(TripStatusEnum.PENDING.getTripStatusId());
		tripDetails.setDeliveryStatus(deliveryStatus);
		tripDetails.setTripTime(new Date());
		tripDetails.setTripStartOtp(userService.generateOtp());
		tripDetails = tripDetailsRepo.save(tripDetails);
		LOG.info("Booking confirmed for the customer : "+customerDetails.getUser().getMobileNumber());
		if(tripDetails.getId() == 0) {
			throw new BusinessException(ErrorCodes.TRIPDETAILSNOTSAVED.name(), ErrorCodes.TRIPDETAILSNOTSAVED.value());
		}
		
		LOG.info("Driver details for trip " +"driver id "+driverDetails.getId() +" User id "+driverDetails.getUser().getId() + " Driver fcm "+driverDetails.getUser().getFcmToken());
		if(driverDetails.getUser().getFcmToken() == null)
		{
			throw new BusinessException(ErrorCodes.FCMTOKEN.name(), ErrorCodes.FCMTOKEN.value());	
		}
		//String bookingBody = "Customer name : "+details.getUser().getFirstName() +" "+" Customer mobile number : "+details.getUser().getMobileNumber();
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
			
		} catch (JSONException e) {
			LOG.error("Exception while creating driver json object "+e.getMessage());
		}
		PushNotificationBean bean = NotificationBuilder.buildPayloadNotification(NotificationType.BOOKING_CONFIRMED, "Booking confirmed", "Booking confirmed", driverJsonObject.toString());
		String dnResponse = transporterPushNotifications.sendPushNotification(driverDetails.getUser().getFcmToken(), bean, "driver");
		if(Utils.isNullOrEmpty(dnResponse)) {
			throw new BusinessException(ErrorCodes.DRIVERPUSHNOTIFICATIONERRORWHILEBOOKING.name(), ErrorCodes.DRIVERPUSHNOTIFICATIONERRORWHILEBOOKING.value());
		}
		
	//	String bookingCustomerBody = "Driver name : "+driverDetails.getUser().getFirstName() +" "+" Driver mobile number : "+driverDetails.getUser().getMobileNumber();
		JSONObject customerJsonObject = new JSONObject();
		try {
			customerJsonObject.put("driverName", driverDetails.getUser().getFirstName());
			customerJsonObject.put("driverMobileNumber", driverDetails.getUser().getMobileNumber());
			customerJsonObject.put("vehicleName", vehicleDetailsList.get(0).getVehicleModel());
			customerJsonObject.put("vehicleNumber",  vehicleDetailsList.get(0).getVehicleNum());
			customerJsonObject.put("vehicleImage",  vehicleDetailsList.get(0).getVehicleType().getSelectedVehicleUrl());
			customerJsonObject.put("tripStartOtp",  tripDetails.getTripStartOtp());
			customerJsonObject.put("driverId",  driverDetails.getId());
			customerJsonObject.put("tripId", tripDetails.getId());
			
		} catch (JSONException e) {
			LOG.error("Exception while creating customer json object "+e.getMessage());
		}
		
		PushNotificationBean customerBean = NotificationBuilder.buildPayloadNotification(NotificationType.BOOKING_CONFIRMED, "Booking confirmed", "Booking confirmed", customerJsonObject.toString());
		String cnResponse = transporterPushNotifications.sendPushNotification(customerDetails.getUser().getFcmToken(), customerBean, "customer");
		if(Utils.isNullOrEmpty(cnResponse)) {
			LOG.error("Notification error for customer, while booking");
		}
		
		//driverService.updateRidingStatus(driverDetails.getId(), RidingStatusEnum.ONRIDING.getRidingStatusId());
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

	@Override
	public Integer getTotalDayRideNumber(Integer userId, Date calendar) {
		return tripDetailsDao.getTotalDayRideNumber(userId, calendar);
	}
	
	@Override
	public Integer getTotalRideNumber(Integer userId) {
		return tripDetailsDao.getTotalRideNumber(userId);
	}

}

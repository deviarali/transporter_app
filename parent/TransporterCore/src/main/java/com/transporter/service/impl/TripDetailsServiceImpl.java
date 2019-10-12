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
import com.transporter.enums.DeliveryStatusEnum;
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
import com.transporter.vo.DriverDetailsVo;
import com.transporter.vo.FetchSelectedVehiclesRequest;
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
			
			if(deliveryStatusId == 3 || deliveryStatusId == 4) {
				driverService.updateRidingStatus(tripDetails.getDriverDetails().getId(), 0);
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
	public DriverDetailsVo confirmBooking(TripDetailsVo tripDetailsVo) {
		DriverDetailsVo driverDetailsVo = null;
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
		
		CustomerDetails details = customerDetailsService.findCustomerById(tripDetailsVo.getCustomerId());
		
		DriverDetails driverDetails = driverService.findDriverById(vehicleDetailsList.get(0).getDriverDetails().getId());
		LOG.info("Driver details for trip " +"driver id "+driverDetails.getId() +" User id "+driverDetails.getUser().getId() + " Driver fcm "+driverDetails.getUser().getFcmToken());
		if(driverDetails.getUser().getFcmToken() == null)
		{
			throw new BusinessException(ErrorCodes.FCMTOKEN.name(), ErrorCodes.FCMTOKEN.value());	
		}
		//String bookingBody = "Customer name : "+details.getUser().getFirstName() +" "+" Customer mobile number : "+details.getUser().getMobileNumber();
		JSONObject jsonObjectC = new JSONObject();
		try {
			jsonObjectC.put("customerName", details.getUser().getFirstName());
			jsonObjectC.put("customerMobileNumber", details.getUser().getMobileNumber());
		//	jsonObjectC.put("sourceLattitude", tripDetailsVo.getlo)
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PushNotificationBean bean = NotificationBuilder.buildPayloadNotification(NotificationType.BOOKING_CONFIRMED, "Booking confirmed", "Booking confirmed", jsonObjectC.toString());
		String dnResponse = transporterPushNotifications.sendPushNotification(driverDetails.getUser().getFcmToken(), bean, "driver");
		if(Utils.isNullOrEmpty(dnResponse)) {
			throw new BusinessException(ErrorCodes.DRIVERPUSHNOTIFICATIONERRORWHILEBOOKING.name(), ErrorCodes.DRIVERPUSHNOTIFICATIONERRORWHILEBOOKING.value());
		}
		
	//	String bookingCustomerBody = "Driver name : "+driverDetails.getUser().getFirstName() +" "+" Driver mobile number : "+driverDetails.getUser().getMobileNumber();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("driverName", driverDetails.getUser().getFirstName());
			jsonObject.put("driverMobileNumber", driverDetails.getUser().getMobileNumber());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PushNotificationBean customerBean = NotificationBuilder.buildPayloadNotification(NotificationType.BOOKING_CONFIRMED, "Booking confirmed", "Booking confirmed", jsonObject.toString());
		String cnResponse = transporterPushNotifications.sendPushNotification(details.getUser().getFcmToken(), customerBean, "customer");
		if(Utils.isNullOrEmpty(cnResponse)) {
			LOG.error("Notification error for customer, while booking");
		}
		TripDetails tripDetails = gson.fromJson(gson.toJson(tripDetailsVo), TripDetails.class);	
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setId(tripDetailsVo.getCustomerId());
		tripDetails.setCustomerDetails(customerDetails);
		DriverDetails driverDetails2 = new DriverDetails();
		driverDetails2.setId(driverDetails.getId());
		tripDetails.setDriverDetails(driverDetails2);
		DeliveryStatus deliveryStatus = new DeliveryStatus();
		deliveryStatus.setId(DeliveryStatusEnum.PENDING.getId());
		tripDetails.setDeliveryStatus(deliveryStatus);
		tripDetails.setTripTime(new Date());
		tripDetails.setTripStartOtp(userService.generateOtp());
		TripDetails details2 = tripDetailsRepo.save(tripDetails);
	//	driverService.updateRidingStatus(driverDetails2.getId(),1);
		if(details2.getId() > 0) {
			driverDetailsVo = new DriverDetailsVo();
			driverDetailsVo.setId(driverDetails2.getId());
			driverDetailsVo.setDrivername(driverDetails.getUser().getFirstName());
			driverDetailsVo.setMobileNumber(driverDetails.getUser().getMobileNumber());
			driverDetailsVo.setTripStartOtp(tripDetails.getTripStartOtp());
		} else {
			throw new BusinessException(ErrorCodes.TRIPDETAILSNOTSAVED.name(), ErrorCodes.TRIPDETAILSNOTSAVED.value());
		}
		return driverDetailsVo;
	}
}

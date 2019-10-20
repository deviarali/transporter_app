package com.transporter.service;

import java.util.Date;
import java.util.List;

import com.transporter.model.TripDetails;
import com.transporter.vo.DeliveryStatusVo;
import com.transporter.vo.TripCancelledVo;
import com.transporter.vo.TripDetailsConfirmResponse;
import com.transporter.vo.TripDetailsHistoryVo;
import com.transporter.vo.TripDetailsVo;

/**
 * @author Devappa.Arali
 *
 */

public interface TripDetailsService {

	List<TripDetailsVo> getTripHistory(int id, int tripstatus, String fromDate, String toDate);

	TripDetails updateTripRatings(int tripId, String ratings);

	String updateTripStatus(int tripId, int deliveryStatusId);

	String updateTripCancelledStatus(int tripId, int deliveryStatusId, DeliveryStatusVo deliveryStatusVo);

	public TripDetailsConfirmResponse confirmBooking(TripDetailsVo tripDetailsVo);

	public Integer getTotalDayRideNumber(Integer userId, Date calendar);
	
	public Integer getTotalRideNumber(Integer userId);

	String validateStartEndOtp(int tripId, String otp, String status);

	String tripCancelledStatus(TripCancelledVo tripCancelledVo);


	

}

package com.transporter.service;

import java.util.Date;
import java.util.List;

import com.transporter.model.TripDetails;
import com.transporter.vo.DeliveryStatusVo;
import com.transporter.vo.TripDetailsConfirmResponse;
import com.transporter.vo.TripDetailsHistoryVo;
import com.transporter.vo.TripDetailsVo;

/**
 * @author Devappa.Arali
 *
 */

public interface TripDetailsService {

	List<TripDetailsHistoryVo> getTripHistory(int id, int tripstatus, String fromDate, String toDate);

	TripDetails updateTripRatings(int tripId, String ratings);

	String updateTripStatus(int tripId, int deliveryStatusId);

	String updateTripCancelledStatus(int tripId, int deliveryStatusId, DeliveryStatusVo deliveryStatusVo);

	public TripDetailsConfirmResponse confirmBooking(TripDetailsVo tripDetailsVo);

<<<<<<< HEAD
	String validateOtp(int tripId, String otp);
=======
	public Integer getTotalDayRideNumber(Integer userId, Date calendar);
	
	public Integer getTotalRideNumber(Integer userId);
>>>>>>> 96ed563e3c25a6cf656841fe263bd28628e82a5b

}

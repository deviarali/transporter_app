package com.transporter.service;

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

}

package com.transporter.service;

import java.util.List;

import com.transporter.model.TripDetails;
import com.transporter.vo.DeliveryStatusVo;
import com.transporter.vo.TripDetailsHistoryVo;

/**
 * @author Devappa.Arali
 *
 */

public interface TripDetailsService {

	List<TripDetailsHistoryVo> getTripHistory(int id, int tripstatus, String fromDate, String toDate);

	TripDetails updateTripRatings(int tripId, String ratings);

	TripDetails updateTripStatus(int tripId, int deliveryStatusId);

	String updateTripCancelledStatus(int tripId, int deliveryStatusId, DeliveryStatusVo deliveryStatusVo);

}

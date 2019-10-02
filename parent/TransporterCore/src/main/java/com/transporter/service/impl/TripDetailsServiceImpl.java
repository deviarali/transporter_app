package com.transporter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.model.DeliveryStatus;
import com.transporter.model.TripDetails;
import com.transporter.repo.TripDetailsRepo;
import com.transporter.service.TripDetailsService;
import com.transporter.utils.DateTimeUtils;
import com.transporter.vo.TripDetailsHistoryVo;

/**
 * @author Devappa.Arali
 *
 */

@Service
public class TripDetailsServiceImpl implements TripDetailsService {

	@Autowired
	TripDetailsRepo tripDetailsRepo;

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
		}
		return tripDetails;
	}
}

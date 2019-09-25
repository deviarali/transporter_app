package com.transporter.service;

import java.util.List;

import com.transporter.vo.TripDetailsHistoryVo;

/**
 * @author Devappa.Arali
 *
 */

public interface TripDetailsService {

	List<TripDetailsHistoryVo> getTripHistory(int id, int tripstatus, String fromDate, String toDate);

}

package com.transporter.service;

import java.util.List;

import com.transporter.vo.TripDetailsHistoryVo;
import com.transporter.vo.TripDetailsVo;

/**
 * @author Devappa.Arali
 *
 */

public interface TripDetailsService {

	List<TripDetailsHistoryVo> getTripHistory(int id, int tripstatus);

}

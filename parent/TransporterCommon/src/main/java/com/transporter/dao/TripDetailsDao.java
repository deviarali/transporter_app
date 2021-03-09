package com.transporter.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author SHARAN A
 */
public interface TripDetailsDao extends GenericDao {

	public Integer getTotalDayRideNumber(Integer userId, Date calendar);
	
	public Integer getTotalRideNumber(Integer userId);

	public Integer getTotalDayAllRideNumber(Date startTime, Date endTime);

	public Map<Integer, Long> getTopDriversForWeek(int limit, Date startTime, Date endTime);
}

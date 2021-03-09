package com.transporter.dao;

import java.util.Date;

/**
 * @author SHARAN A
 */
public interface TripDetailsDao extends GenericDao {

	public Integer getTotalDayRideNumber(Integer userId, Date calendar);
	
	public Integer getTotalRideNumber(Integer userId);

	public Integer getTotalDayAllRideNumber(Date startTime, Date endTime);
}

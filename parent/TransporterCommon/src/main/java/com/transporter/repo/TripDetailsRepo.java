package com.transporter.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.transporter.model.TripDetails;

@Repository
public interface TripDetailsRepo extends JpaRepository<TripDetails, Integer> {

	@Query(value = "SELECT * from tripdetails m WHERE m.customer_id =:id and m.trip_status =:tripstatus and cast(m.trip_starttime as date) BETWEEN :fromTripStart and :toTripStart ", nativeQuery = true)
	List<TripDetails> getHistoryOfPassanger(@Param(value = "id") int id, @Param(value = "tripstatus") int tripstatus,
			@Param(value = "fromTripStart") String fromTripStart, @Param(value = "toTripStart") String toTripStart);

	@Query(value = "SELECT * from tripdetails m WHERE m.driver_id =:id and m.trip_status =:tripstatus and cast(m.trip_starttime as date) BETWEEN :fromTripStart and :toTripStart ", nativeQuery = true)
	List<TripDetails> getHistoryOfDriver(@Param(value = "id") int id, @Param(value = "tripstatus") int tripstatus,
			@Param(value = "fromTripStart") String fromTripStart, @Param(value = "toTripStart") String toTripStart);

	@Query("SELECT m from TripDetails m WHERE m.customerDetails.id =?1 and m.deliveryStatus.id=?2")
	List<TripDetails> getPassangerHistoryByStatus(int id, int tripstatus);

	@Query("SELECT m from TripDetails m WHERE m.driverDetails.id =?1 and m.deliveryStatus.id=?2")
	List<TripDetails> getDriverHistoryByStatus(int id, int tripstatus);

	/*
	 * @Modifying
	 * 
	 * @Query("UPDATE TripDetails m set m.deliveryStatus.id = :tripStatus where m.id = :id"
	 * ) TripDetails updateTripStatus(@Param("id")int id,@Param("tripStatus")int
	 * tripStatus);
	 */

	@Modifying
	@Query("UPDATE TripDetails m set m.deliveryStatus.id = :tripStatus where m.id = :id")
	Integer updateTripStatus(@Param(value = "id") int id, @Param(value = "tripStatus") int tripStatus);

	@Query("From TripDetails td WHERE td.customerDetails.id= :id AND (td.deliveryStatus.id=10 OR td.deliveryStatus.id=11)")
	List<TripDetails> getPassangerCancelledHistory(@Param(value = "id") int id);

	@Query(value = "SELECT dd.sourceLattitude,dd.sourceLongitude from TripDetails dd where dd.driverDetails.id =?1")
	TripDetails getdriverDeatils(int driverId);

	@Query("SELECT m From TripDetails m where m.id= :tripId and m.tripStartOtp= :otp")
	TripDetails validateStartEndOtp(@Param(value = "tripId") int tripId, @Param(value = "otp") String otp);

	@Query("SELECT m From TripDetails m where m.id= :tripId and m.tripEndOtp= :otp")
	TripDetails validateEndOtp(@Param(value = "tripId") int tripId, @Param(value = "otp") String otp);

	@Query(value = "SELECT m from TripDetails m where m.deliveryStatus=5 and m.tripStarttime BETWEEN :fromTripStart and :toTripStart")
	List<TripDetails> getTopDriversForWeek(@Param(value = "fromTripStart") Date fromTripStart,
			@Param(value = "toTripStart") Date toTripStart);

	@Query("SELECT m From TripDetails m where m.id= :tripId")
	TripDetails getTripDetail(@Param(value = "tripId") int tripId);

	@Query(value = "SELECT * from tripdetails m WHERE m.customer_id =:id", nativeQuery = true)
	List<TripDetails> getTripHistoryByUserId(@Param(value = "id") int id);
	
	@Query("From TripDetails td WHERE td.driverDetails.id= :id AND (td.deliveryStatus.id=10 OR td.deliveryStatus.id=11)")
	List<TripDetails> getDriverCancelledHistory(@Param(value = "id") int id);
}

package com.transporter.repo;

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
	List<TripDetails> getHistory(@Param(value = "id") int id, @Param(value = "tripstatus") int tripstatus,
			@Param(value = "fromTripStart") String fromTripStart, @Param(value = "toTripStart") String toTripStart);

	@Query("SELECT m from TripDetails m WHERE m.customerDetails.id =?1 and m.deliveryStatus.id=?2")
	List<TripDetails> getHistoryByStatus(int id, int tripstatus);

	@Modifying
	@Query("UPDATE TripDetails m set m.deliveryStatus.id = :tripStatus where m.id = :id")
	TripDetails updateTripStatus(@Param("id")int id,@Param("tripStatus")int tripStatus);

}

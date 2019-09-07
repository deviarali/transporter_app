package com.transporter.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.transporter.model.TripDetails;

@Repository
public interface TripDetailsRepo extends JpaRepository<TripDetails, Integer>
{

	@Query("SELECT m from TripDetails m WHERE m.customerDetails.id =?1 and m.deliveryStatus.id=?2")
	List<TripDetails> getHistory(int id, int tripstatus);

}

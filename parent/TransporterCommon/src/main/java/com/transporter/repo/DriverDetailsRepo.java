package com.transporter.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.transporter.model.DriverDetails;

public interface DriverDetailsRepo extends JpaRepository<DriverDetails, Integer>{

	@Modifying
	@Query(value = "update DriverDetails dd set dd.ridingStatus= :status where dd.id= :id")
	void updateRidingStatus(@Param(value ="id") int id, @Param(value ="status")int status);



}

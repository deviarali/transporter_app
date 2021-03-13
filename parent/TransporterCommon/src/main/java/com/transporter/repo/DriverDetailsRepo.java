package com.transporter.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.transporter.model.DriverDetails;

public interface DriverDetailsRepo extends JpaRepository<DriverDetails, Integer>{

	@Modifying
	@Query(value = "update DriverDetails dd set dd.ridingStatus= :status where dd.id= :id")
	void updateRidingStatus(@Param(value ="id") int id, @Param(value ="status")int status);

	@Query("SELECT dd FROM DriverDetails dd WHERE dd.user.status = :status")
	List<DriverDetails> getAllDrivers(@Param(value = "status") int status);

	@Query("SELECT dd FROM DriverDetails dd WHERE dd.user.createdBy.id = :id")
	List<DriverDetails> getDriversForEmployee(@Param(value = "id") int id);

}

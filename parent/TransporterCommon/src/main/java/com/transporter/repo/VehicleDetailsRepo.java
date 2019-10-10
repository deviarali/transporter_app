package com.transporter.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.transporter.model.VehicleDetails;

public interface VehicleDetailsRepo extends JpaRepository<VehicleDetails, Integer>{
	
	@Query(value = "select *, ( 3959 * ACOS( COS( RADIANS(:lattitude) ) * COS( RADIANS( vd.current_lattitude ) ) \r\n"
			+ "  * COS( RADIANS( vd.current_longitude ) - RADIANS(:longitude) ) + SIN( RADIANS(:lattitude) ) * SIN(RADIANS(vd.current_lattitude)) ) ) AS distance \r\n"
			+ "FROM vehicledetails vd INNER JOIN driverdetails dr ON (vd.driver_id = dr.id AND dr.driver_verification_status = 'completed' AND dr.riding_status!=1) WHERE vd.vehicle_type = :vehicleType AND  vd.vehicle_verification_status=1\r\n"
			+ "HAVING distance < :distance \r\n" + "ORDER BY distance", nativeQuery = true)
	public List<VehicleDetails> fetchSelectedVehiclesToConfirmOrder(@Param(value = "lattitude") double lattitude, @Param(value = "longitude") double longitude,
			@Param(value = "distance") double distance, @Param(value = "vehicleType") int vehicleType);

}

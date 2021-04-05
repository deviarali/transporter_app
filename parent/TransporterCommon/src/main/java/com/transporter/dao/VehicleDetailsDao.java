package com.transporter.dao;

import java.util.List;

import com.transporter.model.VehicleDetails;
import com.transporter.vo.FetchSelectedVehiclesRequest;

/**
 * @author Devappa.Arali
 *
 */

public interface VehicleDetailsDao extends GenericDao {

	VehicleDetails isVehicleExists(String vehicleNum);
	
	VehicleDetails isVehicleExistById(int id);

	VehicleDetails getVehicleByDriverId(int driverId);

	int updateLattitudeAndLongitude(int id, String lattitude, String longitude);

	List<VehicleDetails> fetchSelectedVehicles(
			FetchSelectedVehiclesRequest fetchSelectedVehiclesRequest);	
	
	public List<VehicleDetails> getAllVehicles();

}

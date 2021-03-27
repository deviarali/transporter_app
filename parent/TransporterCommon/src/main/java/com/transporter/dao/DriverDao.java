package com.transporter.dao;

import java.util.List;

import com.transporter.model.DriverDetails;
import com.transporter.vo.DriverDetailsVo;
import com.transporter.vo.VehiclesByOrderRequest;

/**
 * @author Devappa.Arali
 *
 */

public interface DriverDao extends GenericDao {

	int updateLattitudeAndLongitude(int id, String lattitude, String longitude);

	int updateDriverDocuments(int driverId, String generateFilePathAndStoreForAdhar,
			String generateFilePathAndStoreForDl);

	DriverDetails findById(int driverId);
	
	List<DriverDetails> checkVehicleAvailability(String lattitude, String longitude, double distance);

	List<DriverDetails> fetchVehiclesByOrder(VehiclesByOrderRequest vehiclesByOrderRequest);

	DriverDetails getDriverDetailsByUserId(int id);

	void updateRidingStatus(int id, int status);

	List<DriverDetails> getDriverForVehicleRegistrationByUserId(int userId);

	void updateVerifcationStatus(int id, String status);

}

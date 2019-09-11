package com.transporter.dao;

import com.transporter.model.User;
import com.transporter.model.VehicleDetails;

/**
 * @author Devappa.Arali
 *
 */

public interface VehicleDetailsDao extends GenericDao {

	VehicleDetails isVehicleExists(String vehicleNum);
	
	VehicleDetails isVehicleExistById(int id);
	

}

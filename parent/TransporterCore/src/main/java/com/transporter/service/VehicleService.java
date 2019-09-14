package com.transporter.service;

import com.transporter.model.VehicleDetails;
import com.transporter.vo.VehicleDetailsVo;

/**
 * @author Devappa.Arali
 *
 */

public interface VehicleService {

	String registerVehicle(VehicleDetailsVo vehicleDetailsVo);
	
	VehicleDetailsVo updateVehicleDetails(VehicleDetailsVo vehicleDetailsVo);
	
	VehicleDetails isVehilceExistById(int vehicleId);

	VehicleDetails getVehicleByDriverId(int id);
	
	
	
	
}

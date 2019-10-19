package com.transporter.service;

import java.util.List;

import com.transporter.model.VehicleDetails;
import com.transporter.response.LatitudeLongitudeResponse;
import com.transporter.vo.FetchSelectedVehiclesRequest;
import com.transporter.vo.FetchSelectedVehiclesResponse;
import com.transporter.vo.VehicleDetailsVo;
import com.transporter.vo.VehiclesByOrderRequest;
import com.transporter.vo.VehiclesByOrderResponse;

/**
 * @author Devappa.Arali
 *
 */

public interface VehicleService {

	String registerVehicle(VehicleDetailsVo vehicleDetailsVo);
	
	VehicleDetailsVo updateVehicleDetails(VehicleDetailsVo vehicleDetailsVo);
	
	VehicleDetails isVehilceExistById(int vehicleId);

	VehicleDetails getVehicleByDriverId(int id);

	int updateLattitudeAndLongitude(int id, String lattitude, String longitude);

	List<FetchSelectedVehiclesResponse> fetchSelectedVehicles(
			FetchSelectedVehiclesRequest fetchSelectedVehiclesRequest);

	List<VehicleDetails> fetchSelectedVehiclesToConfirmOrder(FetchSelectedVehiclesRequest fetchSelectedVehiclesRequest);

	List<VehiclesByOrderResponse> fetchVehiclesByOrder(VehiclesByOrderRequest vehiclesByOrderRequest);

	LatitudeLongitudeResponse getDriverLocations(int driverId);
	
	
	
	
}

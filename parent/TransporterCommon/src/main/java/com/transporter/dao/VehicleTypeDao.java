package com.transporter.dao;

import java.util.List;

import com.transporter.model.VehicleType;
import com.transporter.vo.VehiclesByOrderRequest;

public interface VehicleTypeDao extends GenericDao {

	List<VehicleType> getAllVehicleTypes();

	VehicleType deleteDisplayVehicle(VehicleType displayVehicle);

	List<VehicleType> fetchVehiclesByOrder(VehiclesByOrderRequest vehiclesByOrderRequest);

}

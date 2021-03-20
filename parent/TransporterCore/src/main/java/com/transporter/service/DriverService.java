package com.transporter.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.transporter.model.DriverDetails;
import com.transporter.vo.DriverDetailsVo;
import com.transporter.vo.VehiclesByOrderRequest;
import com.transporter.vo.VehiclesByOrderResponse;

/**
 * @author Devappa.Arali
 *
 */

public interface DriverService {

	String registerDriver(DriverDetailsVo driverDetailsVo);

	String updateLattitudeAndLongitude(int id, String lattitude, String longitude);

	String updateDriverDocuments(int userId, MultipartFile adharMultiPart, MultipartFile dlMultiPart);

	DriverDetailsVo updateDriverOnRoadAndOffRoad(int driverId, DriverDetailsVo detailsVo);

	DriverDetailsVo updateDriverAddress(DriverDetailsVo driverDetailsVo);
	
	String checkVehicleAvailability(String lattitude, String longitude);

	List<VehiclesByOrderResponse> fetchVehiclesByOrder(VehiclesByOrderRequest vehiclesByOrderRequest);

	int generateOtp(String mobileNumber);

	DriverDetailsVo validateOtp(String mobileNumber, String otp);

	DriverDetails findDriverById(int driverId);

	void updateRidingStatus(int id, int status);

	List<DriverDetailsVo> getAllDrivers(int status);

	DriverDetailsVo getDriverById(int driverId);

	List<DriverDetailsVo> getTopDriversForWeek(int count);

	int deleteDriver(int id, String reason);

	List<DriverDetailsVo> getDriversForEmployee(int id);

	List<DriverDetailsVo> getDriverForVehicleRegistrationByUserId(int userId);

	

}

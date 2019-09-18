package com.transporter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.transporter.constants.WebConstants;
import com.transporter.dao.DriverDao;
import com.transporter.enums.UserRoleEnum;
import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.model.DriverDetails;
import com.transporter.model.User;
import com.transporter.model.VehicleDetails;
import com.transporter.notifications.TransporterPushNotifications;
import com.transporter.service.DriverService;
import com.transporter.service.UserService;
import com.transporter.service.VehicleService;
import com.transporter.utility.TransporterUtility;
import com.transporter.vo.DriverDetailsVo;
import com.transporter.vo.UserRoleVo;
import com.transporter.vo.UserVo;
import com.transporter.vo.VehiclesByOrderRequest;
import com.transporter.vo.VehiclesByOrderResponse;

/**
 * @author Devappa.Arali
 *
 */

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	private DriverDao driverDao;

	@Autowired
	private UserService userService;

	@Autowired
	private TransporterUtility transporterUtility;

	@Autowired
	private TransporterPushNotifications transporterPushNotifications;

	@Autowired
	private VehicleService vehicleService;

	@Value("${surrounding.area}")
	private double surrounding;

	@Override
	@Transactional
	public String registerDriver(DriverDetailsVo driverDetailsVo) {
		String response = null;
		UserVo userExists = userService.isUserExists(driverDetailsVo.getUser().getMobileNumber());
		if (null != userExists) {
			throw new BusinessException(ErrorCodes.MOEXISTS.name(), ErrorCodes.MOEXISTS.value());
		}
		UserVo userVo = driverDetailsVo.getUser();
		UserRoleVo userRoleVo = new UserRoleVo();
		userRoleVo.setId(UserRoleEnum.DRIVER.getId());
		userVo.setUserRole(userRoleVo);
		User user = userService.registerUser(userVo);
		DriverDetails driverDetails = new DriverDetails();
		driverDetails.setCreatedOn(new Date());
		driverDetails.setDriverName(userVo.getFirstName());
		driverDetails.setDriverVerificationStatus("pending");
		driverDetails.setOnRoad(0);
		driverDetails.setCreatedBy(driverDetailsVo.getCreatedBy().getId());
		driverDetails.setUser(user);
		driverDao.save(driverDetails);
		if (driverDetails.getId() > 0) {
			response = WebConstants.SUCCESS;
		} else {
			throw new BusinessException(ErrorCodes.NOTSAVED.name(), ErrorCodes.NOTSAVED.value());
		}
		return response;
	}

	@Override
	@Transactional
	public String updateLattitudeAndLongitude(int id, String lattitude, String longitude) {
		String response = null;
		// int updated = driverDao.updateLattitudeAndLongitude(id, lattitude,
		// longitude);
		int updated = vehicleService.updateLattitudeAndLongitude(id, lattitude, longitude);
		if (updated == 1) {
			response = WebConstants.SUCCESS;
		}
		return response;
	}

	@Override
	@Transactional
	public String updateDriverDocuments(int userId, MultipartFile adharMultiPart, MultipartFile dlMultiPart) {
		User userExists = userService.findById(userId);
		if (null == userExists) {
			throw new BusinessException(ErrorCodes.UNFOUND.name(), ErrorCodes.UNFOUND.value());
		}
		String generateFilePathAndStoreForAdhar = transporterUtility.generateFilePathAndStore(adharMultiPart, "driver");
		String generateFilePathAndStoreForDl = transporterUtility.generateFilePathAndStore(dlMultiPart, "driver");
		if (!(StringUtils.isBlank(generateFilePathAndStoreForAdhar))
				|| !(StringUtils.isBlank(generateFilePathAndStoreForDl))) {
			int updated = driverDao.updateDriverDocuments(userId, generateFilePathAndStoreForAdhar,
					generateFilePathAndStoreForDl);
			if (updated != 0) {
				return "Documents Updated..";
			}
		}
		return null;
	}

	@Override
	@Transactional
	public DriverDetailsVo updateDriverOnRoadAndOffRoad(int driverId, DriverDetailsVo detailsVo) {
		DriverDetails driverDetails = driverDao.findById(driverId);
		if (driverDetails == null) {
			throw new BusinessException(ErrorCodes.UNFOUND.name(), ErrorCodes.UNFOUND.value());
		}
		driverDetails.setOnRoad(detailsVo.getOnRoad());
		driverDao.saveOrUpdate(driverDetails);
		return DriverDetails.convertModelToVo(driverDetails);
	}

	@Override
	@Transactional
	public DriverDetailsVo updateDriverAddress(DriverDetailsVo driverDetailsVo) {
		DriverDetails driverDetails = driverDao.findById(driverDetailsVo.getId());
		if (driverDetails == null) {
			return null;
		}
		driverDetails.setDriverName(driverDetailsVo.getDrivername());
		driverDetails.setAddressCity(driverDetailsVo.getAddressCity());
		driverDetails.setAddressState(driverDetailsVo.getAddressState());
		driverDetails.setAddressStreet(driverDetailsVo.getAddressStreet());
		driverDetails.setAddressZipcode(driverDetailsVo.getAddressZipcode());
		driverDetails.setDateOfBirth(driverDetailsVo.getDateofbirth());
		driverDao.saveOrUpdate(driverDetails);
		return DriverDetails.convertModelToVo(driverDetails);
	}

	@Override
	@Transactional
	public String checkVehicleAvailability(String lattitude, String longitude) {
		List<DriverDetails> driverDetailsList = driverDao.checkVehicleAvailability(lattitude, longitude, surrounding);

		String response = null;
		if (null != driverDetailsList && !driverDetailsList.isEmpty()) {
			List<String> devicesToken = new ArrayList<String>();
			devicesToken.add(driverDetailsList.get(0).getUser().getFcmToken());
			// String devicesToken[] =
			// {"euRR9XSIZCE:APA91bEWht_WfZjySzUCiKDTaQbNNP8smnx7snHy3jeIAFvxVVZqRmsU1ffvjuDBhFEOiXSjMW-UteYXPeECuv8Il6h4SfGWdGnj3KJbgYJLgscs-4f_N7Lxbp72umt_JYak_Q20Bh7V"};
			response = transporterPushNotifications.pushNotifications(devicesToken);
		}
		return response;
	}

	@Override
	@Transactional
	public List<VehiclesByOrderResponse> fetchVehiclesByOrder(VehiclesByOrderRequest vehiclesByOrderRequest) {
		List<VehiclesByOrderResponse> orderResponse = new ArrayList<VehiclesByOrderResponse>();
		vehiclesByOrderRequest.setSurroundingDistances(surrounding);
		List<DriverDetails> driverDetailsList = driverDao.fetchVehiclesByOrder(vehiclesByOrderRequest);
		for (DriverDetails details : driverDetailsList) {
			DriverDetails details2 = (DriverDetails) driverDao.get(DriverDetails.class, details.getId());
			VehiclesByOrderResponse response = new VehiclesByOrderResponse();
			VehicleDetails vehicle = vehicleService.getVehicleByDriverId(details2.getId());
			response.setVehicleType(vehicle.getVehicleType().getId());
			response.setVehicleSelectedUrl(vehicle.getVehicleType().getSelectedVehicleUrl());
			response.setVehicleUnSelecetedUrl(vehicle.getVehicleType().getUnselectedVehicleUrl());
			response.setPrice(vehicle.getVehicleType().getPrice() * vehiclesByOrderRequest.getDistance());
			orderResponse.add(response);
		}
		return orderResponse;
	}

	@Override
	public int generateOtp(String mobileNumber) {
		UserVo userExists = userService.isUserExists(mobileNumber);
		if (null == userExists) {
			throw new BusinessException(ErrorCodes.CNFOUND.name(), ErrorCodes.CNFOUND.value());
		}
		return userService.generateOtp(mobileNumber);
	}

	@Override
	@Transactional
	public DriverDetailsVo validateOtp(String mobileNumber, String otp) {
		DriverDetailsVo driverDetailsVo = null;
		UserVo userVo = userService.validateOtp(mobileNumber, otp);
		if (userVo == null) {
			throw new BusinessException(ErrorCodes.INVALIDOTP.name(), ErrorCodes.INVALIDOTP.value());
		}
		DriverDetails driverDetailsByUserId = driverDao.getDriverDetailsByUserId(userVo.getId());
		driverDetailsVo = DriverDetails.convertModelToVo(driverDetailsByUserId);
		return driverDetailsVo;
	}
}

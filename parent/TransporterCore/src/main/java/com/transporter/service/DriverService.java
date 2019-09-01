package com.transporter.service;

import org.springframework.web.multipart.MultipartFile;

import com.transporter.vo.DriverDetailsVo;

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

}

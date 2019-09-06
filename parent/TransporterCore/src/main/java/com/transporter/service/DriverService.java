package com.transporter.service;

import com.transporter.vo.DriverDetailsVo;

/**
 * @author Devappa.Arali
 *
 */

public interface DriverService {

	String registerDriver(DriverDetailsVo driverDetailsVo);

	String updateLattitudeAndLongitude(int id, String lattitude, String longitude);

}

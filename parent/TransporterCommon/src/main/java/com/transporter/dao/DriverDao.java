package com.transporter.dao;

import com.transporter.model.DriverDetails;

/**
 * @author Devappa.Arali
 *
 */

public interface DriverDao extends GenericDao {

	int updateLattitudeAndLongitude(int id, String lattitude, String longitude);

	int updateDriverDocuments(int driverId, String generateFilePathAndStoreForAdhar,
			String generateFilePathAndStoreForDl);

	DriverDetails findById(int driverId);

}

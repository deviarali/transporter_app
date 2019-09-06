package com.transporter.dao;

/**
 * @author Devappa.Arali
 *
 */

public interface DriverDao extends GenericDao {

	int updateLattitudeAndLongitude(int id, String lattitude, String longitude);

}

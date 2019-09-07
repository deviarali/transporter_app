package com.transporter.service;

import java.util.List;

import com.transporter.model.TripDetails;

/**
 * @author Devappa.Arali
 *
 */

public interface TripDetailsService {

	List<TripDetails> getTripHistory(int id, int tripstatus);

}

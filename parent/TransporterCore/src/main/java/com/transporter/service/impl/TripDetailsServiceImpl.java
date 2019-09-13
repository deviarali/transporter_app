package com.transporter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transporter.model.TripDetails;
import com.transporter.repo.TripDetailsRepo;
import com.transporter.service.TripDetailsService;

/**
 * @author Devappa.Arali
 *
 */

@Service
public class TripDetailsServiceImpl implements TripDetailsService {

	@Autowired
	TripDetailsRepo  tripDetailsRepo;
	
	@Override
	public List<TripDetails> getTripHistory(int id, int tripstatus) 
	{
		List<TripDetails> history = tripDetailsRepo.getHistory(id,tripstatus);
		return history;
	}
}

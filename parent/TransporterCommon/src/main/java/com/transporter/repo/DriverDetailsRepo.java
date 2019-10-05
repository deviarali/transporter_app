package com.transporter.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transporter.model.DriverDetails;

public interface DriverDetailsRepo extends JpaRepository<DriverDetails, Integer>{

}

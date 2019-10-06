package com.transporter.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transporter.model.VehicleType;

public interface VehicleTypeRepo extends JpaRepository<VehicleType, Integer>{

	VehicleType findByVehicleName(String vehicleName);

}

package com.transporter.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transporter.model.CustomerDetails;

public interface CustomerDetailsRepo extends JpaRepository<CustomerDetails, Integer>{

}

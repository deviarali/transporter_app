package com.transporter.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.transporter.model.CustomerDetails;

public interface CustomerDetailsRepo extends JpaRepository<CustomerDetails, Integer>{

	@Query("SELECT cd FROM CustomerDetails cd where cd.user.status= :status")
	List<CustomerDetails> getAllCustomers(@Param(value = "status") int status);

}

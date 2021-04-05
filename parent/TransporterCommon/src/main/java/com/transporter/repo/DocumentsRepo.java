package com.transporter.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transporter.model.Documents;

public interface DocumentsRepo extends JpaRepository<Documents, Integer>{

}

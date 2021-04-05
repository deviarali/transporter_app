package com.transporter.service;

import com.transporter.model.DriverDetails;
import com.transporter.vo.DocumentsVo;

/**
 * @author Naveen.R
 *
 */
public interface DocumentsService {
	
	DocumentsVo addDocuments(int userId, String generatedFilePath, String docType, DriverDetails driverDetails);

}

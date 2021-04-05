package com.transporter.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.model.Documents;
import com.transporter.model.DriverDetails;
import com.transporter.repo.DocumentsRepo;
import com.transporter.service.DocumentsService;
import com.transporter.vo.DocumentsVo;

/**
 * @author Naveen.R
 *
 */

@Service
@Transactional
public class DocumentsServiceImpl implements DocumentsService {

	@Autowired
	private DocumentsRepo documentsRepository;

	@Override
	public DocumentsVo addDocuments(int userId, String generatedFilePath, String docType, DriverDetails driverDetails) {
		Documents doc = new Documents();
		doc.setDocumentType(docType);
		doc.setDocumentUrl(generatedFilePath);
		doc.setDriverDetails(driverDetails);
		doc.setCreatedOn(new Date());
		Documents save = documentsRepository.save(doc);
		if (save != null) {
			return Documents.convertModelToVo(save);
		}
		return null;
	}

}

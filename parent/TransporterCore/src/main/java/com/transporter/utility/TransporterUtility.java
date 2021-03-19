package com.transporter.utility;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.transporter.model.ExceptionMaster;
import com.transporter.service.ExceptionService;

/**
 * @author Devappa.Arali
 *
 */

@Component
public class TransporterUtility {

	@Value("${file.path}")
	private String filePath;

	@Autowired
	private ExceptionService exceptionService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TransporterUtility.class);

	public String generateFilePathAndStore(MultipartFile multipart, String typeOfUser) {
		String uniqueId = uuidString();
		LOGGER.info("UploadFile " + multipart.getOriginalFilename());
		Date date = new Date();
		if (StringUtils.isBlank(typeOfUser)) {
			typeOfUser = "transporter";
		}
		String photoType = "profile";
		String fileLocation = photoType + "/" + typeOfUser + uniqueId + date.getTime()
				+ multipart.getOriginalFilename();
		File directory = new File(filePath + photoType);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		try {
			multipart.transferTo(new File(filePath + fileLocation));
		} catch (IllegalStateException | IOException e) {
			LOGGER.error("File not saved and exception is " + e.getMessage());
			return null;
		}
		return fileLocation;
	}

	private String uuidString() {
		return UUID.randomUUID().toString();
	}

	public ExceptionMaster getExceptionMasterByType(String exceptionType) {

		return exceptionService.getExceptionMasterByType(exceptionType);
	}

}

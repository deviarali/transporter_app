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

	public String generateFilePathAndStore(Integer userId, MultipartFile multipart, String typeOfUser,
			String fileType) {
		String uniqueId = uuidString();
		LOGGER.info("UploadFile " + multipart.getOriginalFilename());
		Date date = new Date();

		if (StringUtils.isBlank(typeOfUser)) {
			typeOfUser = "transporter";
		}

		String rootFolder = "";
		String fileLocation = "";
		File directory = null;

		if (typeOfUser.equalsIgnoreCase("user")) {
			rootFolder = "profile";
			fileLocation = rootFolder + "/" + typeOfUser + uniqueId + date.getTime() + "-"
					+ multipart.getOriginalFilename();
			directory = new File(filePath + rootFolder);
		} else if (typeOfUser.equalsIgnoreCase("driver")) {
			rootFolder = "driverDocuments";
			String subFolder = typeOfUser + "_" + userId;
			fileLocation = rootFolder + "/" + subFolder + "/" +  generateFileName(fileType, multipart.getOriginalFilename());
			directory = new File(filePath + rootFolder + "/" + subFolder);
		} else if (typeOfUser.equalsIgnoreCase("vehicle")) {
			rootFolder = "vehicle";
			fileLocation = rootFolder + "/" + typeOfUser + uniqueId + date.getTime() + "-"
					+ multipart.getOriginalFilename();
			directory = new File(filePath + rootFolder);
		} else {
			fileLocation = "";
			directory = new File(filePath);
		}

		if (directory != null && !directory.exists()) {
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

	private String generateFileName(String fileType, String fileName) {
		return fileType + "." + fileName.substring(fileName.indexOf(".") + 1);

	}

}

package com.transporter.utility;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;

/**
 * @author Devappa.Arali
 *
 */

@Component
public class TransporterUtility {
	
	@Value("${file.path}")
	private String filePath;
	
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(TransporterUtility.class);
	
	public String genearteFilePathAndStore(MultipartFile multipart, String typeOfUser) {
		String uniqueId = uuidString();
		LOGGER.info("UploadFile "+ multipart.getOriginalFilename());
		Date date = new Date();
		if(StringUtils.isNullOrEmpty(typeOfUser)) {
			typeOfUser = "transporter";
		}
		String fileLocation = filePath  + typeOfUser + uniqueId + date.getTime();
		File directory = new File(filePath);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		try {
			multipart.transferTo(new File(fileLocation));
		} catch (IllegalStateException | IOException e) {
			LOGGER.error("File not saved "+e.getMessage());
			return null;
		}
		return fileLocation;
	}
	
	private String uuidString() {
		return UUID.randomUUID().toString();
	}

}

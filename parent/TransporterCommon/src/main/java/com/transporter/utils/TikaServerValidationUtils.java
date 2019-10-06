/**
 * 
 */
package com.transporter.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/*import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.springframework.web.accept.MediaTypeFileExtensionResolver;
import org.xml.sax.SAXException;

import com.mchange.v1.lang.BooleanUtils;
import com.transporter.exception.TikaServerCustomException;*/

/**
 * @author SHARAN A
 *
 */
public class TikaServerValidationUtils {

	private static String MEDIA_TYPE_IMAGE = "image";

	/*public static Boolean validateImageFile(File file) throws IOException, SAXException, TikaException {

		boolean isValiateSuccess = false;
		TikaConfig tika = new TikaConfig();

		Metadata metadata = new Metadata();
		metadata.set(Metadata.RESOURCE_NAME_KEY, file.toString());
		MediaType mediaTypeype = tika.getDetector().detect(TikaInputStream.get(file), metadata);

		if (mediaTypeype != null && MEDIA_TYPE_IMAGE.equals(mediaTypeype.getType())) {
			isValiateSuccess = true;
		}
		
		if(!isValiateSuccess) {
			throw new TikaServerCustomException("Uploaded image file has problem. Please re upload.");
		}

		return isValiateSuccess;
	}
	
	public static Boolean validateImageFile(InputStream inputStream) throws IOException, SAXException, TikaException {

		boolean isValiateSuccess = false;
		TikaConfig tika = new TikaConfig();

		MediaType mediaTypeype = tika.getDetector().detect(TikaInputStream.get(inputStream), new Metadata());
		
		System.out.println("File is " + mediaTypeype.getType());
		
		if (mediaTypeype != null && MEDIA_TYPE_IMAGE.equals(mediaTypeype.getType())) {
			isValiateSuccess = true;
		}
		
		if(!isValiateSuccess) {
			throw new TikaServerCustomException("Uploaded image file has problem. Please re upload.");
		}

		return isValiateSuccess;
	}
*/
}

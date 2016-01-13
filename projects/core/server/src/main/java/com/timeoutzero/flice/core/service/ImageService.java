package com.timeoutzero.flice.core.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeoutzero.flice.core.domain.Community;

import io.redspark.simple.file.manager.SimpleFileManager;

@Component
public class ImageService {
	
	private static Logger LOG = LoggerFactory.getLogger(ImageService.class);
	
	private static final String AWS_S3_ENDPOINT = "http:/flice.s3.amazonaws.com";
	public static enum TYPE { THUMB , COVER }
	
	@Autowired
	private SimpleFileManager simpleFileManager;

	public String write(Community community, String base64, TYPE type) {
		
		String[] split = base64.split(",");
		String mime  = split[0].substring(split[0].indexOf(":") + 1, split[0].indexOf(";")).replace("image/", "").trim();
		String image = split[1].trim();
		
		File file = null;
	
		try {
			
			file = File.createTempFile(type.toString().toLowerCase() + "_", "." + mime);
			byte[] decodeBase64 = Base64.decodeBase64(image);
			FileUtils.writeByteArrayToFile(file, decodeBase64);
			
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
		}
		
		String path = String.format("s3://flice/community/%d/static/%s.%s", community.getId(), type.toString().toLowerCase(), mime);
		
		try {
			
			simpleFileManager.write(file, path);
			
		} catch (IOException e) {
			LOG.error(e.getLocalizedMessage());
		}
		
		FileUtils.deleteQuietly(file);

		return AWS_S3_ENDPOINT + path;
	}
}

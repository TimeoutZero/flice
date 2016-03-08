package com.timeoutzero.flice.core.service;

import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.User;

@Component
public class ImageService {
	
	private static Logger LOG = LoggerFactory.getLogger(ImageService.class);
	
	private static final String BUCKET_FLICE = "flice";
	public static enum TYPE { THUMB , COVER }

	@Autowired
	private String awsEndpoint;
	
	@Autowired
	private AmazonS3Client amazonS3;
	
	public String write(Community community, TYPE type, String base64) {
		
		File file = createTemporaryFile(base64);  
		String key = String.format("flice/community/%d/static/%s.%s", community.getId(), type.toString().toLowerCase(), FilenameUtils.getExtension(file.getName()));
		
		return saveFileAtS3(file, BUCKET_FLICE, key);
	}
	
	public String write(User user, String base64) {
		
		File file = createTemporaryFile(base64);  
		String key = "flice/account/photos/" + user.getAccountId() + FilenameUtils.getExtension(file.getName());

		return saveFileAtS3(file, BUCKET_FLICE, key);
	}
	
	public File createTemporaryFile(String base64) {
		
		String[] split = base64.split(",");
		String mime  = split[0].substring(split[0].indexOf(":") + 1, split[0].indexOf(";")).replace("image/", "").trim();
		String image = split[1].trim();
		
		File file = null;
	
		try {
			
			file = File.createTempFile("tmp_", "." + mime);
			byte[] decodeBase64 = Base64.decodeBase64(image);
			FileUtils.writeByteArrayToFile(file, decodeBase64);
			
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
		}
		
		return file;
	}

	private String saveFileAtS3(File file, String bucket, String key) {
		
		PutObjectRequest put = new PutObjectRequest(bucket, key, file);
		put.withCannedAcl(CannedAccessControlList.PublicRead);
		
		amazonS3.putObject(put);
		
		FileUtils.deleteQuietly(file);

		return awsEndpoint + File.separator  + key;
	}
}

package com.timeoutzero.flice.core.util;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

public class ImageMockUtil {

	public static String getMockBase64Image() throws IOException, URISyntaxException {
		byte[] byteArray = IOUtils.toByteArray(ImageMockUtil.class.getClassLoader().getResourceAsStream("images/batman.jpg"));
		
		StringBuilder builder = new StringBuilder("data:image/png;base64,");
		builder.append(Base64.encodeBase64String(byteArray));
		
		return builder.toString();
	}
}

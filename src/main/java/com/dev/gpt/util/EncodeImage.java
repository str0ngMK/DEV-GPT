package com.dev.gpt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class EncodeImage {
	public String encodeImage(String imagePath) throws IOException {
		File imageFile = new File(imagePath);
		byte[] imageData = new byte[(int) imageFile.length()];
		FileInputStream fileInputStream = new FileInputStream(imageFile);
		fileInputStream.read(imageData);
		fileInputStream.close();
		return Base64.getEncoder().encodeToString(imageData);
	}
}

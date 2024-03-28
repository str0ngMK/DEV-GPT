package com.dev.gpt.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.dev.gpt.config.ApplicationConfig;
import com.dev.gpt.util.EncodeImage;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ImageSearchService {
	@Autowired
	private EncodeImage encodeImage;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ApplicationConfig applicationConfig;

	public ResponseEntity<String> SendImageSearch() throws IOException {
		String imagePath = "";
		String base64Image = encodeImage.encodeImage(imagePath);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + applicationConfig.getApiKey());

		HashMap<String, Object> body = body(base64Image);
		
		System.out.println(mapper.writeValueAsString(body));

		HttpEntity<String> requestEntity = new HttpEntity<>(mapper.writeValueAsString(body), headers);
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange("https://api.openai.com/v1/chat/completions",
				HttpMethod.POST, requestEntity, String.class);

		return response;
	}

	private HashMap<String, Object> body(String base64Image) {
		HashMap<String, Object> body = new HashMap<>();
		
		body.put("model", "gpt-4-vision-preview");
		
		List<HashMap<String, Object>> messagesList = new ArrayList<>();
		HashMap<String, Object> message = new HashMap<>();
		message.put("role", "user");
		
		List<HashMap<String, Object>> contentList = new ArrayList<>();
		
		for (int i = 0; i < 2; i++) {
			HashMap<String, Object> content = new HashMap<>();
			if(i == 0) {
				content.put("type", "text");
				content.put("text", "이 이미지에는 무엇이 있나요? 거기에서 Waldo를 찾을 수 있나요?");
			} else {
				content.put("type", "image_url");
//				content.put("url", "data:image/jpeg;vase64," + base64Image);
				content.put("image_url", "https://blog.kuula.co/assets/waldo/header.png");
			}
			contentList.add(content);
		}
		message.put("content", contentList);
		messagesList.add(message);
		
		body.put("messages", messagesList);
		body.put("temperature", 1);
		body.put("top_p", 1);
		body.put("n", 1);
		body.put("stream", false);
		body.put("max_tokens", 1000);
		body.put("presence_penalty", 0);
		body.put("frequency_penalty", 0);
		return body;
	}
}

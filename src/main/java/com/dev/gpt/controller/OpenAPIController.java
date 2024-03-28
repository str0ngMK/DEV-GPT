package com.dev.gpt.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.gpt.service.AskDevService;
import com.dev.gpt.service.ImageSearchService;

@RestController
@RequestMapping("/ai")
public class OpenAPIController {
	private final ChatClient chatClient;
	
	@Autowired
	private ImageSearchService imageSearchService;
	
	@Autowired
	private AskDevService askDevService;
	
	@Autowired
	public OpenAPIController(ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	@GetMapping("/send")
	public Map<String, String> completion(@RequestParam(value = "message") String message) {
		return Map.of("generation", chatClient.call(message));
	}
	
	
	@PostMapping("/send/img")
	public ResponseEntity<String> imageSearch() throws IOException {
		return imageSearchService.SendImageSearch();
	}
	
	@PostMapping("/send/dev")
	public ResponseEntity<String> AskDev(@RequestBody HashMap<String, Object> reqBody) throws IOException{
		return askDevService.askDev(reqBody);
		
	}
	
}

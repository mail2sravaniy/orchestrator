package com.centime.orchestrator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.centime.orchestrator.model.Request;

/**
 * Business logic service for orchestrating calls to Service 2 and Service 3
 * 
 * Implements the core orchestration logic as specified in requirements: 1.
 * Calls Service 2 GET method to get greeting 2. Calls Service 3 POST method
 * with same payload to get processed name 3. Concatenates and returns the
 * combined response
 */
@Service
public class OrchestratorService {

	private static final Logger logger = LoggerFactory.getLogger(OrchestratorService.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service2.url:http://localhost:8082}")
	private String service2Url;

	@Value("${service3.url:http://localhost:8083}")
	private String service3Url;

	/**
	 * Main orchestration method
	 * 
	 * @param request UserRequest containing name and surname
	 * @param traceId Unique trace ID for logging
	 * @return Concatenated response from Service 2 and Service 3
	 */
	public String orchestrateServices(Request request, String traceId) {
		MDC.put("traceId", traceId);

		logger.info("[TraceID: {}] Starting orchestration process", traceId);

		try {
			// Step 1: Call Service 2 GET method
			logger.info("[TraceID: {}] Calling Service 2 GET at: {}", traceId, service2Url);
			String greeting = callService2(traceId);

			// Step 2: Call Service 3 POST method with same payload
			logger.info("[TraceID: {}] Calling Service 3 POST at: {}", traceId, service3Url);
			String processedName = callService3(request, traceId);

			// Step 3: Concatenate responses
			String finalResult = greeting + " " + processedName;
			logger.info("[TraceID: {}] Orchestration completed. Final result: {}", traceId, finalResult);

			return finalResult;

		} catch (Exception e) {
			logger.error("[TraceID: {}] Orchestration failed: {}", traceId, e.getMessage());
			throw new RuntimeException("Service orchestration failed: " + e.getMessage());
		}
	}

	/**
	 * Call Service 2 GET method As specified: "Get call of Service 2"
	 */
	private String callService2(String traceId) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("X-Trace-Id", traceId);
			HttpEntity<String> entity = new HttpEntity<>(headers);

			String url = service2Url + "/api/greeting";
			logger.info("[TraceID: {}] Making GET request to Service 2: {}", traceId, url);

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			String result = response.getBody();
			logger.info("[TraceID: {}] Service 2 response: {}", traceId, result);

			return result;

		} catch (Exception e) {
			logger.error("[TraceID: {}] Failed to call Service 2: {}", traceId, e.getMessage());
			throw new RuntimeException("Service 2 call failed: " + e.getMessage());
		}
	}

	/**
	 * Call Service 3 POST method with same payload As specified: "Post call of
	 * Service 3 using the same payload"
	 */
	private String callService3(Request request, String traceId) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("X-Trace-Id", traceId);
			HttpEntity<Request> entity = new HttpEntity<>(request, headers);

			String url = service3Url + "/api/process";
			logger.info("[TraceID: {}] Making POST request to Service 3: {}", traceId, url);

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

			String result = response.getBody();
			logger.info("[TraceID: {}] Service 3 response: {}", traceId, result);

			return result;

		} catch (Exception e) {
			logger.error("[TraceID: {}] Failed to call Service 3: {}", traceId, e.getMessage());
			throw new RuntimeException("Service 3 call failed: " + e.getMessage());
		}
	}
}

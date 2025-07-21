package com.centime.orchestrator.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centime.orchestrator.model.Response;
import com.centime.orchestrator.model.Request;
import com.centime.orchestrator.service.OrchestratorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Main REST Controller for Service 1 - Orchestrator
 * 
 * Exposes two HTTP methods as specified in requirements: 1. GET /api/health -
 * Returns "Up" if service is up 2. POST /api/orchestrate - Returns concatenated
 * responses from Service 2 and Service 3
 */

@RestController
@RequestMapping("/api")
@Tag(name = "Orchestrator Service", description = "Main orchestration service for microservice communication")
public class OrchestratorController {

	private static final Logger logger = LoggerFactory.getLogger(OrchestratorController.class);

	@Autowired
	private OrchestratorService orchestratorService;

	/**
	 * GET method - Returns "Up" if service is up
	 * 
	 * As specified in requirements: "From the get method return 'Up' if service is
	 * up"
	 */
	@GetMapping("/health")
	@Operation(summary = "Health Check", description = "Returns 'Up' if service is running")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Service is healthy") })
	public ResponseEntity<String> health() {
		String traceId = UUID.randomUUID().toString();
		MDC.put("traceId", traceId);

		logger.info("[TraceID: {}] GET /api/health - Health check called", traceId);

		try {
			return ResponseEntity.ok("Up");
		} finally {
			MDC.clear();
		}
	}

	@PostMapping("/orchestrate")
	@Operation(summary = "Orchestrate Services", description = "Calls Service 2 (GET) and Service 3 (POST) with same payload, returns concatenated response")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully orchestrated services"),
			@ApiResponse(responseCode = "400", description = "Invalid request payload"),
			@ApiResponse(responseCode = "500", description = "Service orchestration failed") })
	public ResponseEntity<Response> orchestrate(@Valid @RequestBody Request request) {
		String traceId = UUID.randomUUID().toString();
		MDC.put("traceId", traceId);

		logger.info("[TraceID: {}] POST /api/orchestrate - Request received: {}", traceId, request);

		try {
			// Delegate to service layer for orchestration logic
			String result = orchestratorService.orchestrateServices(request, traceId);

			logger.info("[TraceID: {}] POST /api/orchestrate - Final response: {}", traceId, result);

			return ResponseEntity.ok(new Response(result));

		} catch (Exception e) {
			logger.error("[TraceID: {}] POST /api/orchestrate - Error occurred: {}", traceId, e.getMessage());
			throw e;
		} finally {
			MDC.clear();
		}
	}
}

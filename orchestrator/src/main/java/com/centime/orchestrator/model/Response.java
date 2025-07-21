package com.centime.orchestrator.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response model for the orchestrated result
 * 
 * Represents the JSON response that will be returned: { "message": "Hello John
 * Doe" }
 */
@Schema(description = "API response containing the orchestrated message")
public class Response {

	@Schema(description = "The concatenated message from Service 2 and Service 3", example = "Hello John Doe")
	private String message;

	// Default constructor
	public Response() {
	}

	// Constructor with message
	public Response(String message) {
		this.message = message;
	}

	// Getter and Setter
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

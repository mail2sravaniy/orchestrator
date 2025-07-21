package com.centime.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Service 1 - Orchestrator Application
 * 
 * Main entry point for the orchestration microservice. This service coordinates
 * calls between Service 2 (Greeting) and Service 3 (Processor).
 * 
 * Features: - REST API orchestration - JavaEE log tracing with trace IDs -
 * Swagger UI documentation - Comprehensive error handling
 */

@SpringBootApplication
public class OrchestratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrchestratorApplication.class, args);

		System.out.println("");
		System.out.println("🚀 Service 1 (Orchestrator) started successfully!");
		System.out.println("🌐 Server: http://localhost:8081");
		System.out.println("📖 Swagger UI: http://localhost:8081/swagger-ui/index.html");
		System.out.println("🏥 Health Check: http://localhost:8081/api/health");
		System.out.println("");
	}

    /**
     * RestTemplate bean for making HTTP calls to other microservices
     */
    @Bean
    RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

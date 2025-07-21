package com.centime.orchestrator.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


/**
 * Request model for user data
 * 
 * Represents the JSON payload that will be sent to this service:
 * {
 *   "name": "John",
 *   "surname": "Doe"
 * }
 */

@Schema(description = "User request containing name and surname")
public class Request {
    
    @NotBlank(message = "Name cannot be empty")
    @Schema(description = "User's first name", example = "John", required = true)
    private String name;
    
    @NotBlank(message = "Surname cannot be empty") 
    @Schema(description = "User's surname", example = "Doe", required = true)
    private String surname;
    
    // Default constructor
    public Request() {}
    
    // Constructor with parameters
    public Request(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
    
    // Getters and Setters
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    public String getSurname() { 
        return surname; 
    }
    
    public void setSurname(String surname) { 
        this.surname = surname; 
    }
    
    @Override
    public String toString() {
        return "UserRequest{name='" + name + "', surname='" + surname + "'}";
    }
}



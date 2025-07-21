# Service 1 - Orchestrator Microservice

## 🎯 Overview
Service 1 is the main orchestration service that coordinates calls between Service 2 (Greeting) and Service 3 (Processor). It demonstrates REST API orchestration, JavaEE log tracing, and comprehensive error handling.

## ✨ Features
- ✅ **Two HTTP Methods** - GET health check and POST orchestration
- ✅ **Swagger UI Documentation** - Interactive API documentation
- ✅ **JavaEE Log Tracing** - Unique trace IDs across service calls
- ✅ **Error Handling** - Comprehensive validation and exception handling
- ✅ **Spring Boot 3.2.0** - Modern Spring Boot implementation
- ✅ **Docker Ready** - Complete containerization support

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Docker (optional)

### Build and Run
```bash
# Make scripts executable
chmod +x *.sh

# Build the service
./build.sh

# Run the service
./run.sh
```

### Test the Service
```bash
# Run comprehensive tests
./test.sh

# Manual health check
curl http://localhost:8081/api/health

# Manual orchestration test (requires Service 2 and 3 running)
curl -X POST http://localhost:8081/api/orchestrate \
  -H "Content-Type: application/json" \
  -d '{"name": "John", "surname": "Doe"}'
```

## 📋 API Endpoints

### Health Check
```http
GET /api/health
```
**Response:** `"Up"`

### Orchestrate Services
```http
POST /api/orchestrate
Content-Type: application/json

{
  "name": "John",
  "surname": "Doe"
}
```
**Response:**
```json
{
  "message": "Hello John Doe"
}
```

## 🏗️ Architecture
This service implements the orchestration pattern:
1. Receives client request with user data
2. Calls Service 2 GET /api/greeting → "Hello"
3. Calls Service 3 POST /api/process → "John Doe"
4. Combines responses → "Hello John Doe"
5. Returns final result to client

## 🔍 Configuration
Edit `src/main/resources/application.yml`:
```yaml
service2:
  url: http://localhost:8082
service3:
  url: http://localhost:8083
```

## 🐳 Docker Usage
```bash
# Build Docker image
docker build -t service1-orchestrator .

# Run with Docker Compose
docker-compose up -d
```

## 📖 Documentation
- **Swagger UI**: http://localhost:8081/swagger-ui/index.html
- **API Docs**: http://localhost:8081/v3/api-docs
- **Health Check**: http://localhost:8081/actuator/health

## 🔍 Logging
All requests include trace IDs for monitoring:
```
[TraceID: abc-123] Service1 - POST /orchestrate called
[TraceID: abc-123] Service1 - Calling Service 2
[TraceID: abc-123] Service1 - Calling Service 3
[TraceID: abc-123] Service1 - Final response: Hello John Doe
```

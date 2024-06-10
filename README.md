## JobNest - Where Dreams Find Their Match
## Overview
**JobNest is a comprehensive microservice-based application designed to streamline and enhance the job search and application process. The architecture follows best practices for microservices, ensuring scalability, flexibility and maintainability. Key components of the system include an API Gateway for request routing, multiple specialized services for handling distinct aspects of the application and asynchronous communication facilitated by Kafka. The system also includes distributed tracing with Zipkin, configuration management with GitHub and robust security with JWT for authorization. PostgreSQL is used as the primary database for data persistence.*

## Architecture
### API Gateway Service
The API Gateway Service is the entry point for all client requests. It performs the following functions:

* Authentication and Authorization: Ensures that requests are authenticated and authorized via the Authentication Service.
* Request Routing: Routes authenticated requests to the appropriate microservices.

## Microservices
**1. Company Service**

* **Description:** Manages company-related data and operations.
* **Dependencies:**
  1. Job Service
  2. Reviews Service
* **Communication:** Uses Feign clients for synchronous communication with dependent services.

**2. Job Service**

* **Description:** Handles job postings, job details, and related operations.
* **Dependencies:**
  1. Company Service
  2. Search Service
* **Communication:** Uses Feign clients for synchronous communication with dependent services.

**3. Job Application Service**

* **Description:** Manages job applications and interactions between users and job postings.
* **Dependencies:**
  1. Job Service
  2. Users Service
  3. Notification Service
8 **Communication:** Uses Feign clients for synchronous communication with dependent services.

**4. Search Service**

* **Description:** Provides search capabilities for job listings and companies.
* **Dependencies:**
  1. Company Service
  2. Job Service
* **Communication:** Uses Feign clients for synchronous communication with dependent services.

**5. Notification Service**

* **Description:** Sends notifications to users based on job application events and other triggers.
* **Dependencies:**
  1. Job Application Service
  2. Users Service
* **Communication:** Uses Kafka for asynchronous communication with dependent services.

**6. Reviews Service**

* **Description:** Manages company reviews and ratings.
* **Dependencies:**
  1. Company Service
* **Communication:** Uses Kafka for asynchronous communication with dependent services once the rating for a company's average rating get updated.

**7. Authentication Service**

* **Description:** Handles user authentication and authorization.
* **Functionality:** Uses JWT for secure token-based authentication and authorization..

## Supporting Services

**8. Config Server**

* **Description:** Centralized configuration management for all microservices.
* **Functionality:** Provides dynamic configuration properties to microservices at runtime from **Github.**

**9. Eureka Registry/Discovery Service**

* **Description:** Service discovery and registration.
* **Functionality:** Allows microservices to register themselves and discover other services dynamically.

**10. Zipkin**
* **Description:** Provides distributed tracing capabilities.
* **Functionality:** Helps trace requests across microservices to monitor and troubleshoot performance issues.

## Databases
* **PostgreSQL:** Used as the primary database for data persistence across all microservices.

### Asynchronous Communication
JobNest utilizes Kafka for asynchronous communication to ensure decoupled and resilient interactions between services. This is particularly useful for event-driven scenarios such as sending notifications and handling reviews:

1. Notification Service listens for events from Job Application Service and Users Service.
2. Reviews Service listens for events from the Company Service.

## Technology Stack
* Java Spring Boot: For developing microservices.
* Spring Cloud Netflix: For service discovery (Eureka) and circuit breaker patterns.
* Spring Cloud Config: For centralized configuration management.
* Spring Cloud Gateway: For API Gateway.
* Feign Client: For declarative REST client interactions between microservices.
* Kafka: For asynchronous communication.
* Zipkin: For distributed tracing.
* PostgreSQL: For data persistence.
* JWT: For secure token-based authentication and authorization.
* Docker: For containerization of services.

#### Getting Started
To get started with JobNest, follow these steps:

**1. Clone the repository:**

git clone https://github.com/mksharma9560/jobnest-backend-microservices.git
cd jobnest

**2. Build and Run:**
Use Maven or Gradle to build the project. Ensure Docker and Kafka are running for full functionality.

**3. mvn clean install**
docker-compose up

**Configuration:**
Ensure the Config Server is set up with the correct configuration properties for each service. Update application.properties files as needed.

**Service Discovery:**
Start the Eureka Server to enable service registration and discovery.

**Distributed Tracing:**
Ensure Zipkin is set up and running to enable distributed tracing.

## Contribution
Contributions are welcome! Please fork the repository and submit pull requests for any enhancements, bug fixes, or new features. Ensure code adheres to Java Code Conventions and is well-documented.

# BankBuddy POC

BankBuddy POC is a lightweight banking application built using **Spring Boot**, implementing the **Hexagonal Architecture**. The project is designed to deliver modular, maintainable, and extensible banking functionalities, ensuring a clean separation of concerns and ease of extension.

### Key Features & Additions:
1. **Spring Security with JWT**: The application has integrated **Spring Security** to manage user authentication and authorization. JWT (JSON Web Token) is used to securely authenticate users and issue tokens, which are required for accessing protected API endpoints. This ensures that sensitive operations like deposits and withdrawals can only be performed by authenticated users.

2. **Dockerized Application**: The entire application, is containerized using **Docker**. This allows for easy deployment and scalability.

3. **State Design Pattern for Transaction Management**: 
   - The application implements the **State Design Pattern** to manage the different states of a transaction (Pending, Valid, Rejected). This pattern is ideal because it allows for a clear and flexible structure in handling transaction states. Each state is represented as an object, which allows the transaction to change states without modifying the core transaction class.
   - Using the State Pattern makes the code more readable and eliminates conditional logic spread across the application. Instead of having numerous `if`/`else` checks scattered throughout, the behavior changes based on the current state, which is cleaner and easier to manage.

4. **Chain of Responsibility for Transaction Validations**: 
   - For transaction validation, the **Chain of Responsibility** pattern is employed. This pattern allows a series of validation checks to be applied in a sequential manner, where each handler (validator) is responsible for checking a specific aspect of the transaction.
   - This design decouples the validation logic from the core transaction process and ensures that each validation can be easily modified or extended without affecting others.

5. **CI/CD with GitHub Actions**
   - A **CI/CD pipeline** has been set up using **GitHub Actions**. The pipeline automatically triggers tests on every commit or pull request to ensure code quality and prevent issues from being introduced into the system.
     
This combination of **Spring Security with JWT**, **Dockerization**, **State Design Pattern**, and **Chain of Responsibility** for validations ensures that BankBuddy is secure, maintainable, and scalable, while also offering flexibility for future enhancements.


---

## Table of Contents

1. [Features](#features)
2. [Architecture Overview](#architecture-overview)
3. [Technologies Used](#technologies-used)
4. [Setup Instructions](#setup-instructions)
5. [API Documentation](#api-documentation)
6. [Testing](#testing)
7. [Dockerization](#dockerization)
8. [Future Enhancements](#future-enhancements)
9. [Migration data](#dummy-data).

---

## Features

- Manage clients and accounts.
- Perform transactions including deposits and withdrawals.
- Generate account ledger reports.
- Transaction validation using customizable validation chains.
- Error handling with detailed exception responses.

---

## Architecture Overview

### Hexagonal Architecture
This project adopts **Hexagonal Architecture** to promote a clean separation of concerns:

1. **Domain Layer**: Core business logic and models.
2. **Application Layer**: Encapsulates use cases and service logic.
3. **Adapter Layer**: Interfaces for APIs and persistence.
4. **Infrastructure Layer**: Framework-specific configurations and integrations.

### Key Design Principles
- **Ports and Adapters**: Decouples business logic from external systems.
- **Validation Chains**: Chain-of-responsibility pattern for transaction validation.
- **State Management**: Manages transaction states effectively.

---

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Hibernate/JPA**
- **H2 Database** (for development)
- **Maven** (build tool)
- **JUnit** (testing framework)
- **ArchUnit** (Validates architectural compliance)

---

## Setup Instructions

### Prerequisites
- Java 17+
- Maven 3.8+
- Docker

### Clone the Repository
```bash
git clone https://github.com/miguelmaroune/bankBuddy-mvp.git
cd bankBuddy-mvp
```
### Build the Project
To build the project using Maven, run the following command:
```bash
mvn clean package -DskipTests
```
This will clean any previous builds and package the project into a JAR file (target/bankBuddy-core-0.0.1-SNAPSHOT.jar
### Run the Application
To run the application locally, use the following command:

```bash
java -jar target/bankBuddy-core-0.0.1-SNAPSHOT.jar
```
### Run with Docker
To build and run the application using Docker:
#### 1- If you haven't already built the Docker image, you can use Docker Compose to build it by running the following command:
```bash
docker docker-compose up --build
```
This will:
 - Build the Docker image using the Dockerfile located in the docker/ directory.
 - Start the application (app service) and the PostgreSQL database (db service).
The bankbuddy-core:latest image will be tagged and used.
#### 2. Start the Containers with Docker Compose
```bash
docker-compose up
```
Access the application at http://localhost:8080

### API Documentation
You can access the interactive API documentation via Swagger UI by visiting the following URL:
- **Swagger**: http://localhost:8080/swagger-ui/index.html
  
Available Endpoints
## API Endpoints Documentation

| Endpoint                                              | Method | Description                                      | Example Payload                                   |
|-------------------------------------------------------|--------|-------------------------------------------------|--------------------------------------------------|
| `/auth/signup`                                        | POST   | Register a new user                             | `{ "username": "johndoe", "password": "password123", "email": "johndoe@example.com" }` |
| `/auth/login`                                         | POST   | Authenticate a user and generate JWT token      | `{ "username": "johndoe", "password": "password123" }` |
| `/api/clients`                                        | POST   | Create a new client                             | `{ "name": "John Doe" }`                         |
| `/api/clients/{clientId}`                              | GET    | Get client by ID                                | N/A                                              |
| `/api/accounts`                                       | POST   | Create a new account                            | `{ "clientId": 1, "type": "SAVINGS" }`           |
| `/api/accounts/{accountId}`                           | GET    | Get account by ID                               | N/A                                              |
| `/api/accounts/{accountId}/deposit`                   | POST   | Deposit funds into an account                   | `{ "amount": 500 }`                              |
| `/api/accounts/{accountId}/withdrawal`                | POST   | Withdraw funds from an account                  | `{ "amount": 200 }`                              |
| `/api/accounts/{accountId}/transactions`              | GET    | Get transaction ledger for an account           | `{ "type": "VALID", "from": "01/01/2023" }`      |
| `/api/accounts/{accountId}/transactions/report`       | GET    | Generate a PDF report of account transactions    | `{ "type": "PENDING", "from": "01/01/2023" }`    |

## Authentication with Postman
To interact with the secured endpoints in the API, you need to authenticate and obtain a JWT (JSON Web Token). Follow the steps below to authenticate using Postman and use the token for future requests:

### 1. **Get the JWT Token (Login)**

1. Open Postman and create a new `POST` request.
2. Set the URL to `http://localhost:8080/auth/login`.
3. In the **Body** tab, select **raw** and set the format to `JSON`. Then, enter the following JSON for the login request:

   ```json
   {
     "username": "johndoe",
     "password": "password123"
   }
  ``` 
For any API endpoint that requires authentication, you need to add the JWT token in the Authorization header.

### Testing
Run Unit Tests
You can execute the unit tests using:
```bash
mvn test -Dtest=**/*Test
```
### Test Coverage:
- **Service Layer**: Ensures the correctness of business logic.
- **Hexagonal Principles**: Validates architectural compliance.

# Dummy data:
- In order to quickly get started with some sample data for your project, you can use the provided init-data.sql file. Simply run the SQL script located at:
  src/main/resources/db/migration/init-data.sql.
### Steps to run the dummy data script:
- Ensure that your PostgreSQL database is up and running (e.g., using Docker or a local instance)
- Run the init-data.sql file in your database environment.
```bash
  psql -U your_user -d your_database -f src/main/resources/db/migration/init-data.sql
```

### Future Enhancements

- **Liquibase**: For Database Migrations: Implement Liquibase to manage and automate database schema changes
- **Resilience4j**: Add Resilience4j to handle retries and circuit breakers, ensuring fault tolerance and service stability.
- **Kafka Integration**: Integrate Kafka to publish account transaction operations to a specific topic, ensuring reliable delivery and enabling guaranteed processing of these transactions by downstream services.
- **Spring Batch**: Add a Spring Batch job configured to run periodically using a Cron expression to handle and recover stuck transactions.
- **End-to-End Tests**: Add end-to-end (e2e) tests to cover full system scenarios and ensure comprehensive validation.


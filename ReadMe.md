# BankBuddy MVP

BankBuddy MVP is a lightweight banking application built using **Spring Boot**, implementing the **Hexagonal Architecture**. The project is designed to deliver modular, maintainable, and extensible banking functionalities.

---

## Table of Contents

1. [Features](#features)
2. [Architecture Overview](#architecture-overview)
3. [Technologies Used](#technologies-used)
4. [Setup Instructions](#setup-instructions)
5. [Building and Running the Application](#building-and-running-the-application)
6. [API Documentation](#api-documentation)
7. [Testing](#testing)
8. [Dockerization](#dockerization)
9. [Future Enhancements](#future-enhancements)
10. [Contribution Guidelines](#contribution-guidelines)
11. [License](#license)

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
./mvnw clean package
```
### Run the Application
To run the application locally, use the following command:

```bash
java -jar target/bankBuddy-core-0.0.1-SNAPSHOT.jar
```


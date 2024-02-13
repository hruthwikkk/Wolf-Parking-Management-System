# College Parking System Service

This project implements a Java Spring Boot service designed to manage a college parking system. It offers functionalities for parking lot management, vehicle registration, permit issuance, and handling citations. The backend database is powered by MariaDB, ensuring efficient data storage and retrieval.

## Features

- User registration and management (Students, Employees, Visitors)
- Vehicle registration with unique identifiers
- Parking permit issuance and management
- Parking lot and zone management
- Citation generation and appeal handling
- Real-time availability status for parking spaces

## Requirements

- Java JDK 11 or higher
- Maven 3.6.0 or higher
- MariaDB 10.4 or higher
- Spring Boot 2.4.0 or higher

## Installation and Setup

1. **MariaDB Database Setup**
   - Install MariaDB and ensure it is running on your system.
   - Create a database named `college_parking_system`.
   - Import the provided SQL schema into your database.

2. **Application Configuration**
   - Navigate to `src/main/resources/` and open `application.properties`.
   - Update the database connection details to match your MariaDB setup.

3. **Building the Application**
   - Open a terminal in the project root directory.
   - Run `mvn clean install` to build the project.

4. **Running the Application**
   - After successful build, run `java -jar target/college-parking-system-0.0.1-SNAPSHOT.jar` to start the service.
   - The service will be available at `http://localhost:8080`.

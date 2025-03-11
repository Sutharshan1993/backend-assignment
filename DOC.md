# Application Run Instructions

This document provides instructions for running the application.

## Development Environment

* **Version Control:** Git
* **Build Tool:** Maven
* **Java Version:** 17
* **Database:** PostgreSQL
* **Database Migration Tool:** Liquibase

## Prerequisites

Before running the application, ensure you have the following installed:

* **Java Development Kit (JDK):** Version 17.
    * You can download the JDK from [Adoptium](https://adoptium.net/temurin/releases/).
    * Verify installation by running `java -version` and `javac -version` in your terminal.
* **Maven:**
    * You can download Maven from [Apache Maven](https://maven.apache.org/download.cgi).
    * Verify installation by running `mvn -v` in your terminal.
* **Git:**
    * You can download Git from [Git Downloads](https://git-scm.com/downloads).
    * Verify installation by running `git --version` in your terminal.
* **PostgreSQL:**
    * Install and configure PostgreSQL according to its documentation.
    * Create the necessary database and user for the application.
* **IDE (Optional):** [IntelliJ IDEA,STS].
    * An IDE is recommended for development and debugging but not strictly required for running the application.

## Configuration

1. **Database Configuration:**
    * Locate the application's configuration file (`application.yml` in `src/main/resources`).
        * Update the PostgreSQL connection properties with your database details:
            ```properties
            spring:
              datasource:
                  url: jdbc:postgresql://localhost:5432/bayzdelivery
                  username: db_user
                  password: 123qwe
                  driver-class-name: org.postgresql.Driver
              jpa:
                  open-in-view: false
                  show-sql: true
                  hibernate:
                    ddl-auto: update
              liquibase:
                change-log: classpath:/db/changelog/db.changelog-master.xml
                enabled: true
          ```

* Replace `[host]`, `[port]`, `[database-name]`, `[database-username]`, and `[database-password]` with your actual
  values.
* Ensure that the liquibase changelog path is correct.

2. **Environment Variables (Optional):**
    * Alternatively, you can configure the application using environment variables.
    * Set the appropriate environment variables (e.g., `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`,
      `SPRING_DATASOURCE_PASSWORD`).
    * This method is recommended for production environments.

## Running the Application

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/Sutharshan1993/backend-assignment.git
   cd backend-assignment
   ```

2. **Build the Application:**
   ```bash
   mvn clean install
   ```

3. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```

## Accessing the Application

Once the application is running, you can access it using your web browser or API client.

* **Web Browser:** Open your browser and navigate to `http://localhost:8081` .
* **API Client (e.g., Postman, cURL):** Send requests to the application's API endpoints.Locate the Postman Collections
  file (`BayzDelivery API Collection.postman_collection.json` in `src/main/resources`) and import in your Postman to
  test

## Stopping the Application

* **Terminal:** Press `Ctrl+C` in the terminal where the application is running.
* **IDE:** Stop the running application from within your IDE.

## Database Migrations (Liquibase)

* Liquibase will automatically run database migrations on application startup based on the changelog specified in the
  configuration.
* To create or modify database schema changes, add new changelog files to the `src/main/resources/db/changelog`
  directory and update the `db.changelog-master.xml` file.

## Troubleshooting

* **Database Connection Errors:** Verify your PostgreSQL configuration and ensure the database is running.
* **Port Conflicts:** If the application fails to start due to a port conflict, change the port in the configuration
  file or environment variables.
* **Dependency Errors:** If you encounter dependency errors during the build process, ensure you have the correct
  dependencies in your `pom.xml` file.
* **Liquibase Errors:** If Liquibase encounters errors, review the changelog files and database state. Check the
  application's logs for error details.
* **Logging:** Check the application's logs for error messages.
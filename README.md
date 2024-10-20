
# PermissionService

## Overview
**PermissionService** is a web service for managing user permissions, built using Kotlin, Spring Boot, PostgreSQL, and Docker.

## Prerequisites
- **Docker** and **Docker Compose**
- **Java 17** or higher
- **Gradle** 8.7 or higher

## Running the Application with Docker Compose

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/PermissionService.git
   cd PermissionService
   ```

2. **Build and Start the Services:**
   ```bash
   docker-compose up --build
   ```

3. **Access the Application:**
    - Application: [http://localhost:8081](http://localhost:8081)
    - PostgreSQL: port `5431`

## Running the Application Locally Without Docker

1. **Install Dependencies:**
    - Ensure **Java 17**, **Gradle**, and **PostgreSQL** are installed.
    - Set up PostgreSQL locally.

2. **Run the Application:**
   ```bash
   ./gradlew bootRun
   ```

3. **Access the Application:**
   Access at [http://localhost:8080](http://localhost:8080).

## Code Formatting with ktlint

1. **Check Formatting:**
   ```bash
   ./gradlew ktlintCheck
   ```

2. **Fix Formatting:**
   ```bash
   ./gradlew ktlintFormat
   ```

## Testing

Run tests to ensure correctness:

```bash
./gradlew test
```

## Database Configuration

- **Database Name:** `permission_db`
- **Username:** `postgres`
- **Password:** `postgres`

## Docker Compose Configuration

```yaml
services:
  api:
    container_name: "permission-service-api"
    build:
      context: .
    ports:
      - "8081:8080"
    environment:
      POSTGRES_USER: "postgres"
      POSTGRES_PASSWORD: "postgres"
      POSTGRES_DB: "permission_db"
      DB_HOST: "db"
      DB_PORT: 5432
    networks:
      - permission-network
    depends_on:
      - db

  db:
    container_name: "permission_db"
    image: postgres:alpine
    ports:
      - "5431:5432"
    environment:
      POSTGRES_USER: "postgres"
      POSTGRES_PASSWORD: "postgres"
      POSTGRES_DB: "permission_db"
    volumes:
      - permission_db:/var/lib/postgresql/data
    networks:
      - permission-network

volumes:
  permission_db:

networks:
  permission-network:
    external: true
```

## Contribution and License

Feel free to contribute via pull requests or issues. This project is licensed under the MIT License.

## Tech Stack
- Java 21
- Spring Boot 4.0.6
- Maven
- WebFlux & R2DBC
- PostgreSQL 16
- Docker

## Environment Variables

Copy the example file and fill in your values
```bash
cp .env.example .env
```

| Variable | Description | Example |
|---|---|---|
| `SECRET_CONFIG_DB_USERNAME` | Database user | `admin` |
| `SECRET_CONFIG_DB_PASSWORD` | Database password | `admin123` |
| `SECRET_CONFIG_DB_URL` | R2DBC connection URL | `r2dbc:postgresql://db:5432/dbname` |
| `CONFIG_HTTP_HTTPS_PORT` | HTTP server port | `8081` |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka broker address | `kafka:9092` |
| `KAFKA_TOPIC_CUSTOMER_EVENTS` | Topic for customer events | `customer.events` |
| `KAFKA_TOPIC_ACCOUNT_EVENTS` | Topic for account events | `account.events` |

## Running Tests
```bash
mvn test
```

## API documentation

With the app running on port **8081**.

| Resource | URL |
| --- | --- |
| Swagger UI | [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) |
| OpenAPI (JSON) | [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs) |

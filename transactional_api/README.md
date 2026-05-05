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
| `SECRET_CONFIG_DB_URL` | R2DBC connection URL | `r2dbc:postgresql://db:5432/transactions_db` |

## Running Tests
```bash
mvn test
```

## API documentation

With the app running on port **8080**.

| Resource | URL |
| --- | --- |
| Swagger UI | [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) |
| OpenAPI (JSON) | [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs) |

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
| `CONFIG_HTTP_HTTPS_PORT` | HTTP server port | `8080` |
| `TRANSACTIONAL_API_BASE_URL` | Transactional API base URL | `http://localhost:8081` |
| `TRANSACTIONAL_API_EXISTS_ACTIVE_ACCOUNTS_PATH` | Path to check active accounts by customer id | `/api/v1/accounts/exists?customerId={customerId}` |

## Running Tests
```bash
mvn test
```

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/customers/{identification}` | Get customer by identification |
| `GET` | `/api/v1/customers/{id}` | Get customer identification and full name by id |
| `POST` | `/api/v1/customers` | Create customer |
| `PUT` | `/api/v1/customers/{id}` | Update customer |
| `DELETE` | `/api/v1/customers/{id}` | Delete customer |


## API documentation

With the app running on port **8080**.

| Resource | URL |
| --- | --- |
| Swagger UI | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) |
| OpenAPI (JSON) | [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) |
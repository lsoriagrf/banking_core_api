## Prerequisites
- Docker & Docker Compose

## Services

| Service | Port | Description |
|---|---|---|
| `customersapi` | `8080` | Customer management |
| `lsg-msa-account` | `8081` | Accounts & movements |
| `postgres` | `5432` | PostgreSQL database |


## Environment Variables

Copy the example file and fill in your values
```bash
cp .env.example .env
```

| Variable | Description | Example |
|---|---|---|
| `DB_USERNAME` | Database user | `your_db_user` |
| `DB_PASSWORD` | Database password | `your_db_password` |
| `CUSTOMERS_DB_URL` | Customers R2DBC URL | `r2dbc:postgresql://db:5432/customers_db` |
| `ACCOUNT_DB_URL` | Transactions R2DBC URL | `r2dbc:postgresql://db:5432/transactions_db` |
| `ACCOUNT_API_BASE_URL` | Base URL for the account API | `http://lsg-msa-account:8081` |
| `ACCOUNT_API_EXISTS_ACTIVE_ACCOUNTS_PATH` | Path to check if a customer has active accounts | `/api/v1/accounts/exists?customerId={customerId}` |
| `CUSTOMERS_API_BASE_URL` | Base URL for the customers API | `http://customers-api:8080` |
| `CUSTOMERS_API_PATH` | Base path for customers endpoints | `/api/v1/customers` |


## Docker Compose

From the project root, ensure your `.env` matches `.env.example`

**1. Build images**

```bash
docker compose build
```

**2. Start containers**

```bash
docker compose up -d
```

**3. Verify containers are running**

```bash
docker compose ps
```

**4. Follow app logs if something fails**

```bash
docker compose logs -f app
```

**Stop and remove containers**

```bash
docker compose down
```

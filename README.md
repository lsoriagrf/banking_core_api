## Prerequisites
- Docker & Docker Compose

## Services

| Service | Port | Description |
|---|---|---|
| `customers_api` | `8080` | Customer management |
| `transactional_api` | `8081` | Accounts & movements |
| `postgres` | `5432` | PostgreSQL database |


## Environment Variables

Copy the example file and fill in your values
```bash
cp .env.example .env
```

| Variable | Description | Example |
|---|---|---|
| `DB_USERNAME` | Database user | `admin` |
| `DB_PASSWORD` | Database password | `admin123` |
| `CUSTOMERS_DB_URL` | Customers R2DBC URL | `r2dbc:postgresql://db:5432/customers_db` |
| `TRANSACTIONAL_DB_URL` | Transactions R2DBC URL | `r2dbc:postgresql://db:5432/transactions_db` |


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

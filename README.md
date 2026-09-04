## Prerequisites
- Docker & Docker Compose

## Services

| Service | Port | Description |
|---|---|---|
| `lsg-msa-customer` | `8080` | Customer management |
| `lsg-msa-account` | `8081` | Accounts & movements |
| `postgres` | `5432` | PostgreSQL database |
| `kafka` | `9092` | Asynchronous messaging |
| `kafka-ui` | `8088` | Kafka topics UI |


## Environment Variables

Copy the example file and fill in your values
```bash
cp .env.example .env
```

| Variable | Description | Example |
|---|---|---|
| `DB_USERNAME` | Database user | `your_db_user` |
| `DB_PASSWORD` | Database password | `your_db_password` |
| `CUSTOMER_DB_URL` | Customers R2DBC URL | `r2dbc:postgresql://db:5432/customers_db` |
| `ACCOUNT_DB_URL` | Transactions R2DBC URL | `r2dbc:postgresql://db:5432/transactions_db` |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka broker address | `kafka:9092` |
| `KAFKA_TOPIC_CUSTOMER_EVENTS` | Topic for customer events | `customer.events` |
| `KAFKA_TOPIC_ACCOUNT_EVENTS` | Topic for account events | `account.events` |


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

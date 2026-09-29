# Platform Shared PostgreSQL

Shared PostgreSQL 16 instance for the Piseth Java School platform. Each service uses its own database inside the same PostgreSQL server.

## 1. Create the shared Docker network

The Compose file uses `pisethjavaschool-dev-net` as an external network.

```bash
docker network inspect pisethjavaschool-dev-net >/dev/null 2>&1 \
  || docker network create pisethjavaschool-dev-net
```

## 2. Start PostgreSQL

From the project root:

```bash
docker compose up -d
```

PostgreSQL is exposed locally on:

```text
localhost:5436
```

## 3. Create or re-check databases

Database initialization runs automatically when PostgreSQL creates a new data volume.

You can also safely run it again manually:

```bash
./postgres/init/01-create-databases.sh
```

Existing databases are skipped.

## 4. Verify

```bash
./verify.sh
```

## Useful commands

```bash
# View containers
docker compose ps

# View PostgreSQL logs
docker compose logs -f postgres

# Stop containers
docker compose down
```

> `docker compose down` keeps the PostgreSQL data volume. Do not use `-v` unless you intentionally want to delete all database data.

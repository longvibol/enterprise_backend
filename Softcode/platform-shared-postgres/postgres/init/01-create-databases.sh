#!/bin/bash
set -Eeuo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd "${SCRIPT_DIR}/../.." && pwd)"
ENV_FILE="${PROJECT_DIR}/.env"
CONTAINER_NAME="${POSTGRES_CONTAINER_NAME:-ps-postgres}"

# When executed manually from the host, load the project's .env file.
# During PostgreSQL container initialization, Compose already provides POSTGRES_USER.
if [[ -f "${ENV_FILE}" ]]; then
  set -a
  # shellcheck disable=SC1090
  source "${ENV_FILE}"
  set +a
fi

DB_USER="${POSTGRES_USER:-${POSTGRES_USERNAME:-}}"

if [[ -z "${DB_USER}" ]]; then
  echo "ERROR: POSTGRES_USER or POSTGRES_USERNAME must be set."
  exit 1
fi

databases=(
  "${USER_DB_NAME:-platform_user}"
  "${ACCESS_CONTROL_DB_NAME:-platform_access_control}"
  "${PROPERTY_OWNER_DB_NAME:-platform_property_owner}"
  "${PROPERTY_DB_NAME:-platform_property}"
  "${SUBSCRIPTION_DB_NAME:-platform_subscription}"
  "${MODERATION_DB_NAME:-platform_moderation}"
)

run_psql() {
  if [[ -f /.dockerenv ]] && command -v psql >/dev/null 2>&1; then
    psql -v ON_ERROR_STOP=1 --username "${DB_USER}" --dbname postgres "$@"
    return
  fi

  if command -v docker >/dev/null 2>&1 \
      && docker inspect "${CONTAINER_NAME}" >/dev/null 2>&1; then
    docker exec -i "${CONTAINER_NAME}" \
      psql -v ON_ERROR_STOP=1 --username "${DB_USER}" --dbname postgres "$@"
    return
  fi

  echo "ERROR: PostgreSQL container '${CONTAINER_NAME}' is not running."
  echo "Start it first with: docker compose up -d"
  exit 1
}

echo "Creating Platform databases..."

for database in "${databases[@]}"; do
  exists="$(run_psql --tuples-only --no-align \
    --command "SELECT 1 FROM pg_database WHERE datname = '${database}';")"

  if [[ "${exists}" == "1" ]]; then
    echo "Database already exists: ${database}"
  else
    echo "Creating database: ${database}"
    run_psql --command "CREATE DATABASE \"${database}\" OWNER \"${DB_USER}\";"
  fi
done

echo "Platform databases are ready."

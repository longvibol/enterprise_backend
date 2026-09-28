#!/bin/bash
set -Eeuo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ENV_FILE="${SCRIPT_DIR}/.env"
CONTAINER_NAME="${POSTGRES_CONTAINER_NAME:-ps-postgres}"

if [[ -f "${ENV_FILE}" ]]; then
  set -a
  # shellcheck disable=SC1090
  source "${ENV_FILE}"
  set +a
fi

DB_USER="${POSTGRES_USERNAME:-${POSTGRES_USER:-}}"

if [[ -z "${DB_USER}" ]]; then
  echo "ERROR: POSTGRES_USERNAME or POSTGRES_USER must be set."
  exit 1
fi

echo "Container health:"
docker inspect --format='{{.State.Health.Status}}' "${CONTAINER_NAME}"

echo
echo "Databases:"
docker exec "${CONTAINER_NAME}" psql -U "${DB_USER}" -d postgres -c \
"SELECT datname FROM pg_database WHERE datistemplate = false ORDER BY datname;"

echo
echo "Connections:"
docker exec "${CONTAINER_NAME}" psql -U "${DB_USER}" -d postgres -c \
"SELECT datname, usename, application_name, client_addr, state, count(*) AS connections
 FROM pg_stat_activity
 WHERE datname IS NOT NULL
 GROUP BY datname, usename, application_name, client_addr, state
 ORDER BY datname, connections DESC;"

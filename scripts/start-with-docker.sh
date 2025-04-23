#!/bin/bash

set -e

echo "Loading environment..."
set -a
source ../config/.env.docker
set +a

docker compose up -d --build

echo "Waiting for PostgreSQL to be ready..."

until docker exec -it db psql -U postgres -d spendeeze -c "
  SELECT 1 FROM information_schema.tables
  WHERE table_name IN ('expense', 'income', 'member', 'member_roles', 'transaction_category');
" | grep -q 1; do
  echo "Tables not ready yet, waiting..."
  sleep 5
done

echo "Tables are ready. Executing dummy data script..."

docker exec -i db psql -U postgres -d spendeeze < ../backend/db_init/dummyDataGenerator.sql

echo "Dummy data inserted successfully."

echo "Visit http://localhost:3000 in your browser."

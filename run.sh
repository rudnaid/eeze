#!/bin/bash

# Make sure the script exits if any command fails
set -e

# Step 1: Start the containers in detached mode
docker compose up -d --build

# Step 2: Wait for the backend to finish initializing the database
echo "Waiting for PostgreSQL to be ready..."
until docker exec -it db psql -U postgres -d spendeeze -c "
  SELECT 1 FROM information_schema.tables
  WHERE table_name IN ('expense', 'income', 'member', 'member_roles', 'transaction_category');
" | grep -q 1; do
  echo "Tables not yet ready, waiting..."
  sleep 5
done

echo "✅ Tables are ready. Executing dummy data script..."

# Step 3: Run the SQL script to insert dummy data
docker exec -i db psql -U postgres -d spendeeze < ./backend/db_init/dummyDataGenerator.sql

echo "✅ Dummy data inserted successfully."

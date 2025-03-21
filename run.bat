@echo off
setlocal enabledelayedexpansion
set POSTGRES_USER=postgres
set DATABASE=spendeeze

docker-compose up -d --build

echo Waiting for PostgreSQL to be ready...
:waitloop
docker exec -i db psql -U postgres -d spendeeze -c "SELECT 1 FROM information_schema.tables WHERE table_name IN ('expense', 'income', 'member', 'member_roles', 'transaction_category');" >nul 2>nul

if errorlevel 1 (
  echo Tables ready yet, waiting...
  timeout /t 5 >nul
  goto waitloop
)

echo Tables are ready. Executing dummy data script...
docker exec -i db psql -U postgres -d spendeeze < ./backend/db_init/dummyDataGenerator.sql

echo Dummy data inserted successfully.

echo Visit http://localhost:3000 in your browser.

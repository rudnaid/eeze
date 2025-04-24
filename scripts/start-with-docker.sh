#!/bin/bash

set -e

ENV_FILE="../config/.env.docker"
mkdir -p ../config
touch "$ENV_FILE"

if [[ -f "$ENV_FILE" ]]; then
  source "$ENV_FILE"
fi

prompt_with_default() {
  local varname=$1
  local current=${!varname}
  local default=$2
  read -p "$varname [default: ${current:-$default}]: " input
  export "$varname"="${input:-${current:-$default}}"
}

echo ""
echo "Configure environment variables for Docker setup."
echo ""
echo "Entering custom values will override the default .env.docker file."
echo ""
echo "If you have an existing Docker volume for this project with custom PostgreSQL credentials, please enter those values when prompted."
echo ""
echo "If the default values shown in [brackets] do not match your existing setup, enter the values you set up previously when prompted."
echo ""
echo "Otherwise, press Enter to accept the default value shown in [brackets]."
echo ""

prompt_with_default SPRING_DATASOURCE_URL "jdbc:postgresql://eeze_db:5432/eeze"
prompt_with_default SPRING_DATASOURCE_USERNAME "postgres"
prompt_with_default SPRING_DATASOURCE_PASSWORD "admin1234"

prompt_with_default POSTGRES_USER "postgres"
prompt_with_default POSTGRES_PASSWORD "admin1234"
prompt_with_default POSTGRES_DB "eeze"

prompt_with_default JWT_SECRET "0c4872abdb41610145e2c6819fc7420b589e37b624dc57eb7c1f212b14017d28"
prompt_with_default JWT_EXPIRATION_MS "86400000"

cat > "$ENV_FILE" <<EOF
SPRING_DATASOURCE_URL=$SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME=$SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD=$SPRING_DATASOURCE_PASSWORD
POSTGRES_USER=$POSTGRES_USER
POSTGRES_PASSWORD=$POSTGRES_PASSWORD
POSTGRES_DB=$POSTGRES_DB
JWT_SECRET=$JWT_SECRET
JWT_EXPIRATION_MS=$JWT_EXPIRATION_MS
EOF

echo "Environment saved to $ENV_FILE"
echo ""
echo "Starting Docker containers..."

set -a
source "$ENV_FILE"
set +a

docker compose up -d --build

echo ""
echo "Waiting for PostgreSQL to be ready..."

until docker exec -it eeze_db psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "
  SELECT 1 FROM information_schema.tables
  WHERE table_name IN ('expense', 'income', 'member', 'member_roles', 'transaction_category');
" | grep -q 1; do
  echo "Tables not ready yet, waiting..."
  sleep 5
done

echo "Tables are ready. Executing dummy data script..."

docker exec -i eeze_db psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" < ../backend/db_init/dummyDataGenerator.sql

echo "Dummy data inserted successfully."
echo ""
echo "Visit http://localhost:3000 in your browser."
echo ""
echo "To stop running the containers enter: docker compose down, to also remove associated volumes enter: docker compose down -v"

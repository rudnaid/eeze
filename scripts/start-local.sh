#!/bin/bash

trap 'echo "Exiting..."; pkill -f "spring-boot:run"; kill $EEZE_FRONTEND_PID 2>/dev/null; exit' INT

ENV_FILE="../config/.env.local"
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
echo "Configure environment variables for your setup."
echo ""
echo "Entering custom values will override the default .env.local file."
echo ""
echo "If you already have PostgreSQL installed with your own user and password, enter those values when prompted."
echo ""
echo "Otherwise, press Enter to accept the default value shown in [brackets]."
echo ""

prompt_with_default SPRING_DATASOURCE_URL "jdbc:postgresql://localhost:5432/eeze"
prompt_with_default SPRING_DATASOURCE_USERNAME "postgres"
prompt_with_default SPRING_DATASOURCE_PASSWORD "admin1234"

prompt_with_default POSTGRES_USER "postgres"
prompt_with_default POSTGRES_PASSWORD "admin1234"

prompt_with_default JWT_SECRET "0c4872abdb41610145e2c6819fc7420b589e37b624dc57eb7c1f212b14017d28"
prompt_with_default JWT_EXPIRATION_MS "86400000"

cat > "$ENV_FILE" <<EOF
SPRING_DATASOURCE_URL=$SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME=$SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD=$SPRING_DATASOURCE_PASSWORD
POSTGRES_USER=$POSTGRES_USER
POSTGRES_PASSWORD=$POSTGRES_PASSWORD
JWT_SECRET=$JWT_SECRET
JWT_EXPIRATION_MS=$JWT_EXPIRATION_MS
EOF

echo "Environment saved to $ENV_FILE"

echo "Loading environment..."
set -a
source "$ENV_FILE"
set +a

echo "Starting backend..."
(cd ../backend && ./mvnw spring-boot:run) &
EEZE_BACKEND_PID=$!

echo "Starting frontend..."
(cd ../frontend && npm run dev) &
EEZE_FRONTEND_PID=$!

wait $EEZE_BACKEND_PID $EEZE_FRONTEND_PID

echo "Press Ctrl + C to exit"

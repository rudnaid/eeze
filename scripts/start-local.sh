#!/bin/bash

echo "Loading environment..."
set -a
source ../config/.env.local
set +a

echo "Starting backend..."
(cd ../backend && ./mvnw spring-boot:run) &

echo "Starting frontend..."
(cd ../frontend && npm run dev) &

wait

#!/bin/bash

services=(
  "discovery-service"
  "gateway-service"
  "user-service"
  "movie-service"
)

wait_time=5 # (in seconds)

start_service() {
  service_dir=$1
  echo "Starting ${service_dir}..."

  cd ${service_dir}

  # Start the service
  nohup java -jar target/*.jar > /dev/null 2>&1 &

  cd ..

  echo "Waiting for ${wait_time} seconds before starting the next service..."
  sleep ${wait_time}
}

for service in "${services[@]}"
do
  start_service ${service}
done

echo "All services have been started."
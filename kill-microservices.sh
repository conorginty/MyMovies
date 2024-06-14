#!/bin/bash

jar_files=(
  "discovery-service-0.0.1-SNAPSHOT.jar"
  "gateway-service-0.0.1-SNAPSHOT.jar"
  "user-service-0.0.1-SNAPSHOT.jar"
  "movie-service-0.0.1-SNAPSHOT.jar"
)

kill_processes() {
  jar_name=$1
  echo "Killing processes for ${jar_name}..."

  # Find process IDs (PIDs) for Java processes running the specified JAR file
  pids=$(ps aux | grep ${jar_name} | grep -v grep | awk '{print $2}')

  for pid in ${pids}
  do
    echo "Killing PID: ${pid}"
    kill ${pid}
  done

  echo "Processes for ${jar_name} killed."
}

for jar_file in "${jar_files[@]}"
do
  kill_processes ${jar_file}
done

echo "All specified processes have been killed."
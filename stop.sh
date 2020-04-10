#!/bin/bash
set -o allexport
source config
docker-compose -p ${PROJECT_NAME} down

#!/usr/bin/env bash

docker-compose -p demo -f buildscripts/docker-compose.yml rm -f
docker-compose -p demo -f buildscripts/docker-compose.yml pull

docker-compose -p demo -f buildscripts/docker-compose.yml down --rmi local -v --remove-orphans
docker-compose -p demo -f buildscripts/docker-compose.yml up --build --abort-on-container-exit
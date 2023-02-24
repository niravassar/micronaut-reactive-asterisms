#!/bin/bash
function buildProject () {
    echo assembling...
    ./gradlew assemble
}

function composeDown() {
  echo decomposing...
  docker-compose down
}

function composeUp() {
    echo composing...
    docker-compose up --build --force-recreate -d
}

function __run__() {
    composeDown
    buildProject
    composeUp
}

__run__

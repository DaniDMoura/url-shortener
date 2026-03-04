#!/usr/bin/env bash
set -euo pipefail

docker build -t "$DOCKER_CREDS_USR/encurtadorurl:latest" .

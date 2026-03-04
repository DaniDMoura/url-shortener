#!/usr/bin/env bash
set -euo pipefail

IMAGE="$DOCKER_CREDS_USR/encurtadorurl:latest"

printf '%s' "$DOCKER_CREDS_PSW" | docker login \
                        --username "$DOCKER_CREDS_USR" \
                        --password-stdin
docker push "$IMAGE"



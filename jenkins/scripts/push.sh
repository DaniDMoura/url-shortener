#!/usr/bin/env bash
set -euo pipefail

USERNAME=$1
PASSWORD=$2

IMAGE="$USERNAME/encurtadorurl:latest"

printf '%s' "$PASSWORD" | docker login \
                        --username "$USERNAME" \
                        --password-stdin
docker push "$IMAGE"



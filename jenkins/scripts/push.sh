#!/usr/bin/env bash
set -euo pipefail

USERNAME=$1
PASSWORD=$2

IMAGE="$USERNAME/encurtadorurl:latest"

docker login --username "$USERNAME" --password "$PASSWORD"
docker push "$IMAGE"



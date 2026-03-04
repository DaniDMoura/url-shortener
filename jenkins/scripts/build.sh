#!/usr/bin/env bash
set -euo pipefail

USERNAME=$1

docker build -t "$USERNAME/encurtadorurl:latest" -f ../Dockerfile ..

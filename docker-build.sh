# Copyright (C) 2023 Firstpass
# 
# This script will use Docker to build the following versions of Firstpass:
# 1. Windows NSIS Installer
# 2. Windows ZIP
# 3. Linux AppImage
# 4. Linux tar.gz
#
# You can choose to build only the frontend or only the backend by passing
# the argument "frontend" or "backend" respectively.
#
# Usage: ./docker-build.sh [frontend|backend]


if ! [ "$1" = "frontend" ];
then
    echo
    echo Running Maven backend build...
    echo
    docker run -v $(pwd)/backend:/app -w /app maven:3.8.7-openjdk-18 mvn clean package
fi

if ! [ "$1" = "backend" ];
then
    echo
    echo Running electron-builder frontend build...
    echo
    docker run -v $(pwd):/app -w /app/frontend electronuserland/builder:wine /bin/bash -c "yarn install; yarn build; yarn dist:win-lin"
fi
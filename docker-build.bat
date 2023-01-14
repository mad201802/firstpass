@REM Copyright (C) 2023 Firstpass
@REM 
@REM This script will use Docker to build the following versions of Firstpass:
@REM 1. Windows NSIS Installer
@REM 2. Windows ZIP
@REM 3. Linux AppImage
@REM 4. Linux tar.gz
@REM
@REM You can choose to build only the frontend or only the backend by passing
@REM the argument "frontend" or "backend" respectively.
@REM
@REM Usage: ./docker-build.bat [frontend|backend]

@echo off

if not "%1"=="frontend" (
    @echo.
    @echo Running Maven backend build...
    @echo.
    docker run -v %CD%\backend:/app -w /app maven:3.8.7-openjdk-18 mvn clean package
)

if not "%1"=="backend" (
    @echo.
    @echo Running electron-builder frontend build...
    @echo.
    docker run -v %CD%:/app -w /app/frontend electronuserland/builder:wine /bin/bash -c "yarn install; yarn build; yarn dist --linux AppImage --linux tar.gz --win nsis --win zip"
)
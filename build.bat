@REM Copyright (C) 2023 Firstpass
@REM 
@REM This script will build the backend & frontend and
@REM package it into an installer for Windows.
@REM 
@REM Make sure Maven & Yarn are in your PATH.

@echo off

@echo.
@echo Building backend...
@echo.
cd backend
call mvn package
cd ..

@echo.
@echo Building frontend...
@echo.
cd frontend
call yarn install
call yarn build

@echo.
@echo Building installer...
@echo.
call yarn dist
cd ..

@echo.
@echo.
@echo Build finished successfully!


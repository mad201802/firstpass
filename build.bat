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
if %errorlevel% neq 0 goto :error
cd ..

@echo.
@echo Building frontend...
@echo.
cd frontend
call yarn install
if %errorlevel% neq 0 goto :error
call yarn build
if %errorlevel% neq 0 goto :error

@echo.
@echo Building installer...
@echo.
call yarn dist %*
if %errorlevel% neq 0 goto :error
cd ..

@echo.
@echo.
@echo Build finished successfully!

goto :eof

:error
@echo.
@echo.
@echo Build failed!

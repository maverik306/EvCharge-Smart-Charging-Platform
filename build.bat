@echo off
cd /d "c:\Users\User\eclipse-workspace\EvChargingApp"
set "CP=bin;lib/jbcrypt-0.4.jar;C:/Users/User/Downloads/mysql-connector-j-26.10.0/mysql-connector-j-26.10.0/mysql-connector-j-26.10.0.jar"
dir /s /b src\*.java > sources.txt
findstr /v /i "MainApp.java" sources.txt > sources_clean.txt
javac -encoding UTF-8 -d bin -cp "%CP%" @sources_clean.txt
if %ERRORLEVEL% equ 0 (
    copy /y src\db.properties bin\db.properties >nul
    echo BUILD_SUCCESS
) else (
    echo BUILD_FAILED %ERRORLEVEL%
)
del sources.txt sources_clean.txt 2>nul

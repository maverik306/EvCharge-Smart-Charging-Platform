@echo off
cd /d "c:\Users\User\eclipse-workspace\EvChargingApp"
set "CP=bin;lib/jbcrypt-0.4.jar;C:/Users/User/Downloads/mysql-connector-j-26.10.0/mysql-connector-j-26.10.0/mysql-connector-j-26.10.0.jar"
java -cp "%CP%" com.evcharge.app.AppTestSuite
java -cp "%CP%" com.evcharge.app.AuthScenariosTest

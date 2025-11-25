@echo off
REM CV Builder - Run Script for Java 17

echo ========================================
echo    CV Builder Application
echo ========================================
echo.

REM Set Java 17
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.16.8-hotspot\"
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Set Maven
set "M2_HOME=%USERPROFILE%\apache-maven\apache-maven-3.9.9"
set "PATH=%M2_HOME%\bin;%PATH%"

echo Verifying Java...
java -version
echo.

echo Verifying Maven...
mvn -version
echo.

echo ========================================
echo    Running CV Builder
echo ========================================
echo.

mvn javafx:run

pause

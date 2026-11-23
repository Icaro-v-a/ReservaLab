@echo off
setlocal
cd /d "%~dp0"
where java >nul 2>&1
if errorlevel 1 (
  echo ERRO: Java 17 ou superior nao encontrado. Instale o JDK e configure o PATH.
  pause
  exit /b 1
)
java "ferramentas\PrepararDependencias.java"
if errorlevel 1 (
  pause
  exit /b 1
)

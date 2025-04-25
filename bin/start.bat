@echo off

cd /d %~dp0

FOR /F %%i IN ('cmd /C "set KTOR_LOG_LEVEL=ERROR && eac-tk"') DO set TOKEN=%%i
echo %TOKEN%

"C:\Games\SOUND VOLTEX EXCEED GEAR\launcher\modules\errorreporter.exe" -t %TOKEN%
@echo off
cd "C:\Users\austi\OneDrive\Desktop\Java Projects"
for /f "tokens=*" %%i in ('dir /a:d-h-s /b') do (call :listDirectory "%%i")
for /f "tokens=*" %%A in ('java.exe -jar "C:\Users\austi\OneDrive\Desktop\Batch-File-Projects\Test InOut Batch.jar" %directory%') do set message=%%A
echo %message%
pause

:listDirectory
if defined directory (set "directory=%directory%...%1") else (set "directory=%1")
exit /b
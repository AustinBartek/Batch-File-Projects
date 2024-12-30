@echo off

::updating the number of logins

set /p numLogins=<numLogins.txt
set /a numLogins+=1
echo %numLogins% > numLogins.txt

::setting current project file to file path

set /p projectFile=<project.txt

title Welcome Menu

echo ================
echo [WELCOME AUSTIN]
echo ================
echo:
echo Today is %DATE%
echo It is %TIME%
echo Number of Visits: %numLogins%
echo:

set keep=y
goto:doMoreActions

:doMoreActions
if "%keep%" NEQ "y" goto eof

::getting user decision
set /p "input={0: WSL Terminal; 1: Current Java Project; 2: Set New Java Project} :: " || set input=Nothing Chosen
set input=%input:&=%
set input=%input:"=%

if "%input%"=="0" goto wsl
if "%input%"=="1" goto project
if "%input%"=="2" goto newProject
exit /b

:wsl
cd C:\Users\Austin\Desktop
start wsl.exe
exit

:project
start "" "C:\Users\austi\AppData\Local\Programs\Microsoft VS Code\Code.exe" "C:\Users\austi\OneDrive\Desktop\Java Projects\%projectFile%"
exit

:newProject
cd "C:/Users/austi/OneDrive/Desktop/Java Projects"
cls

::lists out all files with numbers in order to allow setting of new project file
set /a count=0
for /f "tokens=*" %%G in ('dir /a:d-h /b') do (
call :incAndEchoCount "%%G"
)

set /a count=0
set /p fileNum="Enter Number of File to set as Current Project File :: " || set fileNum=No Choice
for /f "tokens=*" %%G in ('dir /a:d-h /b') do (
call :incAndCheckCount "%%G"
)

if defined newFile (goto setProject) else (exit /b)



:incAndCheckCount
if %count%==%fileNum% set newFile=%1
set /a count+=1
exit /b

:incAndEchoCount
echo %count% : %1
set /a count+=1
exit /b

:setProject
set newFile=%newFile:&=%
set newFile=%newFile:"=%
echo %newFile% > "C:\Users\austi\OneDrive\Desktop\Batch-File-Projects\Welcome Menu\project.txt"
set projectFile=%newFile%
echo Set Current Project as %newFile%
goto:askToContinue

:askToContinue
cls
set /p "keep=Continue? (y | n) :: " || keep=n
goto:doMoreActions
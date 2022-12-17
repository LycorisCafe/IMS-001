@Echo off
title IMS - Updater
color 0A
set tempPath=C:\ProgramData\LycorisCafe\IMS\Temp
goto INSTALL

:INSTALL
set/p path=<"%tempPath%\appPath.lc"
del /q "%temp%\getadmin.vbs"
rmdir /s /q "%path%"
mkdir "%path%"

move "%tempPath%\*.rar" "%path%\"
move "%tempPath%\unrar.exe" "%path%\"

"%path%\unrar.exe" x -o+ "%path%\1.rar"

echo %date% @ %time% >"%tempPath%\newVersion.lc"
hostname >>"%tempPath%\newVersion.lc"

del /q "%tempPath%\admin.vbs"
del /q "%path%\*.rar"
del /q "%path%\unrar.exe"
del /q "%tempPath%\appPath.lc"
del /q "%tempPath%\invisible.vbs"
del /q "%tempPath%\updater.bat"
exit
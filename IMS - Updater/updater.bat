@Echo off
set tempPath=C:\ProgramData\LycorisCafe\IMS\Temp
set nowPath="C:\ProgramData\Microsoft\Windows\Start Menu\Programs\StartUp"
if exist "%tempPath%\invisible.vbs" goto INSTALL
if not exist "%tempPath%\invisible.vbs" goto INVISIBLE

:INVISIBLE
echo CreateObject("Wscript.Shell").Run """" & WScript.Arguments(0) & """", 0, False >"%tempPath%\invisible.vbs"
"C:\Windows\System32\wscript.exe" "%tempPath%\invisible.vbs" "%nowPath%\updater.bat"
exit

:INSTALL
set/p path=<"%tempPath%\appPath.lc"
rmdir /s /q "%path%"
mkdir "%path%"

move "%tempPath%\*.rar" "%path%\"
move "%tempPath%\unrar.exe" "%path%\"

"%path%\unrar.exe" x -o+ "%path%\1.rar"

echo %date% @ %time% >"%tempPath%\newVersion.lc"
hostname >>"%tempPath%\newVersion.lc"

del /q "%path%\*.rar"
del /q "%path%\unrar.exe"
del /q "%tempPath%\appPath.lc"
del /q "%tempPath%\invisible.vbs"
del /q "%nowPath%\updater.bat"
exit
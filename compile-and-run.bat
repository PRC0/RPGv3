@echo off
REM Script de compilación para RPGv3
REM Compila todos los archivos Java del proyecto

echo ======================================
echo RPGv3 - Script de Compilacion
echo ======================================
echo.

REM Limpiar directorio bin
echo [1/3] Limpiando archivos compilados anteriores...
if exist bin\rpg (
    rmdir /s /q bin\rpg
    echo Limpieza completada.
) else (
    echo No hay archivos anteriores que limpiar.
)
echo.

REM Crear directorio bin si no existe
if not exist bin (
    mkdir bin
)

REM Compilar
echo [2/3] Compilando codigo fuente...
cd src
javac -d ..\bin -encoding UTF-8 ^
    rpg\ai\*.java ^
    rpg\builder\*.java ^
    rpg\combat\*.java ^
    rpg\core\*.java ^
    rpg\decorator\*.java ^
    rpg\events\*.java ^
    rpg\factory\*.java ^
    rpg\game\*.java ^
    rpg\inventory\*.java ^
    rpg\persistence\*.java ^
    rpg\quest\*.java ^
    rpg\ui\*.java

if %ERRORLEVEL% EQU 0 (
    echo Compilacion exitosa!
) else (
    echo ERROR: La compilacion fallo.
    cd ..
    pause
    exit /b 1
)
cd ..
echo.

REM Ejecutar
echo [3/3] Lanzando aplicacion...
echo.
cd bin
java rpg.ui.MainGameWindow

cd ..
echo.
echo ======================================
echo Aplicacion cerrada
echo ======================================
pause

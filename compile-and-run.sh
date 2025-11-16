#!/bin/bash
# Script de compilación para RPGv3 (Linux/Mac)
# Compila todos los archivos Java del proyecto

echo "======================================"
echo "RPGv3 - Script de Compilación"
echo "======================================"
echo ""

# Limpiar directorio bin
echo "[1/3] Limpiando archivos compilados anteriores..."
if [ -d "bin/rpg" ]; then
    rm -rf bin/rpg
    echo "Limpieza completada."
else
    echo "No hay archivos anteriores que limpiar."
fi
echo ""

# Crear directorio bin si no existe
mkdir -p bin

# Compilar
echo "[2/3] Compilando código fuente..."
cd src
javac -d ../bin -encoding UTF-8 \
    rpg/ai/*.java \
    rpg/builder/*.java \
    rpg/combat/*.java \
    rpg/core/*.java \
    rpg/decorator/*.java \
    rpg/events/*.java \
    rpg/factory/*.java \
    rpg/game/*.java \
    rpg/inventory/*.java \
    rpg/persistence/*.java \
    rpg/quest/*.java \
    rpg/ui/*.java

if [ $? -eq 0 ]; then
    echo "¡Compilación exitosa!"
else
    echo "ERROR: La compilación falló."
    cd ..
    exit 1
fi
cd ..
echo ""

# Ejecutar
echo "[3/3] Lanzando aplicación..."
echo ""
cd bin
java rpg.ui.MainGameWindow

cd ..
echo ""
echo "======================================"
echo "Aplicación cerrada"
echo "======================================"

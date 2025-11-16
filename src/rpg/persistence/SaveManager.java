package rpg.persistence;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import rpg.core.Character;
import rpg.factory.CharacterType;

/**
 * Gestor de persistencia del juego - Sistema Mejorado.
 * 
 * PROPÓSITO:
 * - Guarda y carga GameState completo (no solo Character)
 * - Soporte para múltiples slots de guardado
 * - Metadata para mostrar previews de partidas
 * - Gestión de carpeta de saves
 * 
 * QUÉ SE GUARDA:
 * - Stats del personaje (HP, maná, nivel, XP)
 * - Inventario completo con todos los items
 * - Equipamiento actual
 * - Tipo de clase (Warrior, Mage, etc.)
 * - Metadata (fecha, versión, tiempo jugado)
 * 
 * ESTRUCTURA:
 * saves/
 *   |- slot1.sav
 *   |- slot2.sav
 *   |- slot3.sav
 *   |- autosave.sav
 * 
 * FORMATO:
 * Archivos .sav binarios (serialización Java)
 */
public class SaveManager {
    
    private static final String SAVES_FOLDER = "saves";
    private static final int MAX_SLOTS = 5;
    
    /**
     * Inicializa el directorio de saves si no existe.
     */
    public SaveManager() {
        try {
            Path savesPath = Paths.get(SAVES_FOLDER);
            if (!Files.exists(savesPath)) {
                Files.createDirectory(savesPath);
            }
        } catch (IOException e) {
            System.err.println("Error creando carpeta de saves: " + e.getMessage());
        }
    }
    
    /**
     * Guarda el estado del juego en un slot específico.
     * @param player El personaje del jugador
     * @param characterType Tipo de clase
     * @param slotNumber Número de slot (1-5)
     * @param saveName Nombre descriptivo del save
     * @return true si se guardó exitosamente
     */
    public boolean saveGame(Character player, CharacterType characterType, 
                           int slotNumber, String saveName) {
        if (slotNumber < 1 || slotNumber > MAX_SLOTS) {
            System.err.println("Slot inválido: " + slotNumber);
            return false;
        }
        
        String filename = SAVES_FOLDER + File.separator + "slot" + slotNumber + ".sav";
        GameState gameState = new GameState(player, characterType, saveName);
        
        return saveGameState(gameState, filename);
    }
    
    /**
     * Guarda el estado del juego con nombre personalizado.
     */
    public boolean saveGame(Character player, CharacterType characterType, String filename) {
        if (!filename.endsWith(".sav")) {
            filename += ".sav";
        }
        String fullPath = SAVES_FOLDER + File.separator + filename;
        GameState gameState = new GameState(player, characterType, filename);
        
        return saveGameState(gameState, fullPath);
    }
    
    /**
     * Método interno para guardar GameState.
     */
    private boolean saveGameState(GameState gameState, String filepath) {
        try (FileOutputStream fos = new FileOutputStream(filepath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(gameState);
            System.out.println("Partida guardada en " + filepath);
            return true;

        } catch (Exception e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Carga un GameState desde un slot específico.
     * @param slotNumber Número de slot (1-5)
     * @return GameState o null si no existe
     */
    public GameState loadGameFromSlot(int slotNumber) {
        if (slotNumber < 1 || slotNumber > MAX_SLOTS) {
            System.err.println("Slot inválido: " + slotNumber);
            return null;
        }
        
        String filename = SAVES_FOLDER + File.separator + "slot" + slotNumber + ".sav";
        return loadGameState(filename);
    }
    
    /**
     * Carga un GameState desde archivo.
     */
    public GameState loadGame(String filename) {
        if (!filename.endsWith(".sav")) {
            filename += ".sav";
        }
        String fullPath = SAVES_FOLDER + File.separator + filename;
        return loadGameState(fullPath);
    }
    
    /**
     * Método interno para cargar GameState.
     */
    private GameState loadGameState(String filepath) {
        try (FileInputStream fis = new FileInputStream(filepath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            GameState gameState = (GameState) ois.readObject();
            System.out.println("Partida cargada desde " + filepath);
            return gameState;

        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + filepath);
            return null;
        } catch (Exception e) {
            System.err.println("Error al cargar la partida: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Obtiene información de todos los slots guardados.
     * @return Lista de GameState (null en slots vacíos)
     */
    public List<GameState> getAllSaves() {
        List<GameState> saves = new ArrayList<>();
        
        for (int i = 1; i <= MAX_SLOTS; i++) {
            GameState state = loadGameFromSlot(i);
            saves.add(state); // Agrega null si el slot está vacío
        }
        
        return saves;
    }
    
    /**
     * Verifica si un slot tiene un save guardado.
     */
    public boolean slotHasSave(int slotNumber) {
        if (slotNumber < 1 || slotNumber > MAX_SLOTS) {
            return false;
        }
        
        String filename = SAVES_FOLDER + File.separator + "slot" + slotNumber + ".sav";
        return Files.exists(Paths.get(filename));
    }
    
    /**
     * Elimina el save de un slot.
     */
    public boolean deleteSave(int slotNumber) {
        if (slotNumber < 1 || slotNumber > MAX_SLOTS) {
            return false;
        }
        
        try {
            String filename = SAVES_FOLDER + File.separator + "slot" + slotNumber + ".sav";
            Files.deleteIfExists(Paths.get(filename));
            System.out.println("Save eliminado: slot " + slotNumber);
            return true;
        } catch (IOException e) {
            System.err.println("Error eliminando save: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Crea un autosave.
     */
    public boolean autoSave(Character player, CharacterType characterType) {
        String filename = SAVES_FOLDER + File.separator + "autosave.sav";
        GameState gameState = new GameState(player, characterType, "AutoSave");
        return saveGameState(gameState, filename);
    }
    
    /**
     * Obtiene la cantidad de slots disponibles.
     */
    public int getMaxSlots() {
        return MAX_SLOTS;
    }
}
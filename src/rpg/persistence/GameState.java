package rpg.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import rpg.core.Character;
import rpg.factory.CharacterType;

/**
 * Encapsula TODO el estado del juego para serialización.
 * 
 * Contiene:
 * - Personaje del jugador completo
 * - Metadata (fecha, versión, tiempo jugado)
 * - Tipo de clase seleccionada
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Datos del juego
    private Character player;
    private CharacterType characterType;
    
    // Metadata
    private String saveName;
    private LocalDateTime saveDate;
    private String gameVersion;
    private int playTimeMinutes;
    
    /**
     * Constructor completo.
     */
    public GameState(Character player, CharacterType characterType, String saveName) {
        this.player = player;
        this.characterType = characterType;
        this.saveName = saveName;
        this.saveDate = LocalDateTime.now();
        this.gameVersion = "3.0";
        this.playTimeMinutes = 0;
    }
    
    // Getters
    public Character getPlayer() { return player; }
    public CharacterType getCharacterType() { return characterType; }
    public String getSaveName() { return saveName; }
    public LocalDateTime getSaveDate() { return saveDate; }
    public String getGameVersion() { return gameVersion; }
    public int getPlayTimeMinutes() { return playTimeMinutes; }
    
    // Setters
    public void setPlayTimeMinutes(int minutes) { this.playTimeMinutes = minutes; }
    
    /**
     * Retorna información legible del save para mostrar en UI.
     */
    public String getDisplayInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(saveName).append("\n");
        sb.append("Personaje: ").append(player.getName()).append(" (").append(characterType).append(")\n");
        sb.append("Nivel: ").append(player.getLevel()).append("\n");
        sb.append("HP: ").append(player.getCurrentHp()).append("/").append(player.getMaxHp()).append("\n");
        sb.append("Oro: ").append(player.getGold()).append("\n");
        sb.append("Guardado: ").append(saveDate.format(formatter));
        return sb.toString();
    }
    
    /**
     * Información resumida para lista de saves.
     */
    public String getSummary() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("%s - Lvl %d %s - %s", 
            saveName, 
            player.getLevel(), 
            characterType, 
            saveDate.format(formatter));
    }
}

package rpg.quest;

import java.io.Serializable;

/**
 * Representa un objetivo dentro de una quest.
 * Puede ser matar enemigos, recoger items, etc.
 */
public class QuestObjective implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public enum ObjectiveType {
        KILL_ENEMY,      // Matar X enemigos de cierto tipo
        COLLECT_ITEM,    // Recoger X items
        REACH_LEVEL,     // Alcanzar nivel X
        TALK_TO_NPC,     // Hablar con un NPC
        EXPLORE_AREA,    // Explorar una zona
        WIN_BATTLES      // Ganar X batallas
    }
    
    private ObjectiveType type;
    private String targetName;  // Nombre del enemigo/item/NPC objetivo
    private int targetAmount;   // Cantidad necesaria
    private int currentAmount;  // Progreso actual
    private String description; // Descripción del objetivo
    
    /**
     * Constructor para un objetivo de quest.
     * @param type Tipo de objetivo
     * @param targetName Nombre del objetivo (enemigo, item, etc.)
     * @param targetAmount Cantidad necesaria para completar
     * @param description Descripción del objetivo
     */
    public QuestObjective(ObjectiveType type, String targetName, int targetAmount, String description) {
        this.type = type;
        this.targetName = targetName;
        this.targetAmount = targetAmount;
        this.currentAmount = 0;
        this.description = description;
    }
    
    /**
     * Incrementa el progreso del objetivo.
     * @param amount Cantidad a incrementar
     * @return true si el objetivo se completó con este incremento
     */
    public boolean incrementProgress(int amount) {
        if (isCompleted()) {
            return false; // Ya estaba completado
        }
        
        currentAmount += amount;
        if (currentAmount >= targetAmount) {
            currentAmount = targetAmount; // No exceder el objetivo
            return true; // Se acaba de completar
        }
        
        return false;
    }
    
    /**
     * Verifica si este objetivo aplica al target dado.
     * @param targetName Nombre del enemigo/item a verificar
     * @return true si coincide con el objetivo
     */
    public boolean matchesTarget(String targetName) {
        if (this.targetName == null || this.targetName.isEmpty()) {
            return true; // Objetivo genérico (cualquier enemigo/item)
        }
        return this.targetName.equalsIgnoreCase(targetName);
    }
    
    /**
     * Verifica si el objetivo está completado.
     */
    public boolean isCompleted() {
        return currentAmount >= targetAmount;
    }
    
    /**
     * Obtiene el progreso como texto.
     */
    public String getProgressText() {
        return currentAmount + "/" + targetAmount;
    }
    
    /**
     * Obtiene el progreso como porcentaje (0.0 a 1.0).
     */
    public double getProgressPercentage() {
        if (targetAmount == 0) return 1.0;
        return (double) currentAmount / targetAmount;
    }
    
    /**
     * Resetea el progreso del objetivo.
     */
    public void reset() {
        currentAmount = 0;
    }
    
    // Getters
    public ObjectiveType getType() {
        return type;
    }
    
    public String getTargetName() {
        return targetName;
    }
    
    public int getTargetAmount() {
        return targetAmount;
    }
    
    public int getCurrentAmount() {
        return currentAmount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setCurrentAmount(int amount) {
        this.currentAmount = Math.max(0, Math.min(amount, targetAmount));
    }
    
    @Override
    public String toString() {
        String status = isCompleted() ? "[COMPLETADO]" : "[" + getProgressText() + "]";
        return status + " " + description;
    }
}

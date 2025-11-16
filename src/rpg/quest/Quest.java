package rpg.quest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import rpg.inventory.Item;

/**
 * Representa una misión (quest) en el juego.
 * Cada misión tiene un ID único, título, descripción y estado.
 * Ahora incluye objetivos rastreables y recompensas de items.
 * Implementa Serializable para poder guardar el progreso de las misiones.
 */
public class Quest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // --- Atributos ---
    private String id;          // Identificador único (ej. "SLAY_SLIMES")
    private String title;       // Nombre de la misión (ej. "Plaga de Slimes")
    private String description; // Descripción de lo que hay que hacer
    private QuestStatus status; // Estado actual de la misión
    
    // Objetivos de la quest
    private List<QuestObjective> objectives;
    
    // Atributos opcionales para futuras expansiones
    private int experienceReward; // XP que otorga al completarla
    private int goldReward;       // Oro que otorga al completarla
    private List<Item> itemRewards; // Items que otorga al completarla
    
    // --- Constructor Principal ---
    /**
     * Crea una nueva misión.
     * @param id Identificador único de la misión
     * @param title Nombre de la misión
     * @param description Descripción de la misión
     */
    public Quest(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = QuestStatus.AVAILABLE; // Comienza disponible
        this.experienceReward = 0;
        this.goldReward = 0;
        this.objectives = new ArrayList<>();
        this.itemRewards = new ArrayList<>();
    }
    
    // --- Constructor con Recompensas ---
    /**
     * Crea una nueva misión con recompensas específicas.
     * @param id Identificador único de la misión
     * @param title Nombre de la misión
     * @param description Descripción de la misión
     * @param experienceReward XP que otorga
     * @param goldReward Oro que otorga
     */
    public Quest(String id, String title, String description, int experienceReward, int goldReward) {
        this(id, title, description);
        this.experienceReward = experienceReward;
        this.goldReward = goldReward;
    }
    
    // --- Métodos para Objetivos ---
    
    /**
     * Añade un objetivo a la quest.
     */
    public void addObjective(QuestObjective objective) {
        this.objectives.add(objective);
    }
    
    /**
     * Actualiza el progreso de un objetivo específico.
     * @param objectiveType Tipo de objetivo
     * @param targetName Nombre del objetivo (enemigo, item, etc.)
     * @param amount Cantidad a incrementar
     * @return true si la quest se completó con esta actualización
     */
    public boolean updateObjective(QuestObjective.ObjectiveType objectiveType, String targetName, int amount) {
        if (this.status != QuestStatus.ACTIVE) {
            return false;
        }
        
        boolean questCompleted = true;
        
        for (QuestObjective objective : objectives) {
            if (objective.getType() == objectiveType && objective.matchesTarget(targetName)) {
                objective.incrementProgress(amount);
            }
            
            if (!objective.isCompleted()) {
                questCompleted = false;
            }
        }
        
        // Si todos los objetivos están completados, completar la quest automáticamente
        if (questCompleted && !objectives.isEmpty()) {
            complete();
            return true;
        }
        
        return false;
    }
    
    /**
     * Verifica si todos los objetivos están completados.
     */
    public boolean areAllObjectivesCompleted() {
        if (objectives.isEmpty()) {
            return false; // Sin objetivos no se puede completar
        }
        
        for (QuestObjective objective : objectives) {
            if (!objective.isCompleted()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Obtiene el progreso total de la quest (0.0 a 1.0).
     */
    public double getTotalProgress() {
        if (objectives.isEmpty()) {
            return 0.0;
        }
        
        double totalProgress = 0.0;
        for (QuestObjective objective : objectives) {
            totalProgress += objective.getProgressPercentage();
        }
        
        return totalProgress / objectives.size();
    }
    
    // --- Métodos de Estado ---
    
    /**
     * Inicia la misión (cambia el estado a ACTIVE).
     */
    public void start() {
        if (this.status == QuestStatus.AVAILABLE) {
            this.status = QuestStatus.ACTIVE;
            System.out.println("Misión iniciada: " + this.title);
        }
    }
    
    /**
     * Completa la misión (cambia el estado a COMPLETED).
     */
    public void complete() {
        if (this.status == QuestStatus.ACTIVE) {
            this.status = QuestStatus.COMPLETED;
            System.out.println("¡Misión completada: " + this.title + "!");
            if (experienceReward > 0 || goldReward > 0 || !itemRewards.isEmpty()) {
                System.out.println("Recompensas obtenidas!");
                if (experienceReward > 0) System.out.println("- " + experienceReward + " XP");
                if (goldReward > 0) System.out.println("- " + goldReward + " Oro");
                for (Item item : itemRewards) {
                    System.out.println("- " + item.getName());
                }
            }
        }
    }
    
    /**
     * Reinicia la misión a su estado disponible (útil para misiones repetibles).
     */
    public void reset() {
        this.status = QuestStatus.AVAILABLE;
        for (QuestObjective objective : objectives) {
            objective.reset();
        }
    }
    
    // --- Métodos para Recompensas de Items ---
    
    /**
     * Añade un item como recompensa de la quest.
     */
    public void addItemReward(Item item) {
        this.itemRewards.add(item);
    }
    
    // --- Getters ---
    
    public List<QuestObjective> getObjectives() {
        return objectives;
    }
    
    public List<Item> getItemRewards() {
        return itemRewards;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public QuestStatus getStatus() {
        return this.status;
    }
    
    public int getExperienceReward() {
        return this.experienceReward;
    }
    
    public int getGoldReward() {
        return this.goldReward;
    }
    
    // Método para obtener descripción de recompensas
    public String getRewards() {
        StringBuilder sb = new StringBuilder();
        if (experienceReward > 0) {
            sb.append(experienceReward).append(" XP");
        }
        if (goldReward > 0) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(goldReward).append(" Oro");
        }
        if (!itemRewards.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            for (int i = 0; i < itemRewards.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(itemRewards.get(i).getName());
            }
        }
        return sb.length() > 0 ? sb.toString() : "Sin recompensas";
    }
    
    /**
     * Obtiene una descripción detallada de los objetivos.
     */
    public String getObjectivesDescription() {
        if (objectives.isEmpty()) {
            return "Sin objetivos especificos";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objectives.size(); i++) {
            if (i > 0) sb.append("\n");
            sb.append((i + 1)).append(". ").append(objectives.get(i).toString());
        }
        return sb.toString();
    }
    
    // --- Setters (para modificar recompensas después de crear la misión) ---
    
    public void setExperienceReward(int experienceReward) {
        this.experienceReward = experienceReward;
    }
    
    public void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
    }
    
    // --- Métodos de Utilidad ---
    
    /**
     * Verifica si la misión está disponible para ser iniciada.
     */
    public boolean isAvailable() {
        return this.status == QuestStatus.AVAILABLE;
    }
    
    /**
     * Verifica si la misión está activa.
     */
    public boolean isActive() {
        return this.status == QuestStatus.ACTIVE;
    }
    
    /**
     * Verifica si la misión está completada.
     */
    public boolean isCompleted() {
        return this.status == QuestStatus.COMPLETED;
    }
    
    // --- toString para depuración ---
    
    @Override
    public String toString() {
        return "Quest{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", xpReward=" + experienceReward +
                ", goldReward=" + goldReward +
                '}';
    }
}
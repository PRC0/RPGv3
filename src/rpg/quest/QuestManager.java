package rpg.quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rpg.inventory.*;

/**
 * Gestor del sistema de misiones del juego.
 * 
 * RESPONSABILIDADES:
 * - Almacena todas las misiones disponibles (base de datos)
 * - Rastrea misiones activas del jugador
 * - Registra misiones completadas (histórico)
 * - Transiciona misiones entre estados (AVAILABLE → ACTIVE → COMPLETED)
 * - Actualiza progreso de objetivos cuando ocurren eventos
 * 
 * CICLO DE VIDA DE UNA MISIÓN:
 * 1. initializeQuests() → carga en questDatabase
 * 2. startQuest(id) → mueve a activeQuests
 * 3. updateProgress() → actualiza objetivos
 * 4. completeQuest(id) → mueve a completedQuests
 * 
 * AGREGACIÓN:
 * Una Quest puede involucrar múltiples Characters (NPCs que dan/reciben items)
 * pero existe independientemente de ellos.
 * 
 * EXPANSIÓN:
 * Para añadir más misiones, modificar initializeQuests() o cargar desde archivo
 */
public class QuestManager {

    // "Base de datos" de todas las misiones que existen en el juego
    private Map<String, Quest> questDatabase;
    
    // Misiones que el jugador ha aceptado
    private List<Quest> activeQuests;
    
    // Misiones que el jugador ya completó
    private List<Quest> completedQuests;

    public QuestManager() {
        this.questDatabase = new HashMap<>();
        this.activeQuests = new ArrayList<>();
        this.completedQuests = new ArrayList<>();
        
        // Cargamos todas las misiones del juego
        initializeQuests();
    }

    /**
     * Carga todas las misiones del juego en la base de datos.
     */
    private void initializeQuests() {
        // Misión 1: Caza de Slimes
        Quest slimeQuest = new Quest(
            "SLAY_SLIMES", 
            "Plaga de Slimes", 
            "El bosque esta infestado de slimes. ¡Elimina a 5 de ellos!",
            150, // XP
            50   // Oro
        );
        slimeQuest.addObjective(new QuestObjective(
            QuestObjective.ObjectiveType.KILL_ENEMY,
            "Slime",
            5,
            "Eliminar 5 Slimes"
        ));
        slimeQuest.addItemReward(new HealthPotion());
        this.questDatabase.put(slimeQuest.getId(), slimeQuest);
        
        // Misión 2: El Sacerdote Necesita Pociones
        Quest potionQuest = new Quest(
            "COLLECT_POTIONS",
            "Suministros para el Templo",
            "El Hermano Elron necesita 3 pociones de salud para el ritual de sanación.",
            100,
            30
        );
        potionQuest.addObjective(new QuestObjective(
            QuestObjective.ObjectiveType.COLLECT_ITEM,
            "Poción de Salud",
            3,
            "Recoger 3 Pociones de Salud"
        ));
        this.questDatabase.put(potionQuest.getId(), potionQuest);
        
        // Misión 3: Cacería de Lobos
        Quest wolfQuest = new Quest(
            "HUNT_WOLVES",
            "Lobos Salvajes",
            "Los lobos estan atacando a los viajeros. Elimina a 3 lobos del bosque.",
            200,
            75
        );
        wolfQuest.addObjective(new QuestObjective(
            QuestObjective.ObjectiveType.KILL_ENEMY,
            "Lobo",
            3,
            "Eliminar 3 Lobos"
        ));
        wolfQuest.addItemReward(new Sword("Espada de Hierro", "Una espada forjada en hierro", 8));
        this.questDatabase.put(wolfQuest.getId(), wolfQuest);
        
        // Misión 4: Exterminador de Goblins
        Quest goblinQuest = new Quest(
            "GOBLIN_CAMP",
            "Campamento Goblin",
            "Destruye el campamento goblin eliminando a 7 de ellos.",
            300,
            100
        );
        goblinQuest.addObjective(new QuestObjective(
            QuestObjective.ObjectiveType.KILL_ENEMY,
            "Goblin",
            7,
            "Eliminar 7 Goblins"
        ));
        goblinQuest.addItemReward(new Chestplate("Peto de Acero", "Armadura resistente de acero", 12));
        goblinQuest.addItemReward(new HealthPotion());
        this.questDatabase.put(goblinQuest.getId(), goblinQuest);
        
        // Misión 5: Ascenso del Guerrero
        Quest levelQuest = new Quest(
            "REACH_LEVEL_5",
            "Camino del Guerrero",
            "Entrena y alcanza el nivel 5 para probar tu valía.",
            500,
            200
        );
        levelQuest.addObjective(new QuestObjective(
            QuestObjective.ObjectiveType.REACH_LEVEL,
            "",
            5,
            "Alcanzar nivel 5"
        ));
        levelQuest.addItemReward(new Sword("Espada del Veterano", "Espada para guerreros experimentados", 15));
        levelQuest.addItemReward(new ManaPotion("Poción de Maná Grande", "Restaura 80 de maná", 80));
        this.questDatabase.put(levelQuest.getId(), levelQuest);
        
        // Misión 6: Venganza Esquelética
        Quest skeletonQuest = new Quest(
            "UNDEAD_THREAT",
            "Amenaza No-Muerta",
            "Los esqueletos se levantan de sus tumbas. Derrota a 4 de ellos.",
            250,
            80
        );
        skeletonQuest.addObjective(new QuestObjective(
            QuestObjective.ObjectiveType.KILL_ENEMY,
            "Esqueleto",
            4,
            "Eliminar 4 Esqueletos"
        ));
        skeletonQuest.addItemReward(new Shield("Escudo de Roble", "Escudo resistente de madera reforzada", 10));
        this.questDatabase.put(skeletonQuest.getId(), skeletonQuest);
        
        // Misión 7: Maestro de Batallas
        Quest battleQuest = new Quest(
            "WIN_BATTLES",
            "Maestro de Combate",
            "Demuestra tu habilidad ganando 10 batallas consecutivas.",
            400,
            150
        );
        battleQuest.addObjective(new QuestObjective(
            QuestObjective.ObjectiveType.WIN_BATTLES,
            "",
            10,
            "Ganar 10 batallas"
        ));
        battleQuest.addItemReward(new Chestplate("Armadura de Campeón", "Armadura legendaria", 20));
        battleQuest.addItemReward(new HealthPotion());
        battleQuest.addItemReward(new ManaPotion("Poción de Maná", "Restaura 50 de maná", 50));
        this.questDatabase.put(battleQuest.getId(), battleQuest);
    }

    // --- API Pública ---

    /**
     * Inicia una misión moviéndola de 'disponible' a 'activa'.
     */
    public void startQuest(String questId) {
        Quest quest = questDatabase.get(questId);
        if (quest != null && quest.getStatus() == QuestStatus.AVAILABLE) {
            quest.start();
            this.activeQuests.add(quest);
        }
    }
    
    /**
     * Completa una misión activa.
     */
    public void completeQuest(String questId) {
        Quest questToComplete = null;
        for (Quest quest : this.activeQuests) {
            if (quest.getId().equals(questId)) {
                questToComplete = quest;
                break;
            }
        }
        
        if (questToComplete != null) {
            questToComplete.complete();
            this.activeQuests.remove(questToComplete);
            this.completedQuests.add(questToComplete);
        }
    }
    
    /**
     * Notifica al QuestManager que se mató a un enemigo.
     * Actualiza las quests relevantes.
     * @param enemyName Nombre del enemigo eliminado
     */
    public void notifyEnemyKilled(String enemyName) {
        for (Quest quest : activeQuests) {
            quest.updateObjective(QuestObjective.ObjectiveType.KILL_ENEMY, enemyName, 1);
        }
    }
    
    /**
     * Notifica al QuestManager que se recogió un item.
     * Actualiza las quests relevantes.
     * @param itemName Nombre del item recogido
     */
    public void notifyItemCollected(String itemName) {
        for (Quest quest : activeQuests) {
            quest.updateObjective(QuestObjective.ObjectiveType.COLLECT_ITEM, itemName, 1);
        }
    }
    
    /**
     * Notifica al QuestManager que el jugador subió de nivel.
     * Actualiza las quests relevantes.
     * @param newLevel Nuevo nivel del jugador
     */
    public void notifyLevelReached(int newLevel) {
        for (Quest quest : activeQuests) {
            for (QuestObjective objective : quest.getObjectives()) {
                if (objective.getType() == QuestObjective.ObjectiveType.REACH_LEVEL) {
                    objective.setCurrentAmount(newLevel);
                    if (objective.isCompleted() && quest.areAllObjectivesCompleted()) {
                        quest.complete();
                    }
                }
            }
        }
    }
    
    /**
     * Notifica al QuestManager que se ganó una batalla.
     * Actualiza las quests relevantes.
     */
    public void notifyBattleWon() {
        for (Quest quest : activeQuests) {
            quest.updateObjective(QuestObjective.ObjectiveType.WIN_BATTLES, "", 1);
        }
    }

    // --- Getters para la GUI ---
    public List<Quest> getActiveQuests() {
        return this.activeQuests;
    }
    
    public List<Quest> getAvailableQuests() {
        List<Quest> available = new ArrayList<>();
        for (Quest quest : questDatabase.values()) {
            if (quest.getStatus() == QuestStatus.AVAILABLE && !activeQuests.contains(quest)) {
                available.add(quest);
            }
        }
        return available;
    }
    
    public Quest getQuest(String questId) {
        return this.questDatabase.get(questId);
    }
}
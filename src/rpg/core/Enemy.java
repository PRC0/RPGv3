// Archivo: rpg/core/Enemy.java
package rpg.core;

// Importa las clases de otros paquetes
import rpg.combat.AttackStrategy;
import rpg.ai.EnemyAI;
import rpg.ai.AggressiveAI;
import rpg.inventory.LootTable;
import rpg.inventory.Item;
import java.util.List;

public class Enemy extends Character {
    
    private static final long serialVersionUID = 1L;
    private EnemyAI ai; // Estrategia de IA
    private int experienceValue; // XP que otorga al morir
    private LootTable lootTable; // Tabla de items que puede dropear

    // Constructor simple (sin IA)
    public Enemy(String name, int level, int baseAttack, int baseMagic, int maxHp, AttackStrategy strategy) {
        // Llama al constructor de Character. Los enemigos no tienen maná.
        super(name, level, baseAttack, baseMagic, maxHp, 0);
        this.setAttackStrategy(strategy);
        this.ai = new AggressiveAI(); // IA por defecto
        this.experienceValue = level * 10; // XP basado en nivel
        this.lootTable = new LootTable(); // Tabla de loot vacía por defecto
    }
    
    // Constructor con IA personalizada
    public Enemy(String name, int level, int baseAttack, int baseMagic, int maxHp, 
                 AttackStrategy strategy, EnemyAI ai, int xpValue) {
        super(name, level, baseAttack, baseMagic, maxHp, 0);
        this.setAttackStrategy(strategy);
        this.ai = ai;
        this.experienceValue = xpValue;
        this.lootTable = new LootTable();
    }

    // --- Implementamos los métodos abstractos (obligatorios) ---

    @Override
    public void specialAbility(Character target) {
        // La "habilidad especial" de un enemigo es solo un golpe fuerte
        int damage = (int) (this.baseAttack * 1.5);
        
        String damageMessage = target.receiveDamage(damage);
        if (!damageMessage.isEmpty()) {
            System.out.println(damageMessage);
        }
    }

    @Override
    public void levelUp() {
        // Los enemigos no suben de nivel. Dejamos este método vacío.
        // Opcional: imprimir un mensaje de burla.
        System.out.println(this.name + " se ríe de tu intento de 'levelUp'!");
    }
    
    /**
     * Usa la IA para decidir la acción del enemigo.
     */
    public void performAIAction(Character target) {
        if (ai != null) {
            ai.takeTurn(this, target);
        } else {
            // Si no tiene IA, ataca normalmente
            this.attack(target);
        }
    }
    
    /**
     * Genera los items que dropea este enemigo al morir.
     * @return Lista de items dropeados
     */
    public List<Item> generateLoot() {
        if (lootTable != null) {
            return lootTable.generateDrops();
        }
        return new java.util.ArrayList<>();
    }
    
    /**
     * Genera el oro que dropea este enemigo al morir.
     * @return Cantidad de oro dropeado
     */
    public int generateGold() {
        if (lootTable != null) {
            return lootTable.generateGold();
        }
        return 0;
    }
    
    // Getters y Setters
    public int getExperienceValue() {
        return experienceValue;
    }
    
    public void setAI(EnemyAI ai) {
        this.ai = ai;
    }
    
    public LootTable getLootTable() {
        return lootTable;
    }
    
    public void setLootTable(LootTable lootTable) {
        this.lootTable = lootTable;
    }
}
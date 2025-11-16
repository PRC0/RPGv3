package rpg.core;

import rpg.combat.RangedAttack;
import rpg.ai.EvasiveAI;
import rpg.events.EventType;
import rpg.events.GameEventManager;
import rpg.inventory.*;

/**
 * Bandido - Atacante a distancia con alta evasión.
 * 
 * CARACTERÍSTICAS ESPECIALES:
 * - Ágil y difícil de predecir
 * - Puede robar items (futuro)
 * - Ataques rápidos
 * 
 * HABILIDAD ESPECIAL: [DISPARO CRÍTICO]
 * - Daño: Ataque × 2.2
 * - 30% probabilidad de crítico adicional (×3)
 */
public class Bandit extends Enemy {
    
    private static final long serialVersionUID = 1L;
    private static final double CRIT_CHANCE = 0.30;
    
    public Bandit() {
        super("Bandido", 5, 5, 0, 55, 
              new RangedAttack(), new EvasiveAI(), 45);
        
        configureLoot();
    }
    
    private void configureLoot() {
        LootTable loot = new LootTable();
        
        // Oro robado
        loot.setGoldReward(30, 40); // 30-70 oro
        
        // Items robados variados
        loot.addDrop(new HealthPotion(), 0.35); // 35% chance
        loot.addDrop(new ManaPotion("Poción de Maná", "Restaura 50 de maná", 50), 0.20); // 20% chance
        loot.addDrop(new Sword("Espada Curva", "Espada de bandido afilada", 12), 0.15); // 15% chance
        loot.addDrop(new Boots("Botas de Bandido", "Botas ligeras para moverse rápido", 4, 10), 0.12); // 12% chance
        
        this.setLootTable(loot);
    }
    
    @Override
    public void specialAbility(Character target) {
        String aimMessage = "[APUNTAR] " + this.name + " apunta cuidadosamente...";
        System.out.println(aimMessage);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, aimMessage);
        
        int damage = (int) (this.baseAttack * 2.2);
        boolean isCrit = Math.random() < CRIT_CHANCE;
        
        if (isCrit) {
            damage = (int) (this.baseAttack * 3.0);
            String critMessage = "*** ¡¡CRITICO!! ***";
            System.out.println(critMessage);
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, critMessage);
        }
        
        String shootMessage = ">>> ¡DISPARO CRITICO! <<<";
        System.out.println(shootMessage);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, shootMessage);
        
        String damageMessage = target.receiveDamage(damage);
        if (!damageMessage.isEmpty()) {
            System.out.println(damageMessage);
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, damageMessage);
        }
    }
}

package rpg.core;

import rpg.combat.MeleeAttack;
import rpg.ai.AggressiveAI;
import rpg.events.EventType;
import rpg.events.GameEventManager;
import rpg.inventory.*;

/**
 * Lobo Salvaje - Depredador bestial feroz.
 * 
 * CARACTERÍSTICAS ESPECIALES:
 * - Ataques múltiples rápidos
 * - Instinto de cazador
 * - Aumenta daño cuando objetivo HP < 50%
 * 
 * HABILIDAD ESPECIAL: [MORDIDA SALVAJE]
 * - Daño: Ataque × 1.8
 * - Si objetivo HP < 50%: Daño adicional +50%
 */
public class Wolf extends Enemy {
    
    private static final long serialVersionUID = 1L;
    
    public Wolf() {
        super("Lobo Salvaje", 4, 4, 0, 45, 
              new MeleeAttack(), new AggressiveAI(), 35);
        
        configureLoot();
    }
    
    private void configureLoot() {
        LootTable loot = new LootTable();
        
        // Oro
        loot.setGoldReward(15, 20); // 15-35 oro
        
        // Items
        loot.addDrop(new HealthPotion(), 0.30); // 30% chance
        loot.addDrop(new Boots("Botas de Cuero", "Botas hechas de cuero de lobo", 3, 5), 0.10); // 10% chance
        
        this.setLootTable(loot);
    }
    
    @Override
    public void specialAbility(Character target) {
        String growlMessage = "[GRUÑIDO] " + this.name + " gruñe amenazadoramente...";
        System.out.println(growlMessage);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, growlMessage);
        
        int damage = (int) (this.baseAttack * 1.8);
        
        // Instinto de cazador: más daño a presas heridas
        double targetHpPercent = (double) target.getCurrentHp() / target.getMaxHp();
        if (targetHpPercent < 0.50) {
            damage = (int) (damage * 1.5);
            String huntMessage = "[CAZADOR] ¡Instinto de cazador activado!";
            System.out.println(huntMessage);
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, huntMessage);
        }
        
        String biteMessage = ">>> ¡MORDIDA SALVAJE! <<<";
        System.out.println(biteMessage);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, biteMessage);
        
        String damageMessage = target.receiveDamage(damage);
        if (!damageMessage.isEmpty()) {
            System.out.println(damageMessage);
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, damageMessage);
        }
    }
}

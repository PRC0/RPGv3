package rpg.core;

import rpg.combat.MagicAttack;
import rpg.ai.MageAI;
import rpg.events.EventType;
import rpg.events.GameEventManager;
import rpg.inventory.*;

/**
 * Mago Oscuro - Hechicero de las sombras.
 * 
 * CARACTERÍSTICAS ESPECIALES:
 * - Usa magia oscura
 * - Puede drenar vida del enemigo
 * - Barrera mágica más efectiva
 * 
 * HABILIDAD ESPECIAL: [DRENAJE DE VIDA]
 * - Daño: Magia × 1.8
 * - Cura 50% del daño infligido
 */
public class DarkMage extends Enemy {
    
    private static final long serialVersionUID = 1L;
    
    public DarkMage() {
        super("Mago Oscuro", 8, 8, 15, 60, 
              new MagicAttack(), new MageAI(), 80);
        
        // Maná inicial para hechizos
        this.currentMana = 40;
        this.maxMana = 40;
        
        configureLoot();
    }
    
    private void configureLoot() {
        LootTable loot = new LootTable();
        
        // Oro moderado
        loot.setGoldReward(50, 60); // 50-110 oro
        
        // Items mágicos
        loot.addDrop(new ManaPotion("Poción de Maná Grande", "Restaura 80 de maná", 80), 0.50); // 50% chance
        loot.addDrop(new HealthPotion(), 0.25); // 25% chance
        loot.addDrop(new Ring("Anillo de Sombras", "Anillo imbuido con magia oscura", 0, 10, 5), 0.18); // 18% chance
        loot.addDrop(new Shield("Grimorio Oscuro", "Libro de hechizos prohibidos", 15), 0.10); // 10% chance
        
        this.setLootTable(loot);
    }
    
    @Override
    public void specialAbility(Character target) {
        String castMessage = "[MAGIA] " + this.name + " invoca magia prohibida...";
        System.out.println(castMessage);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, castMessage);
        
        // Drenaje de Vida - 1.8x daño mágico
        int damage = (int) (this.baseMagic * 1.8);
        
        String drainMessage = ">>> ¡DRENAJE DE VIDA! <<<";
        System.out.println(drainMessage);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, drainMessage);
        
        String damageMessage = target.receiveDamage(damage);
        if (!damageMessage.isEmpty()) {
            System.out.println(damageMessage);
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, damageMessage);
        }
        
        // Curación por 50% del daño
        int healing = damage / 2;
        this.currentHp = Math.min(this.currentHp + healing, this.maxHp);
        
        String healMessage = "[ABSORCION] " + this.name + " absorbe " + healing + " HP de energia vital!";
        System.out.println(healMessage);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, healMessage);
    }
}

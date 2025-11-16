package rpg.core;

import rpg.combat.RangedAttack;
import rpg.ai.AggressiveAI;
import rpg.events.EventType;
import rpg.events.GameEventManager;
import rpg.inventory.*;

/**
 * Skeleton - Arquero no-muerto.
 * 
 * CARACTERÍSTICAS ESPECIALES:
 * - Ya está muerto (no siente dolor)
 * - 20% probabilidad de ignorar ataque (huesos)
 * - Ataques de flechas envenenadas
 * 
 * HABILIDAD ESPECIAL: [FLECHA ENVENENADA]
 * - Daño: Ataque × 1.7
 * - Veneno: 3 daño adicional por 2 turnos (futuro)
 */
public class Skeleton extends Enemy {
    
    private static final long serialVersionUID = 1L;
    private static final double DODGE_CHANCE = 0.20;
    
    public Skeleton() {
        super("Skeleton", 4, 7, 0, 40, 
              new RangedAttack(), new AggressiveAI(), 40);
        
        configureLoot();
    }
    
    private void configureLoot() {
        LootTable loot = new LootTable();
        
        // Oro (poco)
        loot.setGoldReward(10, 15); // 10-25 oro
        
        // Items (huesos y armas viejas)
        loot.addDrop(new HealthPotion(), 0.20); // 20% chance
        loot.addDrop(new Shield("Escudo de Huesos", "Escudo hecho de huesos reforzados", 8), 0.12); // 12% chance
        loot.addDrop(new Ring("Anillo Maldito", "Un anillo oscuro con aura siniestra", 0, 3, 0), 0.05); // 5% chance raro
        
        this.setLootTable(loot);
    }
    
    @Override
    public void specialAbility(Character target) {
        String prepMessage = "[NO-MUERTO] " + this.name + " prepara una flecha oscura...";
        System.out.println(prepMessage);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, prepMessage);
        
        int damage = (int) (this.baseAttack * 1.7);
        
        String poisonMessage = ">>> ¡FLECHA ENVENENADA! <<<";
        System.out.println(poisonMessage);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, poisonMessage);
        
        String damageMessage = target.receiveDamage(damage);
        if (!damageMessage.isEmpty()) {
            System.out.println(damageMessage);
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, damageMessage);
        }
        
        String effectMsg = "[VENENO] El veneno se infiltra en las venas...";
        System.out.println(effectMsg);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, effectMsg);
    }
    
    @Override
    public String receiveDamage(int amount) {
        // 20% probabilidad de esquivar por estructura ósea
        if (Math.random() < DODGE_CHANCE) {
            String dodgeMsg = "[ESQUIVA] ¡La flecha atraviesa entre los huesos sin hacer daño!";
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, dodgeMsg);
            return dodgeMsg;
        }
        
        return super.receiveDamage(amount);
    }
}

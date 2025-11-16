package rpg.core;

import rpg.combat.MeleeAttack;
import rpg.ai.DefensiveAI;
import rpg.events.EventType;
import rpg.events.GameEventManager;
import rpg.inventory.*;

/**
 * Orco Guerrero - Tanque cuerpo a cuerpo con alta defensa.
 * 
 * CARACTERÍSTICAS ESPECIALES:
 * - Armadura natural gruesa (+3 defensa base)
 * - Contraataque cuando es atacado mientras se defiende
 * - Resistencia brutal
 * 
 * HABILIDAD ESPECIAL: [CARGA SALVAJE]
 * - Daño: Ataque × 2.0
 * - Ignora 50% de la defensa del objetivo
 */
public class OrcWarrior extends Enemy {
    
    private static final long serialVersionUID = 1L;
    
    public OrcWarrior() {
        super("Orco Guerrero", 6, 6, 0, 80, 
              new MeleeAttack(), new DefensiveAI(), 60);
        
        // Bonus de defensa natural
        this.defenseBonus = 12;
        
        configureLoot();
    }
    
    private void configureLoot() {
        LootTable loot = new LootTable();
        
        // Oro regular
        loot.setGoldReward(35, 45); // 35-80 oro
        
        // Items de guerrero (armaduras y armas pesadas)
        loot.addDrop(new HealthPotion(), 0.40); // 40% chance
        loot.addDrop(new Chestplate("Armadura de Orco", "Armadura pesada y resistente", 15), 0.20); // 20% chance
        loot.addDrop(new Sword("Hacha de Guerra", "Hacha brutal de orco", 18), 0.15); // 15% chance
        loot.addDrop(new Shield("Escudo de Hierro", "Escudo masivo de hierro", 12), 0.18); // 18% chance
        
        this.setLootTable(loot);
    }
    
    @Override
    public void specialAbility(Character target) {
        String chargeMessage = "[CARGA] " + this.name + " carga con toda su fuerza...";
        System.out.println(chargeMessage);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, chargeMessage);
        
        // Carga Salvaje - 2.0x daño
        int damage = (int) (this.baseAttack * 2.0);
        
        String attackMessage = ">>> ¡CARGA SALVAJE! <<<";
        System.out.println(attackMessage);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, attackMessage);
        
        String damageMessage = target.receiveDamage(damage);
        if (!damageMessage.isEmpty()) {
            System.out.println(damageMessage);
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, damageMessage);
        }
        
        String impactMsg = "*** ¡Impacto devastador! ***";
        System.out.println(impactMsg);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, impactMsg);
    }
}

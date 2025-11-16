// MagicAttack.java
package rpg.combat;

import rpg.core.Character;
import rpg.events.EventType;
import rpg.events.GameEventManager;

public class MagicAttack implements AttackStrategy {
    
    private static final long serialVersionUID = 1L;
    private static final int MANA_COST = 5; // Costo de maná del ataque básico

    @Override
    public void execute(Character attacker, Character target) {
        
        // 1. Revisa si el atacante tiene maná
        if (attacker.consumeMana(MANA_COST)) {
            // 2. Si tiene, calcula el daño con la magia base
            int damage = attacker.getBaseMagic();
            
            String attackMessage = attacker.getName() + " lanza un hechizo a " + target.getName() + " por " + damage + " de daño!";
            System.out.println(attackMessage);
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, attackMessage);
            
            String damageMessage = target.receiveDamage(damage);
            if (!damageMessage.isEmpty()) {
                System.out.println(damageMessage);
                GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, damageMessage);
            }
        
        } else {
            // 3. Si no tiene maná, no hace nada
            String noManaMessage = attacker.getName() + " intenta lanzar un hechizo, ¡pero no tiene maná!";
            System.out.println(noManaMessage);
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, noManaMessage);
        }
    }
}
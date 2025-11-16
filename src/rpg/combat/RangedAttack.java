// RangedAttack.java
package rpg.combat;

import rpg.core.Character;

public class RangedAttack implements AttackStrategy {
    
    private static final long serialVersionUID = 1L;

    @Override
    public void execute(Character attacker, Character target) {
        // Lógica de un ataque a distancia simple
        // Usa el 'baseAttack' del arquero
        int damage = attacker.getBaseAttack();
        
        System.out.println(attacker.getName() + " dispara una flecha a " + target.getName() + " por " + damage + " de daño!");
        
        String damageMessage = target.receiveDamage(damage);
        if (!damageMessage.isEmpty()) {
            System.out.println(damageMessage);
        }
    }
}
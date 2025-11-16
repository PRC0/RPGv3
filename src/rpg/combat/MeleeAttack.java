// MeleeAttack.java
package rpg.combat;

import rpg.core.Character;

public class MeleeAttack implements AttackStrategy {
    
    private static final long serialVersionUID = 1L;

    @Override
    public void execute(Character attacker, Character target) {
        // Lógica de un ataque cuerpo a cuerpo simple:
        // Obtiene el ataque base del atacante
        int damage = attacker.getBaseAttack();
        
        System.out.println(attacker.getName() + " ataca a " + target.getName() + " por " + damage + " de daño!");
        
        // Llama al método que ya programamos en Character y muestra el resultado
        String damageMessage = target.receiveDamage(damage);
        if (!damageMessage.isEmpty()) {
            System.out.println(damageMessage);
        }
    }
}
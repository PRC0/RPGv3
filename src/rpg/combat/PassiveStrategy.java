package rpg.combat;

import rpg.core.Character;

/**
 * Una estrategia de ataque para NPCs que no hacen nada en combate.
 */
public class PassiveStrategy implements AttackStrategy {
    
    private static final long serialVersionUID = 1L;

    @Override
    public void execute(Character attacker, Character target) {
        // No hace nada
        System.out.println(attacker.getName() + " está en una postura pasiva.");
    }
}

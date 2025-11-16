package rpg.ai;

import rpg.core.Character;
import rpg.events.EventType;
import rpg.events.GameEventManager;

/**
 * IA Mágica - Gestiona el maná inteligentemente.
 * 
 * COMPORTAMIENTO:
 * - Usa ataques mágicos mientras tenga maná
 * - Cuando se queda sin maná, ataca normalmente
 * - Si HP < 30%, se defiende para recuperarse
 * 
 * IDEAL PARA:
 * - Magos, hechiceros, brujos
 * - Dragones (ataques mágicos y físicos)
 * - Enemigos con poderes arcanos
 * 
 * ESTRATEGIA:
 * Maximiza el daño mágico temprano, conserva HP para durar más
 */
public class MageAI implements EnemyAI {
    
    @Override
    public void takeTurn(Character enemy, Character target) {
        double hpPercent = (double) enemy.getCurrentHp() / enemy.getMaxHp();
        
        // Si HP es muy bajo, defiende para sobrevivir
        if (hpPercent < 0.30) {
            String message = enemy.getName() + " se concentra en una barrera mágica protectora!";
            System.out.println(message);
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, message);
            enemy.defend();
            return;
        }
        
        // Intenta atacar (usará magia si tiene, físico si no)
        int initialHp = target.getCurrentHp();
        enemy.attack(target);
        int damage = initialHp - target.getCurrentHp();
        
        if (damage > 0) {
            String attackMsg = enemy.getName() + " ataca por " + damage + " de daño!";
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, attackMsg);
        }
    }
}

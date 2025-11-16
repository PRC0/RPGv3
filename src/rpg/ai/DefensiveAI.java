package rpg.ai;

import rpg.core.Character;
import rpg.events.EventType;
import rpg.events.GameEventManager;

/**
 * IA defensiva - Prioriza la supervivencia sobre el daño.
 * 
 * COMPORTAMIENTO:
 * - Alterna entre atacar y defenderse
 * - HP > 50%: Defiende 1 de cada 2 turnos
 * - HP < 50%: Defiende 2 de cada 3 turnos (más cauteloso)
 * 
 * IDEAL PARA:
 * - Tanques enemigos de alta resistencia
 * - Guardias o centinelas
 * - Enemigos que protegen a otros
 * 
 * ESTRATEGIA:
 * Dificulta las batallas rápidas, prolonga el combate
 */
public class DefensiveAI implements EnemyAI {
    
    private boolean shouldDefend = false;
    
    @Override
    public void takeTurn(Character enemy, Character target) {
        // Si tiene menos del 50% HP, se defiende más seguido
        double hpPercent = (double) enemy.getCurrentHp() / enemy.getMaxHp();
        
        if (hpPercent < 0.5) {
            // Bajo HP: defiende 2 de cada 3 turnos
            if (shouldDefend || Math.random() < 0.66) {
                String message = enemy.getName() + " toma una postura defensiva cautelosa!";
                System.out.println(message);
                GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, message);
                enemy.defend();
                shouldDefend = false;
            } else {
                int initialHp = target.getCurrentHp();
                enemy.attack(target);
                int damage = initialHp - target.getCurrentHp();
                
                String attackMsg = enemy.getName() + " ataca cautelosamente por " + damage + " de daño!";
                GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, attackMsg);
                shouldDefend = true;
            }
        } else {
            // HP normal: alterna ataque y defensa
            if (shouldDefend) {
                String message = enemy.getName() + " se prepara para defenderse!";
                System.out.println(message);
                GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, message);
                enemy.defend();
                shouldDefend = false;
            } else {
                int initialHp = target.getCurrentHp();
                enemy.attack(target);
                int damage = initialHp - target.getCurrentHp();
                
                String attackMsg = enemy.getName() + " ataca por " + damage + " de daño!";
                GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, attackMsg);
                shouldDefend = true;
            }
        }
    }
}

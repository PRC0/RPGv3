package rpg.ai;

import rpg.core.Character;
import rpg.events.EventType;
import rpg.events.GameEventManager;

/**
 * IA agresiva - Maximiza el daño infligido.
 * 
 * COMPORTAMIENTO:
 * - Ataca constantemente sin defenderse
 * - Usa habilidad especial cada 3 turnos
 * - Ignora su propia supervivencia
 * 
 * IDEAL PARA:
 * - Enemigos berserker o fanáticos
 * - Bosses en fase de furia
 * - Enemigos de bajo nivel que mueren rápido
 * 
 * DEBILIDAD:
 * Vulnerable a contraataques, no se defiende nunca
 */
public class AggressiveAI implements EnemyAI {
    
    private int turnCounter = 0;
    
    @Override
    public void takeTurn(Character enemy, Character target) {
        turnCounter++;
        
        // Cada 3 turnos usa habilidad especial
        if (turnCounter % 3 == 0) {
            String message = enemy.getName() + " entra en modo FURIA!";
            System.out.println(message);
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, message);
            
            int initialHp = target.getCurrentHp();
            enemy.specialAbility(target);
            int damage = initialHp - target.getCurrentHp();
            
            String damageMsg = enemy.getName() + " usa [GOLPE BRUTAL] por " + damage + " de daño!";
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, damageMsg);
        } else {
            // Ataque normal
            int initialHp = target.getCurrentHp();
            enemy.attack(target);
            int damage = initialHp - target.getCurrentHp();
            
            String attackMsg = enemy.getName() + " ataca ferozmente por " + damage + " de daño!";
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, attackMsg);
        }
    }
}

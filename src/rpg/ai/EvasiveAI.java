package rpg.ai;

import rpg.core.Character;
import rpg.events.EventType;
import rpg.events.GameEventManager;

/**
 * IA evasiva - Comportamiento impredecible y errático.
 * 
 * COMPORTAMIENTO (aleatorio cada turno):
 * - 50% probabilidad: Ataque normal
 * - 30% probabilidad: Defensa/evasión
 * - 20% probabilidad: Habilidad especial sorpresa
 * 
 * IDEAL PARA:
 * - Ladrones, asesinos o ninjas
 * - Enemigos mágicos caóticos
 * - Crear batallas dinámicas e impredecibles
 * 
 * TÁCTICA:
 * Dificulta la planificación del jugador, cada turno es una sorpresa
 */
public class EvasiveAI implements EnemyAI {
    
    @Override
    public void takeTurn(Character enemy, Character target) {
        double roll = Math.random();
        
        // 50% ataque normal
        // 30% defensa
        // 20% habilidad especial
        
        if (roll < 0.50) {
            int initialHp = target.getCurrentHp();
            enemy.attack(target);
            int damage = initialHp - target.getCurrentHp();
            
            String attackMsg = enemy.getName() + " ataca velozmente por " + damage + " de daño!";
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, attackMsg);
        } else if (roll < 0.80) {
            String message = enemy.getName() + " esquiva ágilmente!";
            System.out.println(message);
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, message);
            enemy.defend();
        } else {
            String message = enemy.getName() + " realiza un movimiento inesperado!";
            System.out.println(message);
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, message);
            
            int initialHp = target.getCurrentHp();
            enemy.specialAbility(target);
            int damage = initialHp - target.getCurrentHp();
            
            String damageMsg = enemy.getName() + " te sorprende por " + damage + " de daño!";
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, damageMsg);
        }
    }
}

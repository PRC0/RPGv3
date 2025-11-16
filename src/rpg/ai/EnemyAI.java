package rpg.ai;

import rpg.core.Character;

/**
 * Interfaz para estrategias de IA enemiga - Patrón Strategy (GoF).
 * 
 * PROPÓSITO:
 * - Define comportamientos intercambiables para enemigos en combate
 * - Permite cambiar la táctica del enemigo en tiempo de ejecución
 * - Encapsula algoritmos de decisión de combate
 * 
 * IMPLEMENTACIONES:
 * - AggressiveAI: Ataca constantemente con habilidades especiales
 * - DefensiveAI: Alterna entre ataque y defensa según HP
 * - EvasiveAI: Comportamiento impredecible y aleatorio
 * 
 * @see rpg.combat.AttackStrategy
 */
public interface EnemyAI {
    /**
     * Decide y ejecuta la acción del enemigo en su turno.
     * @param enemy El enemigo que realiza la acción
     * @param target El objetivo del enemigo (normalmente el jugador)
     */
    void takeTurn(Character enemy, Character target);
}

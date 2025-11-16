package rpg.combat;

import java.io.Serializable;
import rpg.core.Character;

/**
 * Estrategia de ataque - Patrón Strategy (GoF).
 * 
 * PROPÓSITO:
 * - Encapsula diferentes algoritmos de ataque
 * - Permite cambiar el comportamiento de ataque en tiempo de ejecución
 * - Cada personaje puede tener su propia estrategia
 * 
 * IMPLEMENTACIONES:
 * - MeleeAttack: Ataque cuerpo a cuerpo (usa baseAttack)
 * - MagicAttack: Ataque mágico que consume maná (usa baseMagic)
 * - RangedAttack: Ataque a distancia con flechas (usa baseAttack)
 * - PassiveStrategy: No ataca (para NPCs no combatientes)
 * 
 * USO:
 * character.setAttackStrategy(new MagicAttack());
 * character.attack(enemy); // Ejecuta la estrategia configurada
 * 
 * SERIALIZABLE:
 * Extiende Serializable para que Character pueda ser guardado con su estrategia
 * 
 * @see rpg.ai.EnemyAI (estrategias de IA, concepto similar)
 */
public interface AttackStrategy extends Serializable {
    void execute(Character attacker, Character target); 
}
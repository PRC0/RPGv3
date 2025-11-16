// Archivo: rpg/factory/EnemyType.java
package rpg.factory;

/**
 * Enumeración de tipos de enemigos disponibles.
 * Cada tipo define un perfil único de stats y comportamiento.
 */
public enum EnemyType {
    SLIME,          // Débil, melee
    GOBLIN,         // Balanceado, melee
    SKELETON,       // Arquero, ranged
    ORC,            // Fuerte, alta defensa
    DARK_MAGE,      // Mágico, alto daño
    WOLF,           // Rápido, ataque melee
    DRAGON_WHELP,   // Jefe menor, stats altos
    BANDIT          // Humanoide, balanceado
}
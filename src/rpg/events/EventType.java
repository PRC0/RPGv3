package rpg.events;

/**
 * Define los tipos de eventos que el GameEventManager puede disparar.
 */
public enum EventType {
    PLAYER_HP_CHANGED,  // La vida del jugador cambió
    PLAYER_MANA_CHANGED, // El maná del jugador cambió
    PLAYER_LEVELED_UP,  // El jugador subió de nivel
    PLAYER_DIED,        // El jugador ha muerto (HP <= 0)
    ENEMY_DEFEATED,     // Un enemigo fue derrotado
    QUEST_COMPLETED,    // Una misión fue completada
    NEW_MESSAGE_LOGGED  // Un nuevo mensaje para la consola
}
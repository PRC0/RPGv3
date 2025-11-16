package rpg.events;

/**
 * La interfaz 'Observer'. 
 * Cualquier clase que quiera escuchar eventos del juego debe implementar esto.
 */
public interface GameEventListener {
    
    /**
     * El método que será llamado por el GameEventManager cuando ocurra un evento.
     * @param type El tipo de evento que ocurrió.
     * @param data El objeto que contiene los datos del evento (ej. el jugador).
     */
    void onGameEvent(EventType type, Object data);
}
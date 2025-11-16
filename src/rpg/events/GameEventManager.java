package rpg.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestor central de eventos - Patrones Observer + Singleton (GoF).
 * 
 * PROPÓSITO:
 * - Implementa comunicación desacoplada entre subsistemas
 * - Permite que la GUI reaccione a cambios del modelo sin dependencias directas
 * - Gestiona múltiples tipos de eventos simultáneamente
 * 
 * ARQUITECTURA:
 * Subject (Observable) ← observado por → Observers (GameEventListener)
 * 
 * FLUJO:
 * 1. GUI se suscribe: GameEventManager.getInstance().subscribe(EventType.PLAYER_HP_CHANGED, guiPanel)
 * 2. Backend notifica: GameEventManager.getInstance().notify(EventType.PLAYER_HP_CHANGED, player)
 * 3. GUI actualiza: guiPanel.onGameEvent() recibe la notificación
 * 
 * EVENTOS SOPORTADOS:
 * - PLAYER_HP_CHANGED: HP del jugador cambió
 * - PLAYER_MANA_CHANGED: Maná cambió
 * - PLAYER_LEVELED_UP: Subió de nivel
 * - ENEMY_DEFEATED: Enemigo derrotado
 * - QUEST_COMPLETED: Misión completada
 * - NEW_MESSAGE_LOGGED: Nuevo mensaje para log
 * 
 * @see GameEventListener
 * @see EventType
 */
public class GameEventManager {

    // --- Implementación del Singleton ---
    private static GameEventManager instance;

    /**
     * Método estático para obtener la única instancia del manager.
     */
    public static GameEventManager getInstance() {
        if (instance == null) {
            instance = new GameEventManager();
        }
        return instance;
    }
    // --- Fin del Singleton ---

    // Un mapa que guarda una lista de listeners para cada tipo de evento
    private Map<EventType, List<GameEventListener>> listeners;

    // El constructor es privado para el Singleton
    private GameEventManager() {
        this.listeners = new HashMap<>();
        // Inicializa una lista vacía para cada tipo de evento
        for (EventType type : EventType.values()) {
            this.listeners.put(type, new ArrayList<>());
        }
    }

    /**
     * Método para que un 'Observer' (ej. la GUI) se suscriba a un evento.
     * @param type El tipo de evento al que se suscribe.
     * @param listener El objeto que escucha.
     */
    public void subscribe(EventType type, GameEventListener listener) {
        this.listeners.get(type).add(listener);
    }

    /**
     * Método para que un 'Observer' se desuscriba.
     */
    public void unsubscribe(EventType type, GameEventListener listener) {
        this.listeners.get(type).remove(listener);
    }

    /**
     * El método clave. ¡Dispara un evento y notifica a todos los suscriptores!
     * @param type El tipo de evento que ocurrió.
     * @param data Los datos del evento (ej. el jugador, un mensaje de string).
     */
    public void notify(EventType type, Object data) {
        // Itera sobre la lista de listeners para ese tipo de evento
        for (GameEventListener listener : this.listeners.get(type)) {
            listener.onGameEvent(type, data);
        }
    }
}
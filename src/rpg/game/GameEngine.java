package rpg.game;

// Importamos todas las piezas que hemos construido
import rpg.core.Character;
import rpg.core.GameFacade; // ¡Importante! Usaremos la Facade
import rpg.factory.CharacterType;
// import rpg.ui.MainGameWindow; // (Esta la crearás después)

/**
 * Clase principal que contiene el método main().
 * Su única responsabilidad es iniciar los sistemas principales
 * (como la Facade y la GUI) y correr el juego.
 */
public class GameEngine {

    /**
     * El punto de entrada principal del RPG.
     */
    public static void main(String[] args) {
        
        // 1. Obtenemos la instancia del GameFacade (Singleton)
        // La Facade es la que realmente "es" el motor del juego.
        GameFacade facade = GameFacade.getInstance();

        // 2. --- ¡Aquí es donde vivirá tu GUI! ---
        // Cuando tengas tu GUI de Swing, aquí es donde la crearás
        // y la harás visible.
        /*
        try {
            // Un LookAndFeel simple para que no se vea tan feo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Creas la ventana principal y le pasas la Facade
        MainGameWindow gameWindow = new MainGameWindow(facade);
        gameWindow.setVisible(true);
        */


        // 3. --- Prueba de Consola (MIENTRAS NO HAY GUI) ---
        // Como aún no tenemos GUI, vamos a correr una prueba
        // para ver que todo funcione.
        System.out.println("--- INICIANDO PRUEBA DE CONSOLA ---");
        runConsoleTest(facade);
        System.out.println("--- PRUEBA DE CONSOLA TERMINADA ---");
    }
    
    /**
     * Un método simple de prueba para verificar que todo el backend funciona
     * antes de construir la GUI.
     */
    public static void runConsoleTest(GameFacade facade) {
        
        // 1. Iniciar un juego nuevo
        facade.startNewGame(CharacterType.WARRIOR, "Gnos");
        
        // 2. Obtener al jugador (para ver sus stats)
        Character player = facade.getPlayer();
        System.out.println("Jugador creado: " + player.getName());
        System.out.println("HP Inicial: " + player.getCurrentHp() + "/" + player.getMaxHp());
        
        // 3. Simular una batalla
        System.out.println("\n--- ¡Comienza una batalla de prueba! ---");
        facade.startBattle(); // La Facade se encarga de crear al enemigo y al BattleManager
        
        // 4. Revisar el resultado
        System.out.println("\n--- Batalla terminada ---");
        System.out.println("HP final del jugador: " + player.getCurrentHp() + "/" + player.getMaxHp());
        
        if (!player.isAlive()) {
            System.out.println("¡Has sido derrotado!");
        } else {
            System.out.println("¡Victoria!");
        }
    }
}
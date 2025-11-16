package rpg.ui;

import javax.swing.*;
import java.awt.*;
import rpg.core.GameFacade;
import rpg.core.Character;
import rpg.factory.CharacterType;
import rpg.events.GameEventListener;
import rpg.events.EventType;
import rpg.events.GameEventManager;

/**
 * PROPÓSITO:
 * Ventana principal del juego que gestiona el flujo completo:
 * TitleScreen → ClassSelectionScreen → MainGame → GameOverScreen
 * 
 * ARQUITECTURA MEJORADA:
 * - Usa CardLayout para cambiar entre pantallas de estado del juego
 * - Cada panel del juego se suscribe a sus propios eventos (Observer desacoplado)
 * - Escucha PLAYER_DIED para mostrar pantalla de Game Over
 * 
 * FLUJO:
 * 1. TitleScreen (inicio)
 * 2. ClassSelectionScreen (selección de clase)
 * 3. MainGame (juego principal con todos los paneles)
 * 4. GameOverScreen (cuando el jugador muere)
 * 
 * @author RPGv3 Team
 */
public class MainGameWindow extends JFrame implements GameEventListener {
    private static final long serialVersionUID = 1L;
    
    // Facade para comunicación con el backend
    private GameFacade gameFacade;
    
    // CardLayout principal para cambiar entre estados del juego
    private CardLayout mainCardLayout;
    private JPanel mainContainer;
    
    // Pantallas principales
    private TitleScreen titleScreen;
    private ClassSelectionScreen classSelectionScreen;
    private JPanel gamePanel;  // Panel que contiene el juego completo
    private GameOverScreen gameOverScreen;
    
    // Paneles del juego (cuando está en estado GAME)
    private TopPanel topPanel;
    private LeftMenuPanel leftMenuPanel;
    private RightStatsPanel rightStatsPanel;
    private BottomLogPanel bottomLogPanel;
    
    // Panel central con CardLayout para intercambiar vistas del juego
    private JPanel centerPanel;
    private CardLayout gameCenterLayout;
    
    // Paneles dinámicos del centro del juego
    private ExplorePanel explorePanel;
    private InventoryPanel inventoryPanel;
    private QuestPanel questPanel;
    private StatsPanel statsPanel;
    private BattlePanel battlePanel;
    
    /**
     * Constructor principal de la ventana.
     * Inicializa el flujo completo del juego comenzando con la pantalla de título.
     */
    public MainGameWindow() {
        // Configurar tooltips globalmente
        configureTooltips();
        
        // Configuración de la ventana
        setTitle("RPGv3 - Sistema de Combate por Turnos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null); // Centrar en pantalla
        
        // Obtener instancia del facade
        gameFacade = GameFacade.getInstance();
        
        // Suscribirse al evento PLAYER_DIED
        GameEventManager.getInstance().subscribe(EventType.PLAYER_DIED, this);
        
        // Configurar CardLayout principal
        mainCardLayout = new CardLayout();
        mainContainer = new JPanel(mainCardLayout);
        
        // Inicializar pantallas
        initializeScreens();
        
        // Agregar container principal
        add(mainContainer);
        
        // Mostrar pantalla de título
        showTitleScreen();
    }
    
    /**
     * Inicializa todas las pantallas del juego.
     */
    private void initializeScreens() {
        // Pantalla de título
        titleScreen = new TitleScreen(new TitleScreen.TitleScreenListener() {
            @Override
            public void onNewGame() {
                showClassSelection();
            }
            
            @Override
            public void onLoadGame() {
                loadGame();
            }
            
            @Override
            public void onExit() {
                System.exit(0);
            }
        });
        
        // Pantalla de selección de clase
        classSelectionScreen = new ClassSelectionScreen(new ClassSelectionScreen.ClassSelectionListener() {
            @Override
            public void onClassSelected(CharacterType characterType, String playerName) {
                startNewGame(characterType, playerName);
            }
            
            @Override
            public void onCancel() {
                showTitleScreen();
            }
        });
        
        // Panel del juego principal
        gamePanel = createGamePanel();
        
        // Agregar todas las pantallas al CardLayout
        mainContainer.add(titleScreen, "TITLE");
        mainContainer.add(classSelectionScreen, "CLASS_SELECTION");
        mainContainer.add(gamePanel, "GAME");
    }
    
    /**
     * Crea el panel del juego principal con todos sus componentes.
     */
    private JPanel createGamePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        
        // Panel superior (HP/MP/Stats)
        topPanel = new TopPanel();
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Panel izquierdo (Menú de navegación)
        leftMenuPanel = new LeftMenuPanel(this);
        panel.add(leftMenuPanel, BorderLayout.WEST);
        
        // Panel derecho (Detalles del personaje)
        rightStatsPanel = new RightStatsPanel();
        panel.add(rightStatsPanel, BorderLayout.EAST);
        
        // Panel inferior (Log de eventos)
        bottomLogPanel = new BottomLogPanel();
        panel.add(bottomLogPanel, BorderLayout.SOUTH);
        
        // Panel central con CardLayout
        gameCenterLayout = new CardLayout();
        centerPanel = new JPanel(gameCenterLayout);
        centerPanel.setBackground(new Color(240, 240, 240));
        
        // Inicializar paneles dinámicos
        explorePanel = new ExplorePanel(this);
        inventoryPanel = new InventoryPanel();
        questPanel = new QuestPanel();
        statsPanel = new StatsPanel();
        battlePanel = new BattlePanel(this);
        
        // Agregar paneles al CardLayout
        centerPanel.add(explorePanel, "EXPLORE");
        centerPanel.add(inventoryPanel, "INVENTORY");
        centerPanel.add(questPanel, "QUEST");
        centerPanel.add(statsPanel, "STATS");
        centerPanel.add(battlePanel, "BATTLE");
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Muestra la pantalla de título.
     */
    private void showTitleScreen() {
        mainCardLayout.show(mainContainer, "TITLE");
    }
    
    /**
     * Muestra la pantalla de selección de clase.
     */
    private void showClassSelection() {
        mainCardLayout.show(mainContainer, "CLASS_SELECTION");
    }
    
    /**
     * Inicia un nuevo juego con la clase y nombre seleccionados.
     */
    private void startNewGame(CharacterType characterType, String playerName) {
        gameFacade.startNewGame(characterType, playerName);
        refreshAllPanels();
        logMessage("¡Bienvenido, " + playerName + " el " + characterType + "!");
        showGameScreen();
    }
    
    /**
     * Muestra la pantalla del juego principal.
     */
    private void showGameScreen() {
        mainCardLayout.show(mainContainer, "GAME");
        showPanel("EXPLORE");
    }
    
    /**
     * Muestra la pantalla de Game Over.
     */
    private void showGameOver() {
        Character player = gameFacade.getPlayer();
        
        gameOverScreen = new GameOverScreen(new GameOverScreen.GameOverListener() {
            @Override
            public void onRetry() {
                showClassSelection();
            }
            
            @Override
            public void onMainMenu() {
                showTitleScreen();
            }
            
            @Override
            public void onExit() {
                System.exit(0);
            }
        }, player);
        
        // Agregar y mostrar
        mainContainer.add(gameOverScreen, "GAME_OVER");
        mainCardLayout.show(mainContainer, "GAME_OVER");
    }
    
    /**
     * Maneja el evento PLAYER_DIED.
     */
    @Override
    public void onGameEvent(EventType eventType, Object data) {
        if (eventType == EventType.PLAYER_DIED) {
            SwingUtilities.invokeLater(() -> {
                // Pequeño delay para que se vea el último mensaje
                Timer timer = new Timer(1500, e -> showGameOver());
                timer.setRepeats(false);
                timer.start();
            });
        }
    }
    
    /**
     * Configura tooltips globalmente con mejor visibilidad.
     */
    private void configureTooltips() {
        // Configurar el tooltip manager para mejor visibilidad
        UIManager.put("ToolTip.background", new Color(255, 255, 220)); // Amarillo claro
        UIManager.put("ToolTip.foreground", Color.BLACK); // Texto negro
        UIManager.put("ToolTip.font", new Font("Segoe UI", Font.BOLD, 14)); // Fuente más grande
        UIManager.put("ToolTip.border", BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Configurar tiempo de aparición y duración
        ToolTipManager.sharedInstance().setInitialDelay(300);  // 300ms para aparecer
        ToolTipManager.sharedInstance().setDismissDelay(10000); // 10 segundos visible
        ToolTipManager.sharedInstance().setReshowDelay(100);    // 100ms para reaparecer
    }
    
    /**
     * Cambia el panel visible en el centro de la ventana del juego.
     * @param panelName Nombre del panel a mostrar
     */
    public void showPanel(String panelName) {
        gameCenterLayout.show(centerPanel, panelName);
        
        // Refrescar el panel mostrado
        switch(panelName) {
            case "EXPLORE":
                explorePanel.refresh();
                break;
            case "INVENTORY":
                inventoryPanel.refresh();
                break;
            case "QUEST":
                questPanel.refresh();
                break;
            case "STATS":
                statsPanel.refresh();
                break;
            case "BATTLE":
                battlePanel.refresh();
                break;
        }
    }
    
    /**
     * Inicia una batalla y cambia al panel de combate.
     */
    public void startBattle() {
        gameFacade.startBattle();
        showPanel("BATTLE");
    }
    
    /**
     * Guarda el juego actual usando el sistema de slots.
     */
    public void saveGame() {
        SaveLoadDialog dialog = new SaveLoadDialog(this, gameFacade.getSaveManager(), true);
        int slot = dialog.showDialog();
        
        if (slot != -1) {
            String saveName = JOptionPane.showInputDialog(
                this,
                "Nombre de la partida:",
                "Mi Partida"
            );
            
            if (saveName != null && !saveName.trim().isEmpty()) {
                boolean success = gameFacade.saveGameToSlot(slot, saveName);
                if (success) {
                    logMessage("✓ Partida guardada en Slot " + slot);
                    JOptionPane.showMessageDialog(this, "Partida guardada exitosamente en Slot " + slot);
                } else {
                    logMessage("✗ Error al guardar");
                    JOptionPane.showMessageDialog(this, "Error al guardar la partida", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    /**
     * Carga una partida guardada (método público para LeftMenuPanel y TitleScreen).
     */
    public void loadGame() {
        SaveLoadDialog dialog = new SaveLoadDialog(this, gameFacade.getSaveManager(), false);
        int slot = dialog.showDialog();
        
        if (slot != -1) {
            boolean success = gameFacade.loadGameFromSlot(slot);
            if (success) {
                logMessage("✓ Partida cargada desde Slot " + slot);
                refreshAllPanels();
                showGameScreen();  // Ir directamente al juego
                JOptionPane.showMessageDialog(this, "Partida cargada exitosamente");
            } else {
                logMessage("✗ Error al cargar");
                JOptionPane.showMessageDialog(this, "Error al cargar la partida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Refresca todos los paneles con datos actualizados.
     */
    public void refreshAllPanels() {
        topPanel.refresh();
        rightStatsPanel.refresh();
        explorePanel.refresh();
        inventoryPanel.refresh();
        questPanel.refresh();
        statsPanel.refresh();
        battlePanel.refresh();
    }
    
    /**
     * Agrega un mensaje al log de eventos.
     * @param message Mensaje a mostrar
     */
    public void logMessage(String message) {
        bottomLogPanel.addLog(message);
    }
    
    /**
     * Obtiene la instancia del GameFacade.
     * @return GameFacade instance
     */
    public GameFacade getGameFacade() {
        return gameFacade;
    }
    
    /**
     * Punto de entrada principal para ejecutar la GUI.
     */
    public static void main(String[] args) {
        // Usar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Crear y mostrar la ventana en el EDT
        SwingUtilities.invokeLater(() -> {
            MainGameWindow window = new MainGameWindow();
            window.setVisible(true);
        });
    }
}

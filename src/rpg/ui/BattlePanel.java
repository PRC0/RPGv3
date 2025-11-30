package rpg.ui;

import javax.swing.*;
import java.awt.*;
import rpg.core.GameFacade;
import rpg.core.Character;
import rpg.core.Enemy;
import rpg.core.GameConstants;
import rpg.events.GameEventListener;
import rpg.events.GameEventManager;
import rpg.events.EventType;
import rpg.ui.theme.UITheme;
import rpg.ui.components.ModernButton;

/**
 * Panel de batalla mejorado con UI moderna.
 */
public class BattlePanel extends JPanel implements GameEventListener {
    private static final long serialVersionUID = 1L;
    
    private MainGameWindow mainWindow;
    private JTextArea battleLogArea;
    private JLabel playerHpLabel;
    private JLabel enemyHpLabel;
    private JProgressBar playerHpBar;
    private JProgressBar enemyHpBar;
    
    // Botones modernos
    private ModernButton attackBtn;
    private ModernButton specialBtn;
    private ModernButton defendBtn;
    private ModernButton fleeBtn;
    
    public BattlePanel(MainGameWindow mainWindow) {
        this.mainWindow = mainWindow;
        
        setLayout(new BorderLayout(15, 15));
        setBackground(UITheme.SECONDARY_DARK);
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Suscribirse a eventos de batalla
        GameEventManager.getInstance().subscribe(EventType.NEW_MESSAGE_LOGGED, this);
        
        // Panel superior: Información de combatientes
        JPanel combatantsPanel = createCombatantsPanel();
        
        // Panel central: Log de batalla
        battleLogArea = new JTextArea();
        battleLogArea.setEditable(false);
        battleLogArea.setFont(UITheme.FONT_MONOSPACE);
        battleLogArea.setBackground(UITheme.PRIMARY_DARK);
        battleLogArea.setForeground(UITheme.TEXT_PRIMARY);
        battleLogArea.setLineWrap(true);
        battleLogArea.setWrapStyleWord(true);
        battleLogArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(battleLogArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 63, 65), 1));
        scrollPane.getViewport().setBackground(UITheme.PRIMARY_DARK);
        
        // Panel inferior: Botones de acción
        JPanel actionPanel = createActionPanel();
        
        add(combatantsPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
        
        refresh();
    }
    
    private JLabel playerImageLabel;
    private JLabel enemyImageLabel;

    private JPanel createCombatantsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 30, 0));
        panel.setOpaque(false);
        
        // Info del jugador
        JPanel playerPanel = createStatCard("Jugador", UITheme.ACCENT_GREEN);
        
        // Imagen del jugador
        playerImageLabel = new JLabel();
        playerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updatePlayerImage(); // Cargar imagen
        
        playerHpLabel = new JLabel("HP: 100/100");
        playerHpLabel.setForeground(UITheme.TEXT_PRIMARY);
        playerHpLabel.setFont(UITheme.FONT_BODY_BOLD);
        playerHpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        playerHpBar = createStyledProgressBar(UITheme.ACCENT_GREEN);
        
        playerPanel.add(playerImageLabel);
        playerPanel.add(Box.createVerticalStrut(10));
        playerPanel.add(playerHpLabel);
        playerPanel.add(Box.createVerticalStrut(5));
        playerPanel.add(playerHpBar);
        
        // Info del enemigo
        JPanel enemyPanel = createStatCard("Enemigo", UITheme.ACCENT_RED);
        
        // Imagen del enemigo
        enemyImageLabel = new JLabel();
        enemyImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        enemyHpLabel = new JLabel("HP: 50/50");
        enemyHpLabel.setForeground(UITheme.TEXT_PRIMARY);
        enemyHpLabel.setFont(UITheme.FONT_BODY_BOLD);
        enemyHpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        enemyHpBar = createStyledProgressBar(UITheme.ACCENT_RED);
        
        enemyPanel.add(enemyImageLabel);
        enemyPanel.add(Box.createVerticalStrut(10));
        enemyPanel.add(enemyHpLabel);
        enemyPanel.add(Box.createVerticalStrut(5));
        enemyPanel.add(enemyHpBar);
        
        panel.add(playerPanel);
        panel.add(enemyPanel);
        
        return panel;
    }
    
    private void updatePlayerImage() {
        Character player = GameFacade.getInstance().getPlayer();
        String imageName = "hero.png"; // Default Warrior
        
        if (player != null) {
            String className = player.getClass().getSimpleName().toLowerCase();
            if (className.contains("mage")) {
                imageName = "mage.png";
            } else if (className.contains("archer")) {
                imageName = "archer.png";
            } else if (className.contains("priest")) {
                imageName = "priest.png";
            }
        }
        
        ImageIcon icon = loadIcon(imageName);
        if (icon != null) {
            playerImageLabel.setIcon(icon);
        } else {
            playerImageLabel.setText("[HERO]");
        }
    }
    
    private void updateEnemyImage(Enemy enemy) {
        if (enemy == null) {
            enemyImageLabel.setIcon(null);
            return;
        }
        
        String imageName = null;
        String name = enemy.getName().toLowerCase();
        
        if (name.contains("goblin")) {
            imageName = "goblin.png";
        } else if (name.contains("slime")) {
            imageName = "slime.png";
        } else if (name.contains("wolf")) {
            imageName = "wolf.png";
        }
        
        if (imageName != null) {
            ImageIcon icon = loadIcon(imageName);
            if (icon != null) {
                enemyImageLabel.setIcon(icon);
                enemyImageLabel.setText(""); // Clear text if image loads
            } else {
                enemyImageLabel.setIcon(null);
                enemyImageLabel.setText("[" + enemy.getName() + "]");
            }
        } else {
            enemyImageLabel.setIcon(null);
            enemyImageLabel.setText("[" + enemy.getName() + "]");
        }
    }
    
    private ImageIcon loadIcon(String filename) {
        try {
            String path = "assets/images/" + filename;
            ImageIcon icon = new ImageIcon(path);
            // Escalar si es necesario (ej. 128x128)
            Image img = icon.getImage();
            Image newImg = img.getScaledInstance(128, 128,  java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(newImg);
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + filename);
            return null;
        }
    }
    
    private JPanel createStatCard(String title, Color accentColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UITheme.PRIMARY_DARK);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, accentColor),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UITheme.FONT_SUBTITLE);
        titleLabel.setForeground(accentColor);
        panel.add(titleLabel);
        
        return panel;
    }
    
    private JProgressBar createStyledProgressBar(Color color) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(100);
        bar.setStringPainted(true);
        bar.setForeground(color);
        bar.setBackground(new Color(60, 63, 65));
        bar.setBorderPainted(false);
        bar.setPreferredSize(new Dimension(100, 20));
        return bar;
    }
    
    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setOpaque(false);
        
        attackBtn = new ModernButton("Atacar", UITheme.ACCENT_RED);
        attackBtn.setToolTipText("Ataque básico con tu arma");
        attackBtn.addActionListener(e -> performAttack());
        
        specialBtn = new ModernButton("Especial", UITheme.ACCENT_BLUE);
        specialBtn.setToolTipText("Habilidad especial (20 MP)");
        specialBtn.addActionListener(e -> performSpecial());
        
        defendBtn = new ModernButton("Defender", UITheme.ACCENT_GREEN);
        defendBtn.setToolTipText("Reducir daño recibido");
        defendBtn.addActionListener(e -> performDefend());
        
        fleeBtn = new ModernButton("Huir", UITheme.ACCENT_GOLD);
        fleeBtn.setToolTipText("Intentar escapar (50%)");
        fleeBtn.addActionListener(e -> flee());
        
        panel.add(attackBtn);
        panel.add(specialBtn);
        panel.add(defendBtn);
        panel.add(fleeBtn);
        
        return panel;
    }
    
    public void refresh() {
        Character player = GameFacade.getInstance().getPlayer();
        
        if (player != null) {
            playerHpLabel.setText("HP: " + player.getCurrentHp() + "/" + player.getMaxHp());
            playerHpBar.setMaximum(player.getMaxHp());
            playerHpBar.setValue(player.getCurrentHp());
        }
        
        // Verificar si hay batalla activa
        if (GameFacade.getInstance().getBattleManager() != null) {
            Enemy currentEnemy = GameFacade.getInstance().getBattleManager().getCurrentEnemy();
            if (currentEnemy != null) {
                enemyHpLabel.setText(currentEnemy.getName() + " HP: " + currentEnemy.getCurrentHp() + "/" + currentEnemy.getMaxHp());
                enemyHpBar.setMaximum(currentEnemy.getMaxHp());
                enemyHpBar.setValue(currentEnemy.getCurrentHp());
                updateEnemyImage(currentEnemy);
            } else {
                enemyHpLabel.setText("Sin enemigo");
                enemyHpBar.setValue(0);
                enemyImageLabel.setIcon(null);
            }
        } else {
            enemyHpLabel.setText("Sin batalla activa");
            enemyHpBar.setValue(0);
        }
    }
    
    private void performAttack() {
        appendBattleLog("\n--- Turno del héroe ---");
        GameFacade.getInstance().playerAttack();
        processTurn();
    }
    
    private void performSpecial() {
        appendBattleLog("\n--- Turno del héroe ---");
        GameFacade.getInstance().playerSpecialAbility();
        processTurn();
    }
    
    private void performDefend() {
        appendBattleLog("\n--- Turno del héroe ---");
        appendBattleLog(">>> Te pones en guardia defensiva");
        processTurn();
    }
    
    private void flee() {
        appendBattleLog(">>> Intentas huir...");
        
        if (Math.random() < GameConstants.FLEE_SUCCESS_RATE) {
            appendBattleLog("¡Escapaste con éxito!");
            mainWindow.showPanel("EXPLORE");
        } else {
            appendBattleLog("¡No pudiste escapar!");
            processTurn();
        }
    }
    
    private void processTurn() {
        refresh();
        
        Enemy currentEnemy = GameFacade.getInstance().getBattleManager().getCurrentEnemy();
        if (currentEnemy == null || !currentEnemy.isAlive()) {
            appendBattleLog("\n=== ¡VICTORIA! ===\n");
            disableButtons();
            
            Timer timer = new Timer(2000, e -> {
                mainWindow.showPanel("EXPLORE");
                enableButtons();
            });
            timer.setRepeats(false);
            timer.start();
            return;
        }
        
        Timer enemyTurnTimer = new Timer(1000, e -> {
            appendBattleLog("\n--- Turno del enemigo ---");
            GameFacade.getInstance().enemyTurn();
            refresh();
            
            Character player = GameFacade.getInstance().getPlayer();
            if (!player.isAlive()) {
                appendBattleLog("\n=== DERROTA ===");
                disableButtons();
                
                Timer defeatTimer = new Timer(2000, evt -> {
                    player.heal(player.getMaxHp());
                    mainWindow.refreshAllPanels();
                    mainWindow.showPanel("EXPLORE");
                    enableButtons();
                });
                defeatTimer.setRepeats(false);
                defeatTimer.start();
            }
        });
        enemyTurnTimer.setRepeats(false);
        enemyTurnTimer.start();
    }
    
    private void appendBattleLog(String message) {
        battleLogArea.append(message + "\n");
        battleLogArea.setCaretPosition(battleLogArea.getDocument().getLength());
    }
    
    private void disableButtons() {
        attackBtn.setEnabled(false);
        specialBtn.setEnabled(false);
        defendBtn.setEnabled(false);
        fleeBtn.setEnabled(false);
    }
    
    private void enableButtons() {
        attackBtn.setEnabled(true);
        specialBtn.setEnabled(true);
        defendBtn.setEnabled(true);
        fleeBtn.setEnabled(true);
        battleLogArea.setText(""); 
    }
    
    @Override
    public void onGameEvent(EventType eventType, Object data) {
        if (eventType == EventType.NEW_MESSAGE_LOGGED && data instanceof String) {
            String message = (String) data;
            appendBattleLog(message);
        }
    }
}

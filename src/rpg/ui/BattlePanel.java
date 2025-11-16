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

/**
 * Panel de batalla que muestra el combate por turnos.
 */
public class BattlePanel extends JPanel implements GameEventListener {
    private static final long serialVersionUID = 1L;
    
    private MainGameWindow mainWindow;
    private JTextArea battleLogArea;
    private JLabel playerHpLabel;
    private JLabel enemyHpLabel;
    private JProgressBar playerHpBar;
    private JProgressBar enemyHpBar;
    private JButton attackBtn;
    private JButton specialBtn;
    private JButton defendBtn;
    private JButton fleeBtn;
    
    public BattlePanel(MainGameWindow mainWindow) {
        this.mainWindow = mainWindow;
        
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(40, 40, 45));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Suscribirse a eventos de batalla
        GameEventManager.getInstance().subscribe(EventType.NEW_MESSAGE_LOGGED, this);
        
        // Panel superior: Información de combatientes
        JPanel combatantsPanel = createCombatantsPanel();
        
        // Panel central: Log de batalla
        battleLogArea = new JTextArea();
        battleLogArea.setEditable(false);
        battleLogArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        battleLogArea.setBackground(new Color(44, 62, 80));
        battleLogArea.setForeground(new Color(236, 240, 241));
        battleLogArea.setLineWrap(true);
        battleLogArea.setWrapStyleWord(true);
        battleLogArea.setToolTipText("<html><body style='width: 250px; padding: 8px; background-color: #2C3E50;'>" +
            "<b style='font-size: 13px; color: #ECF0F1;'>📜 HISTORIAL DE BATALLA</b><br>" +
            "<span style='font-size: 11px; color: #BDC3C7;'>Registro detallado de todas<br>las acciones de combate</span>" +
            "</body></html>");
        
        JScrollPane scrollPane = new JScrollPane(battleLogArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 73, 94), 2),
            "Log de Batalla",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(236, 240, 241)
        ));
        
        // Panel inferior: Botones de acción
        JPanel actionPanel = createActionPanel();
        
        add(combatantsPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
        
        refresh();
    }
    
    private JPanel createCombatantsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setOpaque(false);
        
        // Info del jugador
        JPanel playerPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        playerPanel.setBackground(new Color(39, 174, 96));
        playerPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
            "Jugador",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), Color.WHITE
        ));
        
        playerHpLabel = new JLabel("HP: 100/100");
        playerHpLabel.setForeground(Color.WHITE);
        playerHpLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        playerHpBar = new JProgressBar(0, 100);
        playerHpBar.setValue(100);
        playerHpBar.setStringPainted(true);
        playerHpBar.setForeground(new Color(46, 204, 113));
        playerHpBar.setBackground(new Color(70, 70, 70));
        playerHpBar.setBorder(BorderFactory.createLineBorder(new Color(39, 174, 96), 2));
        
        playerPanel.add(new JLabel()); // Espaciador
        playerPanel.add(playerHpLabel);
        playerPanel.add(playerHpBar);
        
        // Info del enemigo
        JPanel enemyPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        enemyPanel.setBackground(new Color(192, 57, 43));
        enemyPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(231, 76, 60), 2),
            "Enemigo",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), Color.WHITE
        ));
        
        enemyHpLabel = new JLabel("HP: 50/50");
        enemyHpLabel.setForeground(Color.WHITE);
        enemyHpLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        enemyHpBar = new JProgressBar(0, 100);
        enemyHpBar.setValue(100);
        enemyHpBar.setStringPainted(true);
        enemyHpBar.setForeground(new Color(231, 76, 60));
        enemyHpBar.setBackground(new Color(70, 70, 70));
        enemyHpBar.setBorder(BorderFactory.createLineBorder(new Color(192, 57, 43), 2));
        
        enemyPanel.add(new JLabel()); // Espaciador
        enemyPanel.add(enemyHpLabel);
        enemyPanel.add(enemyHpBar);
        
        panel.add(playerPanel);
        panel.add(enemyPanel);
        
        return panel;
    }
    
    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setOpaque(false);
        
        attackBtn = createActionButton("Atacar", new Color(231, 76, 60));
        attackBtn.setToolTipText("<html><body style='width: 220px; padding: 8px; background-color: #FFFACD;'>" +
            "<b style='font-size: 13px; color: #C0392B;'>⚔ ATAQUE BASICO</b><br>" +
            "<span style='font-size: 12px; color: #000;'>Ataque normal usando<br>tu arma equipada</span><br>" +
            "<span style='font-size: 10px; color: #666;'>Danio basado en Ataque</span>" +
            "</body></html>");
        attackBtn.addActionListener(e -> performAttack());
        
        specialBtn = createActionButton("Especial", new Color(52, 152, 219));
        specialBtn.setToolTipText("<html><body style='width: 220px; padding: 8px; background-color: #E0F7FF;'>" +
            "<b style='font-size: 13px; color: #2980B9;'>✦ HABILIDAD ESPECIAL</b><br>" +
            "<span style='font-size: 12px; color: #000;'>Usa la habilidad unica<br>de tu clase</span><br>" +
            "<span style='font-size: 10px; color: #C00;'>Costo: 20 MP</span>" +
            "</body></html>");
        specialBtn.addActionListener(e -> performSpecial());
        
        defendBtn = createActionButton("Defender", new Color(39, 174, 96));
        defendBtn.setToolTipText("<html><body style='width: 220px; padding: 8px; background-color: #D4EDDA;'>" +
            "<b style='font-size: 13px; color: #27AE60;'>🛡 DEFENSA</b><br>" +
            "<span style='font-size: 12px; color: #000;'>Reduce el danio recibido<br>en este turno</span><br>" +
            "<span style='font-size: 10px; color: #666;'>Reduccion: 50%</span>" +
            "</body></html>");
        defendBtn.addActionListener(e -> performDefend());
        
        fleeBtn = createActionButton("Huir", new Color(241, 196, 15));
        fleeBtn.setToolTipText("<html><body style='width: 220px; padding: 8px; background-color: #FFF9E6;'>" +
            "<b style='font-size: 13px; color: #D68910;'>🏃 HUIR</b><br>" +
            "<span style='font-size: 12px; color: #000;'>Intenta escapar<br>de la batalla</span><br>" +
            "<span style='font-size: 10px; color: #C00;'>Probabilidad: 50%</span>" +
            "</body></html>");
        fleeBtn.addActionListener(e -> flee());
        
        panel.add(attackBtn);
        panel.add(specialBtn);
        panel.add(defendBtn);
        panel.add(fleeBtn);
        
        return panel;
    }
    
    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        
        // ¡AGREGA ESTO SIEMPRE PARA BOTONES DE COLORES!
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(150, 50));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        Color originalColor = bgColor;
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
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
            } else {
                enemyHpLabel.setText("Sin enemigo");
                enemyHpBar.setValue(0);
            }
        } else {
            enemyHpLabel.setText("Sin batalla activa");
            enemyHpBar.setValue(0);
        }
    }
    
    private void performAttack() {
        appendBattleLog("\n--- Turno del héroe ---");
        // El mensaje ya lo enviará GameFacade vía eventos
        GameFacade.getInstance().playerAttack();
        processTurn();
    }
    
    private void performSpecial() {
        appendBattleLog("\n--- Turno del héroe ---");
        // El mensaje ya lo enviará GameFacade vía eventos
        GameFacade.getInstance().playerSpecialAbility();
        processTurn();
    }
    
    private void performDefend() {
        appendBattleLog("\n--- Turno del héroe ---");
        appendBattleLog(">>> Te pones en guardia defensiva");
        appendBattleLog("Defensa aumentada temporalmente");
        
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
        
        // Verificar si el enemigo murió
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
        
        // Turno del enemigo
        Timer enemyTurnTimer = new Timer(1000, e -> {
            appendBattleLog("\n--- Turno del enemigo ---");
            // El mensaje del ataque enemigo lo enviará GameFacade vía eventos
            GameFacade.getInstance().enemyTurn();
            refresh();
            
            // Verificar si el jugador murió
            Character player = GameFacade.getInstance().getPlayer();
            if (!player.isAlive()) {
                appendBattleLog("\n=== DERROTA ===");
                appendBattleLog("Has sido derrotado...");
                disableButtons();
                
                Timer defeatTimer = new Timer(2000, evt -> {
                    player.heal(player.getMaxHp()); // Revivir
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
        battleLogArea.setText(""); // Limpiar log
    }
    
    @Override
    public void onGameEvent(EventType eventType, Object data) {
        if (eventType == EventType.NEW_MESSAGE_LOGGED && data instanceof String) {
            String message = (String) data;
            appendBattleLog(message);
        }
    }
}

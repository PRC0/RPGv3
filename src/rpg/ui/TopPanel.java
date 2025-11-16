package rpg.ui;

import javax.swing.*;
import java.awt.*;
import rpg.core.Character;
import rpg.core.GameFacade;
import rpg.events.GameEventListener;
import rpg.events.EventType;
import rpg.events.GameEventManager;

/**
 * Panel superior que muestra información vital del jugador.
 * Incluye barras de HP/MP, nivel, oro y nombre del personaje.
 * 
 * RESPONSABILIDAD:
 * - Se suscribe a sus propios eventos (HP_CHANGED, MANA_CHANGED, LEVELED_UP)
 * - Se actualiza automáticamente cuando cambian sus datos relevantes
 * - Componente independiente y reutilizable
 */
public class TopPanel extends JPanel implements GameEventListener {
    private static final long serialVersionUID = 1L;
    
    private JProgressBar hpBar;
    private JProgressBar manaBar;
    private JLabel nameLabel;
    private JLabel levelLabel;
    private JLabel goldLabel;
    private JLabel hpLabel;
    private JLabel manaLabel;
    
    public TopPanel() {
        setLayout(new BorderLayout(10, 5));
        setBackground(new Color(45, 45, 48));
        setPreferredSize(new Dimension(0, 80));
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Panel izquierdo: Nombre y clase
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setOpaque(false);
        
        nameLabel = new JLabel("Hero");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nameLabel.setForeground(new Color(255, 223, 0));
        
        levelLabel = new JLabel("Nivel 1");
        levelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        levelLabel.setForeground(new Color(220, 220, 220));
        
        leftPanel.add(nameLabel);
        leftPanel.add(levelLabel);
        
        // Panel central: Barras de HP/MP
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        centerPanel.setOpaque(false);
        
        hpLabel = new JLabel("HP: 100/100");
        hpLabel.setForeground(Color.WHITE);
        hpLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        hpBar = new JProgressBar(0, 100);
        hpBar.setValue(100);
        hpBar.setStringPainted(true);
        hpBar.setString("");
        hpBar.setForeground(new Color(231, 76, 60));
        hpBar.setBackground(new Color(70, 70, 70));
        hpBar.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        
        manaLabel = new JLabel("MP: 50/50");
        manaLabel.setForeground(Color.WHITE);
        manaLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        manaBar = new JProgressBar(0, 100);
        manaBar.setValue(100);
        manaBar.setStringPainted(true);
        manaBar.setString("");
        manaBar.setForeground(new Color(52, 152, 219));
        manaBar.setBackground(new Color(70, 70, 70));
        manaBar.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        
        centerPanel.add(hpLabel);
        centerPanel.add(hpBar);
        centerPanel.add(manaLabel);
        centerPanel.add(manaBar);
        
        // Panel derecho: Oro
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        
        goldLabel = new JLabel("Oro: 0");
        goldLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        goldLabel.setForeground(new Color(255, 215, 0));
        
        rightPanel.add(goldLabel);
        
        // Agregar todo
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        
        // Suscribirse a eventos relevantes para este panel
        subscribeToEvents();
        
        refresh();
    }
    
    /**
     * Suscribe este panel a los eventos que le interesan.
     * Cada panel es responsable de saber qué eventos necesita escuchar.
     */
    private void subscribeToEvents() {
        GameEventManager eventManager = GameEventManager.getInstance();
        eventManager.subscribe(EventType.PLAYER_HP_CHANGED, this);
        eventManager.subscribe(EventType.PLAYER_MANA_CHANGED, this);
        eventManager.subscribe(EventType.PLAYER_LEVELED_UP, this);
    }
    
    @Override
    public void onGameEvent(EventType eventType, Object data) {
        // Actualizar en el EDT de Swing para thread-safety
        SwingUtilities.invokeLater(() -> {
            refresh();
        });
    }
    
    /**
     * Actualiza la información mostrada con datos del personaje actual.
     */
    public void refresh() {
        Character player = GameFacade.getInstance().getPlayer();
        if (player != null) {
            nameLabel.setText(player.getName() + " (" + player.getClass().getSimpleName() + ")");
            levelLabel.setText("Nivel " + player.getLevel());
            
            int currentHp = player.getCurrentHp();
            int maxHp = player.getMaxHp();
            hpLabel.setText("HP: " + currentHp + "/" + maxHp);
            hpBar.setMaximum(maxHp);
            hpBar.setValue(currentHp);
            
            int currentMana = player.getCurrentMana();
            int maxMana = player.getMaxMana();
            manaLabel.setText("MP: " + currentMana + "/" + maxMana);
            manaBar.setMaximum(maxMana);
            manaBar.setValue(currentMana);
            
            goldLabel.setText("💰 Oro: " + player.getGold());
        }
    }
}

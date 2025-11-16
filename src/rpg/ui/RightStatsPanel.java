package rpg.ui;

import javax.swing.*;
import java.awt.*;
import rpg.core.Character;
import rpg.core.GameFacade;
import rpg.inventory.Equipment;
import rpg.events.GameEventListener;
import rpg.events.EventType;
import rpg.events.GameEventManager;

/**
 * Panel derecho que muestra estadísticas detalladas del personaje.
 * 
 * RESPONSABILIDAD:
 * - Se suscribe a eventos de cambios en stats (HP, LEVELED_UP, etc.)
 * - Se actualiza automáticamente cuando cambian las estadísticas
 * - Componente independiente y reutilizable
 */
public class RightStatsPanel extends JPanel implements GameEventListener {
    private static final long serialVersionUID = 1L;
    
    private JTextArea statsArea;
    
    public RightStatsPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(50, 50, 55));
        setPreferredSize(new Dimension(200, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Personaje");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(236, 240, 241));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        statsArea.setBackground(new Color(44, 62, 80));
        statsArea.setForeground(new Color(236, 240, 241));
        statsArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        statsArea.setLineWrap(true);
        statsArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(statsArea);
        scrollPane.setBorder(null);
        
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        subscribeToEvents();
        refresh();
    }
    
    /**
     * Suscribe este panel a los eventos relevantes.
     * Este panel se interesa en:
     * - PLAYER_HP_CHANGED: el HP puede afectar la visualización
     * - PLAYER_LEVELED_UP: stats cambian con level
     */
    private void subscribeToEvents() {
        GameEventManager.getInstance().subscribe(EventType.PLAYER_HP_CHANGED, this);
        GameEventManager.getInstance().subscribe(EventType.PLAYER_LEVELED_UP, this);
    }
    
    /**
     * Maneja eventos del juego.
     * Cuando ocurre cualquier evento relevante, se actualiza automáticamente.
     */
    @Override
    public void onGameEvent(EventType eventType, Object data) {
        SwingUtilities.invokeLater(() -> {
            refresh();
        });
    }
    
    /**
     * Actualiza las estadísticas mostradas.
     */
    public void refresh() {
        Character player = GameFacade.getInstance().getPlayer();
        if (player == null) {
            statsArea.setText("No hay personaje activo");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("ESTADISTICAS\n");
        sb.append("━━━━━━━━━━━━━━━━━━\n\n");
        sb.append("Ataque:  ").append(player.getAttack()).append("\n");
        sb.append("Defensa: ").append(player.getDefense()).append("\n");
        sb.append("Magia:   ").append(player.getMagic()).append("\n\n");
        
        sb.append("EXPERIENCIA\n");
        sb.append("━━━━━━━━━━━━━━━━━━\n\n");
        sb.append("XP: ").append(player.getCurrentExp()).append("/").append(player.getExpToNextLevel()).append("\n");
        int expPercent = (int)((double)player.getCurrentExp() / player.getExpToNextLevel() * 100);
        sb.append("Progreso: ").append(expPercent).append("%\n\n");
        
        sb.append("EQUIPAMIENTO\n");
        sb.append("━━━━━━━━━━━━━━━━━━\n\n");
        Equipment equipment = player.getInventory().getEquipment();
        
        if (equipment.getWeapon() != null) {
            sb.append("Arma:\n  ").append(equipment.getWeapon().getName()).append("\n\n");
        } else {
            sb.append("Arma:\n  Sin equipar\n\n");
        }
        
        if (equipment.getChestplate() != null) {
            sb.append("Armadura:\n  ").append(equipment.getChestplate().getName()).append("\n");
        } else {
            sb.append("Armadura:\n  Sin equipar\n");
        }
        
        statsArea.setText(sb.toString());
    }
}

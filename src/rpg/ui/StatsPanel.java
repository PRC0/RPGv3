package rpg.ui;

import javax.swing.*;
import java.awt.*;
import rpg.core.Character;
import rpg.core.GameFacade;
import rpg.events.GameEventListener;
import rpg.events.EventType;
import rpg.events.GameEventManager;

/**
 * Panel de estadísticas detalladas del personaje.
 * 
 * RESPONSABILIDAD:
 * - Se suscribe a eventos de cambios en stats
 * - Se actualiza automáticamente cuando el personaje sube de nivel
 * - Componente independiente y reutilizable
 */
public class StatsPanel extends JPanel implements GameEventListener {
    private static final long serialVersionUID = 1L;
    
    private JTextArea statsArea;
    
    public StatsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titleLabel = new JLabel("ESTADISTICAS COMPLETAS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Área de estadísticas
        statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        statsArea.setBackground(Color.WHITE);
        statsArea.setForeground(new Color(52, 73, 94));
        statsArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JScrollPane scrollPane = new JScrollPane(statsArea);
        
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        subscribeToEvents();
        refresh();
    }
    
    /**
     * Suscribe este panel a los eventos relevantes.
     * Este panel se interesa en:
     * - PLAYER_LEVELED_UP: las estadísticas cambian con cada nivel
     * - PLAYER_HP_CHANGED: mostrar HP actualizado
     * - PLAYER_MANA_CHANGED: mostrar maná actualizado
     */
    private void subscribeToEvents() {
        GameEventManager.getInstance().subscribe(EventType.PLAYER_LEVELED_UP, this);
        GameEventManager.getInstance().subscribe(EventType.PLAYER_HP_CHANGED, this);
        GameEventManager.getInstance().subscribe(EventType.PLAYER_MANA_CHANGED, this);
    }
    
    /**
     * Maneja eventos del juego.
     * Cuando cambian las estadísticas del jugador, se actualiza automáticamente.
     */
    @Override
    public void onGameEvent(EventType eventType, Object data) {
        SwingUtilities.invokeLater(() -> {
            refresh();
        });
    }
    
    public void refresh() {
        Character player = GameFacade.getInstance().getPlayer();
        if (player == null) {
            statsArea.setText("No hay personaje activo");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════════════════╗\n");
        sb.append("║      ESTADISTICAS DEL PERSONAJE            ║\n");
        sb.append("╚════════════════════════════════════════════╝\n\n");
        
        sb.append("Nombre:        ").append(player.getName()).append("\n");
        sb.append("Clase:         ").append(player.getClass().getSimpleName()).append("\n");
        sb.append("Nivel:         ").append(player.getLevel()).append("\n\n");
        
        sb.append("┌────────────────────────────────────────────┐\n");
        sb.append("│              VITALIDAD                     │\n");
        sb.append("└────────────────────────────────────────────┘\n\n");
        
        sb.append("HP:            ").append(player.getCurrentHp()).append(" / ").append(player.getMaxHp()).append("\n");
        sb.append("Mana:          ").append(player.getCurrentMana()).append(" / ").append(player.getMaxMana()).append("\n");
        sb.append("Oro:           ").append(player.getGold()).append("\n\n");
        
        sb.append("┌────────────────────────────────────────────┐\n");
        sb.append("│              ATRIBUTOS                     │\n");
        sb.append("└────────────────────────────────────────────┘\n\n");
        
        sb.append("Ataque:        ").append(player.getAttack()).append("\n");
        sb.append("Defensa:       ").append(player.getDefense()).append("\n");
        sb.append("Magia:         ").append(player.getMagic()).append("\n\n");
        
        sb.append("┌────────────────────────────────────────────┐\n");
        sb.append("│              EXPERIENCIA                   │\n");
        sb.append("└────────────────────────────────────────────┘\n\n");
        
        sb.append("XP Actual:     ").append(player.getCurrentExp()).append("\n");
        sb.append("XP Siguiente:  ").append(player.getExpToNextLevel()).append("\n");
        
        int expPercent = (int)((double)player.getCurrentExp() / player.getExpToNextLevel() * 100);
        sb.append("Progreso:      ");
        sb.append("[");
        for (int i = 0; i < 25; i++) {
            if (i < expPercent / 4) {
                sb.append("█");
            } else {
                sb.append("░");
            }
        }
        sb.append("] ").append(expPercent).append("%\n\n");
        
        sb.append("┌────────────────────────────────────────────┐\n");
        sb.append("│              EQUIPAMIENTO                  │\n");
        sb.append("└────────────────────────────────────────────┘\n\n");
        
        if (player.getInventory().getEquipment().getWeapon() != null) {
            sb.append("Arma:          ").append(player.getInventory().getEquipment().getWeapon().getName()).append("\n");
        } else {
            sb.append("Arma:          Ninguna\n");
        }
        
        if (player.getInventory().getEquipment().getChestplate() != null) {
            sb.append("Armadura:      ").append(player.getInventory().getEquipment().getChestplate().getName()).append("\n");
        } else {
            sb.append("Armadura:      Ninguna\n");
        }
        
        sb.append("\n╔════════════════════════════════════════════╗\n");
        
        statsArea.setText(sb.toString());
    }
}

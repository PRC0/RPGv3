package rpg.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import rpg.core.Character;

/**
 * Pantalla de Game Over.
 * Muestra estadísticas finales y opciones para reintentar o volver al menú.
 */
public class GameOverScreen extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private GameOverListener listener;
    
    public interface GameOverListener {
        void onRetry();
        void onMainMenu();
        void onExit();
    }
    
    public GameOverScreen(GameOverListener listener, Character player) {
        this.listener = listener;
        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 25));
        
        initComponents(player);
    }
    
    private void initComponents(Character player) {
        // Panel central
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(20, 20, 25));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(60, 50, 60, 50));
        
        // Título GAME OVER
        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setFont(new Font("Segoe UI", Font.BOLD, 72));
        gameOverLabel.setForeground(new Color(231, 76, 60)); // Rojo
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Tu aventura ha terminado...");
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 24));
        subtitleLabel.setForeground(new Color(150, 150, 150));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(gameOverLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createVerticalStrut(50));
        
        // Panel de estadísticas
        if (player != null) {
            JPanel statsPanel = createStatsPanel(player);
            statsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(statsPanel);
            centerPanel.add(Box.createVerticalStrut(50));
        }
        
        // Botones
        JButton retryButton = createGameOverButton("REINTENTAR", new Color(52, 152, 219));
        JButton menuButton = createGameOverButton("MENU PRINCIPAL", new Color(149, 165, 166));
        JButton exitButton = createGameOverButton("SALIR", new Color(231, 76, 60));
        
        retryButton.addActionListener(e -> {
            if (listener != null) listener.onRetry();
        });
        
        menuButton.addActionListener(e -> {
            if (listener != null) listener.onMainMenu();
        });
        
        exitButton.addActionListener(e -> {
            if (listener != null) listener.onExit();
        });
        
        centerPanel.add(retryButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(menuButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(exitButton);
        centerPanel.add(Box.createVerticalGlue());
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private JPanel createStatsPanel(Character player) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(44, 62, 80));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(231, 76, 60), 2),
            BorderFactory.createEmptyBorder(25, 40, 25, 40)
        ));
        
        JLabel titleLabel = new JLabel("ESTADISTICAS FINALES");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(255, 215, 0));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        
        // Stats
        addStatLine(panel, "Nombre:", player.getName());
        addStatLine(panel, "Clase:", player.getClass().getSimpleName());
        addStatLine(panel, "Nivel alcanzado:", String.valueOf(player.getLevel()));
        addStatLine(panel, "Oro acumulado:", player.getGold() + " monedas");
        
        return panel;
    }
    
    private void addStatLine(JPanel panel, String label, String value) {
        JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        linePanel.setBackground(new Color(44, 62, 80));
        
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelComp.setForeground(new Color(189, 195, 199));
        labelComp.setPreferredSize(new Dimension(180, 25));
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        valueComp.setForeground(Color.WHITE);
        
        linePanel.add(labelComp);
        linePanel.add(valueComp);
        
        panel.add(linePanel);
    }
    
    private JButton createGameOverButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 22));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(400, 60));
        button.setPreferredSize(new Dimension(400, 60));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        Color originalColor = color;
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(originalColor.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
}

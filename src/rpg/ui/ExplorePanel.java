package rpg.ui;

import javax.swing.*;
import java.awt.*;
import rpg.core.GameConstants;

/**
 * Panel de exploración donde el jugador puede iniciar encuentros.
 */
public class ExplorePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private MainGameWindow mainWindow;
    private JTextArea descriptionArea;
    
    public ExplorePanel(MainGameWindow mainWindow) {
        this.mainWindow = mainWindow;
        
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titleLabel = new JLabel("EXPLORACION");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Área de descripción
        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBackground(new Color(255, 255, 255));
        descriptionArea.setForeground(new Color(52, 73, 94));
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        descriptionArea.setText(
            "Te encuentras en un campo de batalla abandonado.\n\n" +
            "Los vientos soplan entre armaduras oxidadas y espadas clavadas en el suelo.\n" +
            "A lo lejos, puedes escuchar gruñidos de criaturas salvajes.\n\n" +
            "¿Qué harás?"
        );
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        
        JButton exploreBattleBtn = createStyledButton("Buscar Batalla", new Color(192, 57, 43));
        exploreBattleBtn.addActionListener(e -> {
            mainWindow.logMessage("Buscando enemigos...");
            mainWindow.startBattle();
        });
        
        JButton restBtn = createStyledButton("Descansar", new Color(39, 174, 96));
        restBtn.addActionListener(e -> {
            mainWindow.getGameFacade().getPlayer().heal(GameConstants.REST_HP_RESTORE);
            mainWindow.refreshAllPanels();
            mainWindow.logMessage("Descansaste y recuperaste " + GameConstants.REST_HP_RESTORE + " HP");
            JOptionPane.showMessageDialog(this, 
                "Recuperaste " + GameConstants.REST_HP_RESTORE + " HP", 
                "Descanso", 
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        buttonPanel.add(exploreBattleBtn);
        buttonPanel.add(restBtn);
        
        // Panel central
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);
        centerPanel.add(descriptionArea, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(titleLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        
        // ¡AGREGA ESTO SIEMPRE PARA BOTONES DE COLORES!
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(220, 55));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 3),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
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
        // Actualizar si es necesario
    }
}

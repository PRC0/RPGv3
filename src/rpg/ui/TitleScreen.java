package rpg.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Pantalla de título/inicio del juego.
 * Muestra logo y opciones: Nuevo Juego, Cargar Partida, Salir.
 */
public class TitleScreen extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton exitButton;
    
    // Listener para manejar las acciones
    private TitleScreenListener listener;
    
    public interface TitleScreenListener {
        void onNewGame();
        void onLoadGame();
        void onExit();
    }
    
    public TitleScreen(TitleScreenListener listener) {
        this.listener = listener;
        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 30));
        
        initComponents();
    }
    
    private void initComponents() {
        // Panel central con todo el contenido
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(20, 20, 30));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        
        // Logo del juego
        JLabel titleLabel = new JLabel("RPG ADVENTURE");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 72));
        titleLabel.setForeground(new Color(255, 215, 0)); // Dorado
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Edicion Definitiva");
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 24));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Espacio
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createVerticalStrut(80));
        
        // Botones
        newGameButton = createMenuButton("NUEVO JUEGO", new Color(46, 204, 113));
        loadGameButton = createMenuButton("CARGAR PARTIDA", new Color(52, 152, 219));
        exitButton = createMenuButton("SALIR", new Color(231, 76, 60));
        
        newGameButton.addActionListener(e -> {
            if (listener != null) listener.onNewGame();
        });
        
        loadGameButton.addActionListener(e -> {
            if (listener != null) listener.onLoadGame();
        });
        
        exitButton.addActionListener(e -> {
            if (listener != null) listener.onExit();
        });
        
        centerPanel.add(newGameButton);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(loadGameButton);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(exitButton);
        centerPanel.add(Box.createVerticalGlue());
        
        // Footer
        JLabel versionLabel = new JLabel("v3.0 | (c) 2025");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        versionLabel.setForeground(new Color(100, 100, 100));
        versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
        add(versionLabel, BorderLayout.SOUTH);
    }
    
    private JButton createMenuButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 28));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(500, 80));
        button.setPreferredSize(new Dimension(500, 80));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
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

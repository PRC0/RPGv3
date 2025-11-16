package rpg.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de menú lateral izquierdo con botones de navegación.
 */
public class LeftMenuPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private MainGameWindow mainWindow;
    
    // Definir colores del tema oscuro
    private static final Color PANEL_FONDO = new Color(0x34, 0x49, 0x5E);    // #34495E (Azul oscuro)
    private static final Color BOTON_FONDO = new Color(0x2C, 0x3E, 0x50);    // #2C3E50 (Azul más oscuro)
    private static final Color BOTON_HOVER = new Color(0x47, 0x65, 0x82);    // Color hover
    private static final Color TEXTO_CLARO = Color.WHITE;
    
    public LeftMenuPanel(MainGameWindow mainWindow) {
        this.mainWindow = mainWindow;
        
        setLayout(new GridLayout(8, 1, 5, 5));
        setBackground(PANEL_FONDO);
        setPreferredSize(new Dimension(150, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        
        // Crear botones de navegación
        addMenuButton("Explorar", "EXPLORE");
        addMenuButton("Inventario", "INVENTORY");
        addMenuButton("Misiones", "QUEST");
        addMenuButton("Estadisticas", "STATS");
        
        add(Box.createVerticalStrut(10));
        add(createSeparator());
        add(Box.createVerticalStrut(10));
        
        addActionButton("Guardar", () -> mainWindow.saveGame());
        addActionButton("Cargar", () -> mainWindow.loadGame());
        addActionButton("Salir", () -> System.exit(0));
    }
    
    /**
     * Agrega un botón de navegación que cambia de panel.
     */
    private void addMenuButton(String text, String panelName) {
        JButton button = new JButton(text);
        styleButton(button);
        button.addActionListener(e -> mainWindow.showPanel(panelName));
        
        // Agregar tooltips descriptivos con HTML
        switch(panelName) {
            case "EXPLORE":
                button.setToolTipText("<html><body style='width: 200px; padding: 5px; background-color: #FFFACD;'>" +
                    "<b style='font-size: 12px; color: #000;'>EXPLORAR</b><br>" +
                    "<span style='font-size: 11px; color: #333;'>Busca enemigos para combatir<br>o descansa para recuperar HP</span>" +
                    "</body></html>");
                break;
            case "INVENTORY":
                button.setToolTipText("<html><body style='width: 200px; padding: 5px; background-color: #FFFACD;'>" +
                    "<b style='font-size: 12px; color: #000;'>INVENTARIO</b><br>" +
                    "<span style='font-size: 11px; color: #333;'>Gestiona tus items y<br>equipamiento</span>" +
                    "</body></html>");
                break;
            case "QUEST":
                button.setToolTipText("<html><body style='width: 200px; padding: 5px; background-color: #FFFACD;'>" +
                    "<b style='font-size: 12px; color: #000;'>MISIONES</b><br>" +
                    "<span style='font-size: 11px; color: #333;'>Revisa y completa<br>misiones disponibles</span>" +
                    "</body></html>");
                break;
            case "STATS":
                button.setToolTipText("<html><body style='width: 200px; padding: 5px; background-color: #FFFACD;'>" +
                    "<b style='font-size: 12px; color: #000;'>ESTADISTICAS</b><br>" +
                    "<span style='font-size: 11px; color: #333;'>Mira las estadisticas<br>completas de tu personaje</span>" +
                    "</body></html>");
                break;
        }
        
        add(button);
    }
    
    /**
     * Agrega un botón de acción personalizada.
     */
    private void addActionButton(String text, Runnable action) {
        JButton button = new JButton(text);
        styleButton(button);
        button.addActionListener(e -> action.run());
        
        // Agregar tooltips descriptivos con HTML
        if (text.equals("Guardar")) {
            button.setToolTipText("<html><body style='width: 200px; padding: 5px; background-color: #FFFACD;'>" +
                "<b style='font-size: 12px; color: #000;'>GUARDAR PARTIDA</b><br>" +
                "<span style='font-size: 11px; color: #333;'>Guarda tu progreso actual</span>" +
                "</body></html>");
        } else if (text.equals("Cargar")) {
            button.setToolTipText("<html><body style='width: 200px; padding: 5px; background-color: #FFFACD;'>" +
                "<b style='font-size: 12px; color: #000;'>CARGAR PARTIDA</b><br>" +
                "<span style='font-size: 11px; color: #333;'>Carga una partida<br>guardada previamente</span>" +
                "</body></html>");
        } else if (text.equals("Salir")) {
            button.setToolTipText("<html><body style='width: 200px; padding: 5px; background-color: #FFE4E1;'>" +
                "<b style='font-size: 12px; color: #C00;'>SALIR DEL JUEGO</b><br>" +
                "<span style='font-size: 11px; color: #333;'>Cierra el juego<br>(no olvides guardar!)</span>" +
                "</body></html>");
        }
        
        add(button);
    }
    
    /**
     * Crea un separador visual.
     */
    private JSeparator createSeparator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(100, 100, 105));
        return sep;
    }
    
    /**
     * Aplica estilo consistente a los botones.
     */
    private void styleButton(JButton button) {
        // --- ¡ESTA ES LA SOLUCIÓN! ---
        // Esto elimina el estilo nativo de Windows que bloquea el color
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI()); 
        // -----------------------------
        
        // Configurar fuente y texto
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(TEXTO_CLARO);      // Texto blanco
        
        // Configurar colores de fondo
        button.setBackground(BOTON_FONDO);      // Fondo azul oscuro
        button.setFocusPainted(false);          // Quitar borde azul al hacer clic
        button.setOpaque(true);
        
        // Borde sutil que combina con el tema
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PANEL_FONDO, 2),
            BorderFactory.createEmptyBorder(12, 8, 12, 8)
        ));
        
        // Cursor de mano
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover - el botón se aclara al pasar el mouse
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BOTON_HOVER);  // Se aclara un poco
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BOTON_FONDO);  // Vuelve al color original
            }
        });
    }
}

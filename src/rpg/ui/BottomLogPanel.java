package rpg.ui;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Panel inferior que muestra un log de eventos del juego.
 */
public class BottomLogPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JTextArea logArea;
    private SimpleDateFormat timeFormat;
    
    public BottomLogPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 35));
        setPreferredSize(new Dimension(0, 150));
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        
        JLabel titleLabel = new JLabel("Log de Eventos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(236, 240, 241));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 8, 0));
        
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        logArea.setBackground(new Color(44, 62, 80));
        logArea.setForeground(new Color(189, 195, 199));
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 73, 94), 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        addLog("Sistema iniciado - ¡Bienvenido a RPGv3!");
    }
    
    /**
     * Agrega un mensaje al log con timestamp.
     */
    public void addLog(String message) {
        String timestamp = timeFormat.format(new Date());
        logArea.append("[" + timestamp + "] " + message + "\n");
        
        // Auto-scroll al final
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    /**
     * Limpia el log.
     */
    public void clearLog() {
        logArea.setText("");
    }
}

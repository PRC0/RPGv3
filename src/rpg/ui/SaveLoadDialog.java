package rpg.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import rpg.persistence.GameState;
import rpg.persistence.SaveManager;

/**
 * Diálogo para seleccionar slot de guardado/carga.
 * Muestra 5 slots con preview de las partidas guardadas.
 */
public class SaveLoadDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private SaveManager saveManager;
    private int selectedSlot = -1;
    private boolean confirmed = false;
    private boolean isSaveMode; // true = guardar, false = cargar
    
    public SaveLoadDialog(Frame parent, SaveManager saveManager, boolean isSaveMode) {
        super(parent, isSaveMode ? "Guardar Partida" : "Cargar Partida", true);
        this.saveManager = saveManager;
        this.isSaveMode = isSaveMode;
        
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        
        initComponents();
    }
    
    private void initComponents() {
        // Panel título
        JLabel titleLabel = new JLabel(isSaveMode ? "SELECCIONA UN SLOT PARA GUARDAR" : "SELECCIONA UNA PARTIDA");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        
        // Panel de slots
        JPanel slotsPanel = new JPanel();
        slotsPanel.setLayout(new BoxLayout(slotsPanel, BoxLayout.Y_AXIS));
        slotsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        List<GameState> saves = saveManager.getAllSaves();
        
        for (int i = 0; i < saves.size(); i++) {
            int slotNumber = i + 1;
            GameState gameState = saves.get(i);
            
            JPanel slotPanel = createSlotPanel(slotNumber, gameState);
            slotsPanel.add(slotPanel);
            slotsPanel.add(Box.createVerticalStrut(10));
        }
        
        JScrollPane scrollPane = new JScrollPane(slotsPanel);
        scrollPane.setBorder(null);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        JButton confirmButton = new JButton("CONFIRMAR");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        confirmButton.setPreferredSize(new Dimension(150, 40));
        confirmButton.setBackground(new Color(46, 204, 113));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        
        JButton cancelButton = new JButton("CANCELAR");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cancelButton.setPreferredSize(new Dimension(150, 40));
        cancelButton.setBackground(new Color(149, 165, 166));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        
        confirmButton.addActionListener(e -> {
            if (selectedSlot == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un slot primero");
                return;
            }
            confirmed = true;
            dispose();
        });
        
        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });
        
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createSlotPanel(int slotNumber, GameState gameState) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setPreferredSize(new Dimension(550, 80));
        panel.setMaximumSize(new Dimension(550, 80));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 73, 94), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setBackground(Color.WHITE);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Número de slot
        JLabel slotLabel = new JLabel("SLOT " + slotNumber);
        slotLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        slotLabel.setForeground(new Color(52, 73, 94));
        
        // Información del save
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFocusable(false);
        infoArea.setBackground(Color.WHITE);
        infoArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        if (gameState != null) {
            infoArea.setText(gameState.getSummary());
            infoArea.setForeground(Color.BLACK);
        } else {
            infoArea.setText("Slot vacío");
            infoArea.setForeground(new Color(149, 165, 166));
            infoArea.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        }
        
        panel.add(slotLabel, BorderLayout.WEST);
        panel.add(infoArea, BorderLayout.CENTER);
        
        // Click para seleccionar
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            private Color normalBg = Color.WHITE;
            private Color selectedBg = new Color(230, 240, 255);
            
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // En modo carga, solo permitir seleccionar slots con saves
                if (!isSaveMode && gameState == null) {
                    JOptionPane.showMessageDialog(SaveLoadDialog.this, 
                        "Este slot está vacío", 
                        "Slot vacío", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                selectedSlot = slotNumber;
                // Reset todos
                Container parent = panel.getParent();
                for (Component c : parent.getComponents()) {
                    if (c instanceof JPanel) {
                        c.setBackground(normalBg);
                    }
                }
                panel.setBackground(selectedBg);
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (selectedSlot != slotNumber) {
                    panel.setBackground(new Color(245, 245, 245));
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (selectedSlot != slotNumber) {
                    panel.setBackground(normalBg);
                } else {
                    panel.setBackground(selectedBg);
                }
            }
        });
        
        return panel;
    }
    
    /**
     * Muestra el diálogo y retorna el slot seleccionado.
     * @return Número de slot (1-5) o -1 si canceló
     */
    public int showDialog() {
        setVisible(true);
        return confirmed ? selectedSlot : -1;
    }
}

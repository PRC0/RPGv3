package rpg.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import rpg.factory.CharacterType;

/**
 * Pantalla de selección de clase.
 * Muestra las 4 clases disponibles con sus stats y descripción.
 */
public class ClassSelectionScreen extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private ClassSelectionListener listener;
    private CharacterType selectedClass = null;
    private List<JPanel> classCards = new ArrayList<>();
    
    // Colores
    private static final Color CARD_BG = new Color(44, 62, 80);
    private static final Color CARD_SELECTED = new Color(52, 73, 94);
    private static final Color CARD_HOVER = new Color(48, 68, 87);
    
    public interface ClassSelectionListener {
        void onClassSelected(CharacterType characterType, String playerName);
        void onCancel();
    }
    
    public ClassSelectionScreen(ClassSelectionListener listener) {
        this.listener = listener;
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 40));
        
        initComponents();
    }
    
    private void initComponents() {
        // Título
        JLabel titleLabel = new JLabel("SELECCIONA TU CLASE");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        titleLabel.setForeground(new Color(255, 215, 0));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        
        // Panel de clases
        JPanel classPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        classPanel.setBackground(new Color(30, 30, 40));
        classPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Crear card para cada clase
        classPanel.add(createClassCard(
            CharacterType.WARRIOR,
            "GUERRERO",
            "Tanque resistente",
            "HP: 120 | ATK: 12 | DEF: 10 | MAG: 3",
            new Color(192, 57, 43),
            "hero.png"
        ));
        
        classPanel.add(createClassCard(
            CharacterType.ARCHER,
            "ARQUERO",
            "Ataque a distancia",
            "HP: 90 | ATK: 15 | DEF: 5 | MAG: 5",
            new Color(39, 174, 96),
            "archer.png"
        ));
        
        classPanel.add(createClassCard(
            CharacterType.MAGE,
            "MAGO",
            "Poder magico devastador",
            "HP: 70 | ATK: 5 | DEF: 3 | MAG: 20",
            new Color(142, 68, 173),
            "mage.png"
        ));
        
        classPanel.add(createClassCard(
            CharacterType.PRIEST,
            "SACERDOTE",
            "Curación y soporte",
            "HP: 80 | ATK: 8 | DEF: 5 | MAG: 15",
            new Color(241, 196, 15),
            "priest.png"
        ));
        
        // Panel inferior con nombre y botones
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(new Color(30, 30, 40));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 40, 100));
        
        JLabel nameLabel = new JLabel("Nombre del personaje:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField nameField = new JTextField("Heroe");
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        nameField.setMaximumSize(new Dimension(400, 40));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(30, 30, 40));
        
        JButton confirmButton = createButton("CONFIRMAR", new Color(46, 204, 113));
        JButton cancelButton = createButton("VOLVER", new Color(149, 165, 166));
        
        confirmButton.addActionListener(e -> {
            if (selectedClass == null) {
                JOptionPane.showMessageDialog(this, 
                    "Selecciona una clase primero", 
                    "Clase no seleccionada", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String playerName = nameField.getText().trim();
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Ingresa un nombre para tu personaje", 
                    "Nombre requerido", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (listener != null) {
                listener.onClassSelected(selectedClass, playerName);
            }
        });
        
        cancelButton.addActionListener(e -> {
            if (listener != null) listener.onCancel();
        });
        
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        
        bottomPanel.add(nameLabel);
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(nameField);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(buttonPanel);
        
        add(titleLabel, BorderLayout.NORTH);
        add(classPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createClassCard(CharacterType type, String name, String description, 
                                    String stats, Color color, String imageFile) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 3),
            BorderFactory.createEmptyBorder(20, 15, 20, 15)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        nameLabel.setForeground(color);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Imagen
        JLabel imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            ImageIcon icon = new ImageIcon("assets/images/" + imageFile);
            Image img = icon.getImage().getScaledInstance(96, 96, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imageLabel.setText("[IMG]");
        }
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        descLabel.setForeground(new Color(189, 195, 199));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextArea statsArea = new JTextArea(stats);
        statsArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        statsArea.setForeground(Color.WHITE);
        statsArea.setBackground(CARD_BG);
        statsArea.setEditable(false);
        statsArea.setFocusable(false);
        statsArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(Box.createVerticalGlue());
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(imageLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(descLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(statsArea);
        card.add(Box.createVerticalGlue());
        
        // Agregar a la lista de cards
        classCards.add(card);
        
        // Click para seleccionar
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedClass = type;
                // Reset all cards
                for (JPanel c : classCards) {
                    c.setBackground(CARD_BG);
                }
                card.setBackground(CARD_SELECTED);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                if (selectedClass != type) {
                    card.setBackground(CARD_HOVER);
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (selectedClass != type) {
                    card.setBackground(CARD_BG);
                } else {
                    card.setBackground(CARD_SELECTED);
                }
            }
        });
        
        return card;
    }
    
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(200, 50));
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

package rpg.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import rpg.core.Character;
import rpg.core.GameFacade;
import rpg.inventory.Item;
import rpg.inventory.Equippable;

/**
 * Panel de inventario que muestra items del jugador.
 */
public class InventoryPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JTable itemTable;
    private DefaultTableModel tableModel;
    
    public InventoryPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titleLabel = new JLabel("INVENTARIO");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Tabla de items
        String[] columns = {"Nombre", "Descripción", "Tipo"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        itemTable = new JTable(tableModel);
        itemTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemTable.setRowHeight(35);
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemTable.setSelectionBackground(new Color(52, 152, 219));
        itemTable.setSelectionForeground(Color.WHITE);
        itemTable.setGridColor(new Color(189, 195, 199));
        itemTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        itemTable.getTableHeader().setBackground(new Color(52, 73, 94));
        itemTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2));
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        
        JButton equipBtn = createActionButton("Equipar", new Color(41, 128, 185));
        equipBtn.addActionListener(e -> equipSelectedItem());
        
        JButton useBtn = createActionButton("Usar", new Color(39, 174, 96));
        useBtn.addActionListener(e -> useSelectedItem());
        
        buttonPanel.add(equipBtn);
        buttonPanel.add(useBtn);
        
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        refresh();
    }
    
    public void refresh() {
        tableModel.setRowCount(0);
        
        Character player = GameFacade.getInstance().getPlayer();
        if (player != null) {
            for (Item item : player.getInventory().getItems()) {
                String type = item instanceof Equippable ? "Equipable" : "Consumible";
                tableModel.addRow(new Object[]{
                    item.getName(),
                    item.getDescription(),
                    type
                });
            }
        }
    }
    
    private void equipSelectedItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un item primero");
            return;
        }
        
        Character player = GameFacade.getInstance().getPlayer();
        Item item = player.getInventory().getItems().get(selectedRow);
        
        if (item instanceof Equippable) {
            player.getInventory().equipItem((Equippable) item);
            JOptionPane.showMessageDialog(this, "Item equipado: " + item.getName());
            refresh();
        } else {
            JOptionPane.showMessageDialog(this, "Este item no se puede equipar");
        }
    }
    
    private void useSelectedItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un item primero");
            return;
        }
        
        Character player = GameFacade.getInstance().getPlayer();
        Item item = player.getInventory().getItems().get(selectedRow);
        
        item.use(player);
        JOptionPane.showMessageDialog(this, "Usaste: " + item.getName());
        refresh();
    }
    
    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        
        // Configuración básica del botón
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(140, 45));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        Color originalColor = color;
        Color hoverColor = color.brighter();
        Color pressedColor = color.darker();
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(pressedColor);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
        });
        
        return button;
    }
}

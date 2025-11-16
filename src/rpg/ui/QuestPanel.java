package rpg.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import rpg.core.GameFacade;
import rpg.quest.Quest;
import rpg.quest.QuestStatus;
import rpg.events.GameEventListener;
import rpg.events.EventType;
import rpg.events.GameEventManager;

/**
 * Panel de misiones que muestra las quests disponibles y activas.
 * 
 * RESPONSABILIDAD:
 * - Se suscribe a QUEST_COMPLETED
 * - Se actualiza automáticamente cuando se completan quests
 * - Componente independiente y reutilizable
 */
public class QuestPanel extends JPanel implements GameEventListener {
    private static final long serialVersionUID = 1L;
    
    private JTable questTable;
    private DefaultTableModel tableModel;
    private JTextArea detailsArea;
    
    public QuestPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titleLabel = new JLabel("MISIONES");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Tabla de quests
        String[] columns = {"Título", "Estado", "Progreso"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        questTable = new JTable(tableModel);
        questTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        questTable.setRowHeight(30);
        questTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        questTable.setSelectionBackground(new Color(52, 152, 219));
        questTable.setSelectionForeground(Color.WHITE);
        questTable.setGridColor(new Color(189, 195, 199));
        questTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        questTable.getTableHeader().setBackground(new Color(52, 73, 94));
        questTable.getTableHeader().setForeground(Color.WHITE);
        questTable.getSelectionModel().addListSelectionListener(e -> showQuestDetails());
        
        JScrollPane tableScrollPane = new JScrollPane(questTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Misiones"));
        
        // Área de detalles
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setBackground(new Color(250, 250, 250));
        detailsArea.setForeground(new Color(44, 62, 80));
        detailsArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
        detailsScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Detalles de la Misión",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(52, 152, 219)
        ));
        detailsScrollPane.setPreferredSize(new Dimension(0, 200));
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        
        JButton startBtn = createQuestButton("Iniciar Quest", new Color(39, 174, 96));
        startBtn.addActionListener(e -> startSelectedQuest());
        
        JButton completeBtn = createQuestButton("Completar Quest", new Color(41, 128, 185));
        completeBtn.addActionListener(e -> completeSelectedQuest());
        
        buttonPanel.add(startBtn);
        buttonPanel.add(completeBtn);
        
        // Layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScrollPane, detailsScrollPane);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.6);
        splitPane.setBorder(null);
        
        add(titleLabel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        subscribeToEvents();
        refresh();
    }
    
    /**
     * Suscribe este panel a los eventos relevantes.
     * Este panel se interesa en:
     * - QUEST_COMPLETED: cuando se completa una quest
     */
    private void subscribeToEvents() {
        GameEventManager.getInstance().subscribe(EventType.QUEST_COMPLETED, this);
    }
    
    /**
     * Maneja eventos del juego.
     * Cuando se completa una quest, se actualiza automáticamente.
     */
    @Override
    public void onGameEvent(EventType eventType, Object data) {
        SwingUtilities.invokeLater(() -> {
            refresh();
        });
    }
    
    public void refresh() {
        tableModel.setRowCount(0);
        
        for (Quest quest : GameFacade.getInstance().getQuestManager().getActiveQuests()) {
            String progress = String.format("%.0f%%", quest.getTotalProgress() * 100);
            tableModel.addRow(new Object[]{
                quest.getTitle(),
                quest.getStatus(),
                progress
            });
        }
        
        for (Quest quest : GameFacade.getInstance().getQuestManager().getAvailableQuests()) {
            tableModel.addRow(new Object[]{
                quest.getTitle(),
                quest.getStatus(),
                "-"
            });
        }
    }
    
    private void showQuestDetails() {
        int selectedRow = questTable.getSelectedRow();
        if (selectedRow == -1) {
            detailsArea.setText("Selecciona una misión para ver los detalles");
            return;
        }
        
        String questTitle = (String) tableModel.getValueAt(selectedRow, 0);
        Quest quest = findQuestByTitle(questTitle);
        
        if (quest != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════\n");
            sb.append("  ").append(quest.getTitle().toUpperCase()).append("\n");
            sb.append("═══════════════════════════════════════\n\n");
            
            sb.append("📜 DESCRIPCIÓN:\n");
            sb.append("   ").append(quest.getDescription()).append("\n\n");
            
            sb.append("🎯 OBJETIVOS:\n");
            if (quest.getObjectives().isEmpty()) {
                sb.append("   Sin objetivos específicos\n");
            } else {
                for (int i = 0; i < quest.getObjectives().size(); i++) {
                    sb.append("   ").append(quest.getObjectives().get(i).toString()).append("\n");
                }
            }
            sb.append("\n");
            
            sb.append("📊 ESTADO: ").append(quest.getStatus()).append("\n\n");
            
            sb.append("🎁 RECOMPENSAS:\n");
            sb.append("   ").append(quest.getRewards()).append("\n");
            sb.append("\n═══════════════════════════════════════");
            
            detailsArea.setText(sb.toString());
            detailsArea.setCaretPosition(0);
        }
    }
    
    private Quest findQuestByTitle(String title) {
        for (Quest q : GameFacade.getInstance().getQuestManager().getActiveQuests()) {
            if (q.getTitle().equals(title)) return q;
        }
        for (Quest q : GameFacade.getInstance().getQuestManager().getAvailableQuests()) {
            if (q.getTitle().equals(title)) return q;
        }
        return null;
    }
    
    private void startSelectedQuest() {
        int selectedRow = questTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una quest primero");
            return;
        }
        
        String questTitle = (String) tableModel.getValueAt(selectedRow, 0);
        Quest quest = findQuestByTitle(questTitle);
        if (quest != null) {
            GameFacade.getInstance().getQuestManager().startQuest(quest.getId());
            JOptionPane.showMessageDialog(this, "Quest iniciada!");
            refresh();
        }
    }
    
    private void completeSelectedQuest() {
        int selectedRow = questTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una quest primero");
            return;
        }
        
        String questTitle = (String) tableModel.getValueAt(selectedRow, 0);
        Quest quest = findQuestByTitle(questTitle);
        if (quest != null) {
            GameFacade.getInstance().getQuestManager().completeQuest(quest.getId());
            JOptionPane.showMessageDialog(this, "¡Quest completada!");
            refresh();
        }
    }
    
    private JButton createQuestButton(String text, Color color) {
        JButton button = new JButton(text);
        
        // Configuración básica del botón
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(180, 45));
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

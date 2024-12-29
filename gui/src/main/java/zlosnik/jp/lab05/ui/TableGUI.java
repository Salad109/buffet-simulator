package zlosnik.jp.lab05.ui;

import zlosnik.jp.lab05.sim.Cafeteria;
import zlosnik.jp.lab05.sim.GUI;
import zlosnik.jp.lab05.sim.SimulationStatus;
import zlosnik.jp.lab05.sim.Worker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TableGUI implements GUI {
    private DefaultTableModel tableModel;
    private Cafeteria cafeteria;

    public void setCafeteria(Cafeteria cafeteria) {
        this.cafeteria = cafeteria;
    }

public void createAndShowGUI() {
    int rows = Math.max(Math.max(cafeteria.cooks.size(), cafeteria.cashiers.size()), 6);
    JFrame frame = new JFrame("Buffet Simulation");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    final int FRAME_WIDTH = 800;
    final int FRAME_HEIGHT = 300;
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    String[] headerNames = new String[5];
    headerNames[0] = "Outside";
    headerNames[1] = "Cooks";
    headerNames[2] = "Cashiers";
    headerNames[3] = "Table queue";
    headerNames[4] = "Table";
    tableModel = new DefaultTableModel(rows, 5);
    tableModel.setColumnIdentifiers(headerNames);
    JTable table = new JTable(tableModel);
    table.getTableHeader().setReorderingAllowed(false);

    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
        table.getColumnModel().getColumn(i).setPreferredWidth(300);
    }

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(FRAME_WIDTH, (int) (table.getRowHeight() * (rows + 1.5))));
    frame.add(scrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

    JPanel cookButtonPanel = new JPanel();
    cookButtonPanel.setLayout(new FlowLayout());

    for (Worker cook : cafeteria.cooks) {
        JButton button = new JButton("Toggle cook " + cook.getId() + " [ON]");
        cookButtonPanel.add(button);
        button.addActionListener(_ -> {
            cook.toggle();
            button.setText("Toggle cook " + cook.getId() + " [" + (cook.isRunning() ? "ON" : "OFF") + "]");
        });
    }

    JPanel cashierButtonPanel = new JPanel();
    cashierButtonPanel.setLayout(new FlowLayout());

    for (Worker cashier : cafeteria.cashiers) {
        JButton button = new JButton("Toggle cashier " + cashier.getId() + " [ON]");
        cashierButtonPanel.add(button);
        button.addActionListener(_ -> {
            cashier.toggle();
            button.setText("Toggle cashier " + cashier.getId() + " [" + (cashier.isRunning() ? "ON" : "OFF") + "]");
        });
    }

    buttonPanel.add(cookButtonPanel);
    buttonPanel.add(cashierButtonPanel);

    frame.add(buttonPanel, BorderLayout.SOUTH);

    frame.pack();
    frame.setAlwaysOnTop(true);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}

private void updateTableData(SimulationStatus status) {
    tableModel.setValueAt(status.outsideQueue(), 0, 0);
    int row = 0;
    for (String cook : status.cooks()) {
        tableModel.setValueAt(cook, row++, 1);
    }
    row = 0;
    for (String cashier : status.cashiers()) {
        tableModel.setValueAt(cashier, row++, 2);
    }

    tableModel.setValueAt(status.tableQueue().getFirst(), 1, 3);
    tableModel.setValueAt(status.tableQueue().getLast(), 4, 3);

    row = 0;
    for (String tableSeat : status.tableSeats()) {
        tableModel.setValueAt(tableSeat, row++, 4);
    }
}

    public void update() {
        updateTableData(cafeteria.getSimulationStatus());
    }
}
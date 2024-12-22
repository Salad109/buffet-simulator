package zlosnik.jp.lab05.sim;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GUI {
    private static DefaultTableModel tableModel;
    private int cooks;
    private int cashiers;
    private Cafeteria cafeteria;

    public GUI() {
        createAndShowGUI();
    }

    public void setCafeteria(Cafeteria cafeteria) {
        this.cafeteria = cafeteria;
        this.cooks = cafeteria.cooks.size();
        this.cashiers = cafeteria.cashiers.size();
        update();
    }

    private void createAndShowGUI() {
        int rows = Math.max(Math.max(cooks, cashiers), 6);
        JFrame frame = new JFrame("Buffet Simulation");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1200, 400);

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
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateTableData(SimulationStatus status) {
        tableModel.setValueAt(status.outsideQueue.toString(), 0, 0);
        int row = 0;
        for (String cook : status.cooks) {
            tableModel.setValueAt(cook, row++, 1);
        }
        row = 0;
        for (String cashier : status.cashiers) {
            tableModel.setValueAt(cashier, row++, 2);
        }

        tableModel.setValueAt(status.tableQueue.getFirst(), 1, 3);
        tableModel.setValueAt(status.tableQueue.getLast(), 4, 3);

        row = 0;
        for (String tableSeat : status.tableSeats) {
            tableModel.setValueAt(tableSeat, row++, 4);
        }
    }

    public void update() {
        updateTableData(cafeteria.getSimulationStatus());
    }
}
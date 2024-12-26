package zlosnik.jp.lab05.ui;

import zlosnik.jp.lab05.sim.Cafeteria;
import zlosnik.jp.lab05.sim.GUI;
import zlosnik.jp.lab05.sim.SimulationStatus;

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
        frame.setSize(1000, 400);

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
        tableModel.setValueAt(status.getOutsideQueue(), 0, 0);
        int row = 0;
        for (String cook : status.getCooks()) {
            tableModel.setValueAt(cook, row++, 1);
        }
        row = 0;
        for (String cashier : status.getCashiers()) {
            tableModel.setValueAt(cashier, row++, 2);
        }

        tableModel.setValueAt(status.getTableQueue().getFirst(), 1, 3);
        tableModel.setValueAt(status.getTableQueue().getLast(), 4, 3);

        row = 0;
        for (String tableSeat : status.getTableSeats()) {
            tableModel.setValueAt(tableSeat, row++, 4);
        }
    }

    public void update() {
        updateTableData(cafeteria.getSimulationStatus());
    }
}
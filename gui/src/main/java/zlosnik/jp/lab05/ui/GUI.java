package zlosnik.jp.lab05.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GUI {
    private static DefaultTableModel tableModel;

    public GUI(int cooks, int cashiers) {
        createAndShowGUI(cooks, cashiers);
    }

    public static void main(String[] args) {
        GUI gui = new GUI(3, 2);
        gui.createAndShowGUI(3, 2);

        gui.updateTableData("Hello");
    }

    private void createAndShowGUI(int cooks, int cashiers) {
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

        // frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void updateTableData(String newData) {
        int number = 1;
        for(int i = 0; i < tableModel.getRowCount(); i++) {
            for(int j = 0; j < tableModel.getColumnCount(); j++) {
                tableModel.setValueAt(newData + number++, i, j);
            }
        }

        tableModel.setValueAt("============", 1, 4);
        tableModel.setValueAt("============", 4, 4);
    }

    public void updateTableData(String newData, int row, int col) {
        tableModel.setValueAt(newData, row, col);
    }
}
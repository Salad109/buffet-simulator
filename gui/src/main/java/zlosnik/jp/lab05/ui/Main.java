package zlosnik.jp.lab05.ui;

import zlosnik.jp.lab05.sim.Cafeteria;
import zlosnik.jp.lab05.sim.Customer;
import zlosnik.jp.lab05.sim.GUI;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int COOK_COUNT = 2;
    private static final int CASHIER_COUNT = 2;
    private static final int CUSTOMER_COUNT = 16;
    private static final int TABLE_LENGTH = 5;

    public static void main(String[] args) {
        GUI gui = new TableGUI();
        Cafeteria cafeteria = new Cafeteria(COOK_COUNT, CASHIER_COUNT, TABLE_LENGTH, gui);
        gui.setCafeteria(cafeteria);
        gui.createAndShowGUI();

        // Create and start customer threads
        List<Thread> customerThreads = new ArrayList<>();
        for (int i = 1; i <= CUSTOMER_COUNT; i++) {
            Customer customer = new Customer(cafeteria, gui);
            Thread customerThread = new Thread(customer);
            customerThreads.add(customerThread);
            customerThread.start();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

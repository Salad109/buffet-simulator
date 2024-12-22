package zlosnik.jp.lab05;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int COOK_COUNT = 4;
    private static final int CASHIER_COUNT = 3;
    private static final int CUSTOMER_COUNT = 8;
    private static final int TABLE_SIZE = 16;
    public static void main(String[] args) {
        GUI gui = new GUI(COOK_COUNT, CASHIER_COUNT);
        Cafeteria cafeteria = new Cafeteria(COOK_COUNT, CASHIER_COUNT, TABLE_SIZE, gui);

        // Create and start customer threads
        List<Thread> customerThreads = new ArrayList<>();
        for (int i = 1; i <= CUSTOMER_COUNT; i++) {
            Customer customer = new Customer(cafeteria);
            Thread customerThread = new Thread(customer);
            customerThreads.add(customerThread);
            customerThread.start();
        }

        gui.updateTableData("Balls ");
    }
}

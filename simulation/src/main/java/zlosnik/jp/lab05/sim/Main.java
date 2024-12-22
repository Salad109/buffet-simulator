package zlosnik.jp.lab05.sim;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int COOK_COUNT = 4;
    private static final int CASHIER_COUNT = 3;
    private static final int CUSTOMER_COUNT = 12;
    private static final int TABLE_LENGTH = 2;

    public static void main(String[] args) {
        GUI gui = new GUI();
        Cafeteria cafeteria = new Cafeteria(COOK_COUNT, CASHIER_COUNT, TABLE_LENGTH, gui);
        gui.setCafeteria(cafeteria);

        // Create and start customer threads
        List<Thread> customerThreads = new ArrayList<>();
        for (int i = 1; i <= CUSTOMER_COUNT; i++) {
            Customer customer = new Customer(cafeteria, gui);
            Thread customerThread = new Thread(customer);
            customerThreads.add(customerThread);
            customerThread.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

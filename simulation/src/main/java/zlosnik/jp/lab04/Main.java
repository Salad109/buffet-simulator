package zlosnik.jp.lab04;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int COOK_COUNT = 4;
    private static final int CASHIER_COUNT = 3;
    private static final int CUSTOMER_COUNT = 8;
    private static final int TABLE_SIZE = 16;
    public static void main(String[] args) {
        Cafeteria cafeteria = new Cafeteria(COOK_COUNT, CASHIER_COUNT, TABLE_SIZE);

        // Create and start customer threads
        List<Thread> customerThreads = new ArrayList<>();
        for (int i = 1; i <= CUSTOMER_COUNT; i++) {
            Customer customer = new Customer(cafeteria);
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

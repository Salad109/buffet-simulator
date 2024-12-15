package zlosnik.jp.lab04;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Cashier cashier = new Cashier();

        // Create and start cashier thread
        Thread cashierThread = new Thread(cashier);
        cashierThread.start();

        // Create and start customer threads
        List<Thread> customerThreads = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Customer customer = new Customer(cashier);
            Thread customerThread = new Thread(customer);
            customerThreads.add(customerThread);
            customerThread.start();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Wait for all customer threads to finish
        for (Thread thread : customerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Wait for cashier thread to finish
        try {
            cashierThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
package zlosnik.jp.lab04;

import java.util.ArrayList;
import java.util.List;

public class Cafeteria {
    List<Cashier> cashiers = new ArrayList<>();

    Cafeteria(int cashierCount) {
        for (int i = 0; i < cashierCount; i++) {
            Cashier cashier = new Cashier(this);
            cashiers.add(cashier);
            Thread cashierThread = new Thread(cashier);
            cashierThread.start();
        }
    }

    synchronized Cashier getReadyCashier() {
        int smallestQueue = Integer.MAX_VALUE;
        Cashier readyCashier = null;
        for (Cashier cashier : cashiers) {
            int queueSize = cashier.getTotalCustomers();
            if (queueSize < smallestQueue) {
                smallestQueue = queueSize;
                readyCashier = cashier;
            }
        }
        return readyCashier;
    }

    synchronized void alertUpdate() {
        System.out.println("==================================================");
        for (Cashier c : cashiers) {
            System.out.println("Cashier " + c.getCashierId() + ": Queue: " + c.customerQueue + " Current: " + c.getCurrentCustomer() + " Serviced: " + c.servicedCustomers);
        }
    }
}
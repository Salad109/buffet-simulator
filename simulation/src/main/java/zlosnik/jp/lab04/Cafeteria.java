package zlosnik.jp.lab04;

import java.util.ArrayList;
import java.util.List;

public class Cafeteria {
    List<Cook> cooks = new ArrayList<>();
    List<Cashier> cashiers = new ArrayList<>();

    Cafeteria(int cooksCount, int cashierCount) {
        for (int i = 0; i < cooksCount; i++) {
            Cook cook = new Cook(this);
            cooks.add(cook);
            Thread cookThread = new Thread(cook);
            cookThread.start();
        }
        for (int i = 0; i < cashierCount; i++) {
            Cashier cashier = new Cashier(this);
            cashiers.add(cashier);
            Thread cashierThread = new Thread(cashier);
            cashierThread.start();
        }
    }

    synchronized Cook getReadyCook() {
        int smallestQueue = Integer.MAX_VALUE;
        Cook readyCook = null;
        for (Cook cook : cooks) {
            int queueSize = cook.getTotalCustomers();
            if (queueSize < smallestQueue) {
                smallestQueue = queueSize;
                readyCook = cook;
            }
        }
        return readyCook;
    }

    synchronized Cashier getReadyCashier(){
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
        System.out.println("=================================================================");
        List<Worker> workers = new ArrayList<>();
        workers.addAll(cooks);
        workers.addAll(cashiers);
        for (Worker worker : workers) {
            System.out.println(worker);
        }
    }
}
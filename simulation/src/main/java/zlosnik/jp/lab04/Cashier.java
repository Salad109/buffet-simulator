package zlosnik.jp.lab04;

import java.util.LinkedList;
import java.util.Queue;

public class Cashier implements Runnable {
    private final Queue<Character> customerQueue = new LinkedList<>();
    private final Queue<Character> servicedCustomers = new LinkedList<>();
    private static final int CAPACITY = 5; // Max queue capacity for customers
    private Character currentCustomer = null;

    // Method for customers to join the queue
    public synchronized void joinQueue(char customer) throws InterruptedException {
        while (customerQueue.size() == CAPACITY) {
            System.out.println(customer + " is waiting: queue full!");
            wait(); // Wait if queue is full
        }

        customerQueue.add(customer);
        notifyAll(); // Notify cashier that there is a customer
        printStatus("JOINED", customer);
    }

    // Method for cashier to service a customer
    public synchronized char serviceCustomer() throws InterruptedException {
        while (customerQueue.isEmpty()) {
            System.out.println("Cashier is waiting: no customers.");
            wait(); // Wait if queue is empty
        }

        currentCustomer = customerQueue.poll(); // Dequeue customer
        notifyAll(); // Notify customers that there is space in the queue
        printStatus("SERVICING", currentCustomer);
        return currentCustomer;
    }

    // Method for cashier to mark a customer as serviced
    public synchronized void markServiced(char customer) {
        if (currentCustomer != null) {
            servicedCustomers.add(customer);
            currentCustomer = null;
            printStatus("SERVED", customer);
        }
    }

    // Print the full status of the queue system
    private synchronized void printStatus(String action, char customer) {
        System.out.println("\n======== Status Update ========");
        System.out.println("Action: " + customer + " - " + action);
        System.out.println("Waiting Queue: " + customerQueue);
        System.out.println("Current Customer Being Serviced: " + (currentCustomer != null ? currentCustomer : "None"));
        System.out.println("Serviced Customers: " + servicedCustomers);
        System.out.println("===============================\n");
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                char customer = serviceCustomer();
                Thread.sleep(1000); // Simulate service time
                markServiced(customer);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
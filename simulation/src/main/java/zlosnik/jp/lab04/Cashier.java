package zlosnik.jp.lab04;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Cashier implements Runnable {
    private final int cashierId;
    private static int nextId = 1;
    final Queue<Character> customerQueue = new LinkedList<>();
    final Queue<Character> servicedCustomers = new LinkedList<>();
    private static final int CAPACITY = 5; // Max queue capacity for customers
    Character currentCustomer = null;
    private final Cafeteria cafeteria;

    Cashier(Cafeteria cafeteria) {
        this.cashierId = nextId++;
        this.cafeteria = cafeteria;
    }

    // Method for customers to join the queue
    public synchronized void joinQueue(char customer) throws InterruptedException {
        while (customerQueue.size() == CAPACITY) {
            System.out.println(customer + " is waiting: queue full!");
            wait(); // Wait if queue is full
        }

        customerQueue.add(customer);
        notifyAll(); // Notify cashier that there is a customer
        cafeteria.alertUpdate(); // Alert cafeteria
    }

    // Method for cashier to service a customer
    public synchronized char serviceCustomer() throws InterruptedException {
        while (customerQueue.isEmpty()) {
            System.out.println("Cashier " + cashierId + " is waiting: no customers.");
            wait(); // Wait if queue is empty
        }

        currentCustomer = customerQueue.poll(); // Dequeue customer
        notifyAll(); // Notify customers that there is space in the queue
        cafeteria.alertUpdate(); // Alert cafeteria
        return currentCustomer;
    }

    // Method for cashier to mark a customer as serviced
    public synchronized void markServiced(char customer) {
        if (currentCustomer != null) {
            servicedCustomers.add(customer);
            currentCustomer = null;
            cafeteria.alertUpdate(); // Alert cafeteria
        }
    }

    int getTotalCustomers() {
        int customers = customerQueue.size();
        if (currentCustomer != null) {
            customers++;
        }
        return customers;
    }

    int getCashierId() {
        return cashierId;
    }

    List<Character> getCurrentCustomer() {
        List<Character> customers = new ArrayList<>();
        if (currentCustomer != null) {
            customers.add(currentCustomer);
        }
        return customers;
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
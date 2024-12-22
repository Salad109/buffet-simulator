package zlosnik.jp.lab05;

import java.util.*;

public abstract class Worker implements Runnable {
    private final int workerId;
    private static int nextId = 1;
    final Queue<Character> customerQueue = new LinkedList<>();
    final Queue<Character> servicedCustomers = new LinkedList<>();
    Character currentCustomer = null;
    private final Cafeteria cafeteria;
    Random rng = new Random();

    Worker(Cafeteria cafeteria) {
        this.workerId = nextId++;
        this.cafeteria = cafeteria;
    }

    // Method for customers to join the queue
    public synchronized void joinQueue(char customer) throws InterruptedException {
        customerQueue.add(customer);
        notifyAll(); // Notify worker that there is a customer
        cafeteria.alertUpdate(); // Alert cafeteria
    }

    // Method for worker to service a customer
    public synchronized char serviceCustomer() throws InterruptedException {
        while (customerQueue.isEmpty()) {
            wait(); // Wait if queue is empty
        }

        currentCustomer = customerQueue.poll(); // Dequeue customer
        notifyAll(); // Notify customers that there is space in the queue
        cafeteria.alertUpdate(); // Alert cafeteria
        return currentCustomer;
    }

    // Method for worker to mark a customer as serviced
    public synchronized void markServiced(char customer) {
        if (currentCustomer != null) {
            servicedCustomers.add(customer);
            currentCustomer = null;
            // cafeteria.alertUpdate(); // Alert cafeteria
        }
    }

    int getTotalCustomers() {
        int customers = customerQueue.size();
        if (currentCustomer != null) {
            customers++;
        }
        return customers;
    }

    int getWorkerId() {
        return workerId;
    }

    List<Character> getCurrentCustomer() {
        List<Character> customers = new ArrayList<>();
        if (currentCustomer != null) {
            customers.add(currentCustomer);
        }
        return customers;
    }
}
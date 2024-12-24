package zlosnik.jp.lab05.sim;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Worker implements Runnable {
    static final Random rng = new Random();
    final ConcurrentLinkedQueue<Character> customerQueue;
    final ConcurrentLinkedQueue<Character> servicedCustomers;
    final GUI gui;
    Character currentCustomer;

    Worker(GUI gui) {
        this.gui = gui;
        currentCustomer = null;
        customerQueue = new CustomConcurrentLinkedQueue();
        servicedCustomers = new CustomConcurrentLinkedQueue();
    }

    // Method for customers to join the queue
    public synchronized void joinQueue(char customer) {
        customerQueue.add(customer);
        notifyAll(); // Notify worker that there is a customer
    }

    // Method for worker to service a customer
    public synchronized char serviceCustomer() throws InterruptedException {
        while (customerQueue.isEmpty()) {
            wait(); // Wait if queue is empty
        }

        currentCustomer = customerQueue.poll(); // Dequeue customer
        notifyAll(); // Notify customers that there is space in the queue
        return currentCustomer;
    }

    // Method for worker to mark a customer as serviced
    public synchronized void markServiced(char customer) {
        if (currentCustomer != null) {
            servicedCustomers.add(customer);
            currentCustomer = null;
            // cafeteria.alertUpdate(); // Alert cafeteria
            notifyAll();
        }
    }

    synchronized int getTotalCustomers() {
        int customers = customerQueue.size();
        if (currentCustomer != null) {
            customers++;
        }
        return customers;
    }

    public synchronized void leaveQueue(char customer) {
        while (!servicedCustomers.contains(customer)) {
            try {
                wait(); // Wait until the customer is serviced
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        servicedCustomers.remove(customer);
        notifyAll();
    }

}
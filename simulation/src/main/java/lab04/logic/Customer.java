package lab04.logic;

import java.util.Random;

public class Customer implements Runnable {
    private final int customerId;
    private static int nextId = 0;
    private static final Random random = new Random();

    public Customer() {
        this.customerId = ++nextId;
    }

    public void run() {
        int iteration = 0;
        while (iteration < 10) {
            try {
                System.out.println("Customer " + customerId + " is doing something for the " + iteration++ + " time");
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Customer " + customerId + " finished");
    }
}
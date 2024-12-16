package zlosnik.jp.lab04;

public class Customer implements Runnable {
    private final char customerId;
    private static char nextId = 'a';
    private final Cafeteria cafeteria;

    public Customer(Cafeteria cafeteria) {
        this.customerId = nextId++;
        this.cafeteria = cafeteria;
    }

    @Override
    public void run() {
        try {
            Cashier cashier = cafeteria.getReadyCashier();
            cashier.joinQueue(customerId);
            while (cashier.customerQueue.contains(customerId) || (cashier.currentCustomer != null && cashier.currentCustomer.equals(customerId))) {
                Thread.yield();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
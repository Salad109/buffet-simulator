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
            Worker cook = cafeteria.getReadyCook();
            cook.joinQueue(customerId);
            while (cook.customerQueue.contains(customerId) || (cook.currentCustomer != null && cook.currentCustomer.equals(customerId))) {
                Thread.sleep(100);
            }
            Worker cashier = cafeteria.getReadyCashier();
            cashier.joinQueue(customerId);
            while (cashier.customerQueue.contains(customerId) || (cashier.currentCustomer != null && cashier.currentCustomer.equals(customerId))) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
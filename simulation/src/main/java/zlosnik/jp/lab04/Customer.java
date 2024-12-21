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
            while (!cook.servicedCustomers.contains(customerId)) {
                Thread.sleep(100);
            }
            cook.servicedCustomers.remove(customerId);

            Worker cashier = cafeteria.getReadyCashier();
            cashier.joinQueue(customerId);
            while (!cashier.servicedCustomers.contains(customerId)) {
                Thread.sleep(100);
            }
            cashier.servicedCustomers.remove(customerId);

            cafeteria.table.sit(customerId);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
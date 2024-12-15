package zlosnik.jp.lab04;

public class Customer implements Runnable {
    private final char customerId;
    private static char nextId = 'a';
    private final Cashier cashier;

    public Customer(Cashier cashier) {
        this.customerId = nextId++;
        this.cashier = cashier;
    }

    @Override
    public void run() {
        try {
            cashier.joinQueue(customerId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
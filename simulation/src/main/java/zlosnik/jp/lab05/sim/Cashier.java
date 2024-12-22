package zlosnik.jp.lab05.sim;

public class Cashier extends Worker {
    Cashier() {
        super();
    }

    @Override
    public String toString() {
        return "Cashier " + getWorkerId() + ": Queue: " + customerQueue + " Current: " + getCurrentCustomer();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                char customer = serviceCustomer();
                Thread.sleep(3000 + rng.nextInt(1000)); // Simulate service time
                markServiced(customer);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Preserve interrupt status
            System.err.println("Cashier thread interrupted: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
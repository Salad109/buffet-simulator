package zlosnik.jp.lab04;

public class Cook extends Worker {
    Cook(Cafeteria cafeteria) {
        super(cafeteria);
    }

    @Override
    public String toString() {
        return "Cook " + getWorkerId() + ": Queue: " + customerQueue + " Current: " + getCurrentCustomer();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                char customer = serviceCustomer();
                Thread.sleep(1000 + rng.nextInt(3000)); // Simulate service time
                markServiced(customer);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Preserve interrupt status
            System.err.println("Cook thread interrupted: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
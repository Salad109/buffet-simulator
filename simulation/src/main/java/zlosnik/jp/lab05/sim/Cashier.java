package zlosnik.jp.lab05.sim;

public class Cashier extends Worker {
    private static int nextCashierId = 1;
    private final int cashierId;

    Cashier(GUI gui) {
        super(gui);
        cashierId = nextCashierId++;
    }

    @Override
    public String toString() {
        char current = currentCustomer == null ? ' ' : currentCustomer;
        return "Cashier " + cashierId + ": " + customerQueue + " -> " + current;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                char customer = serviceCustomer();
                gui.update();
                Thread.sleep((long) 5000 + rng.nextInt(5000)); // Simulate service time
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
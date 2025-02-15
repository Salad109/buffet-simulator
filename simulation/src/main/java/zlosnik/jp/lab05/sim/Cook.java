package zlosnik.jp.lab05.sim;

public class Cook extends Worker {
    private static int nextCookId = 1;

    Cook(GUI gui) {
        super(gui, nextCookId++);
    }

    @Override
    public String toString() {
        char current = currentCustomer == null ? ' ' : currentCustomer;
        return "Cook " + getId() + ": " + customerQueue + " -> " + current;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                char customer = serviceCustomer();
                gui.update();
                Thread.sleep((long) 2000 + rng.nextInt(3000)); // Simulate service time
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
package zlosnik.jp.lab05.sim;

public class Customer implements Runnable {
    private final char customerId;
    private static char nextId = 'a';
    private final Cafeteria cafeteria;
    private final GUI gui;

    public Customer(Cafeteria cafeteria, GUI gui) {
        this.customerId = nextId++;
        this.cafeteria = cafeteria;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                cafeteria.outsideQueue.add(customerId);
                gui.update();
                Thread.sleep(3000);
                gui.update();

                Worker cook = null;
                while (cook == null) {
                    cook = cafeteria.getReadyCook();
                    if (cook == null) {
                        Thread.sleep(250);
                    }
                }
                cafeteria.outsideQueue.remove(customerId);
                cook.joinQueue(customerId);
                gui.update();
                cook.leaveQueue(customerId);
                gui.update();

                Worker cashier = null;
                while (cashier == null) {
                    cashier = cafeteria.getReadyCashier();
                    if (cashier == null) {
                        Thread.sleep(250);
                    }
                }
                cashier.joinQueue(customerId);
                gui.update();
                cashier.leaveQueue(customerId);
                gui.update();

                Table table = cafeteria.getReadyTable();
                table.sit(customerId);
                Thread.sleep(10000);
                table.leave(customerId);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Preserve interrupt status
            System.err.println("Customer thread interrupted: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
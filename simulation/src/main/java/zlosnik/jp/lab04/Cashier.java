package zlosnik.jp.lab04;

public class Cashier extends Worker {
    Cashier(Cafeteria cafeteria) {
        super(cafeteria);
    }

    @Override
    public String toString() {
        return "Cashier " + getWorkerId() + ": Queue: " + customerQueue + " Current: " + getCurrentCustomer() + " Serviced: " + servicedCustomers;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char customer = serviceCustomer();
                Thread.sleep(5000); // Simulate service time
                markServiced(customer);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

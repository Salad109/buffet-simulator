package zlosnik.jp.lab04;

public class Cook extends Worker {
    Cook(Cafeteria cafeteria) {
        super(cafeteria);
    }

    @Override
    public String toString() {
        return "Cook " + getWorkerId() + ": Queue: " + customerQueue + " Current: " + getCurrentCustomer() + " Serviced: " + servicedCustomers;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char customer = serviceCustomer();
                Thread.sleep(1000); // Simulate service time
                markServiced(customer);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

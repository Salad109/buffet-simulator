package lab04.gui;

import zlosnik.jp.lab04.logic.Customer;


public class Main {
    public static void main(String[] args) {
        Customer customer1 = new Customer();
        Thread thread1 = new Thread(customer1);
        thread1.start();

        Customer customer2 = new Customer();
        Thread thread2 = new Thread(customer2);
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        System.out.println("End of the program");
    }
}

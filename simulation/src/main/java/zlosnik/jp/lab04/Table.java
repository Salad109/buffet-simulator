package zlosnik.jp.lab04;

import java.util.*;

public class Table {
    Queue<Character> table;
    Queue<Character> tableQueue; // todo
    Cafeteria cafeteria;
    int size;

    Table(int size, Cafeteria cafeteria) {
        table = new LinkedList<>();
        tableQueue = new LinkedList<>();
        this.size = size;
        this.cafeteria = cafeteria;
    }

    public synchronized void sit(char customer) throws InterruptedException {
        while (table.size() == 20) {
            System.out.println("Table is full!");
            wait(); // Wait if table is full
        }
        table.add(customer);
        cafeteria.alertUpdate(); // Alert cafeteria
        notifyAll(); // Notify customer that there is a customer
    }

    public synchronized void leave(char customer) {
        table.remove(customer);
        cafeteria.alertUpdate(); // Alert cafeteria
        notifyAll(); // Notify customer that there is a free space
    }

    @Override
    public String toString() {
        // Safely iterate over the first half of the table
        Iterator<Character> it = table.iterator();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size / 2; i++) {
            if (it.hasNext()) {
                builder.append(it.next());
            } else {
                builder.append("-");
            }
        }

        builder.append("\n");
        for (int i = 0; i < size / 2; i++) {
            builder.append("=");
        }

        builder.append("\n");

        // Safely iterate over the second half of the table
        for (int i = size / 2; i < size; i++) {
            if (it.hasNext()) {
                builder.append(it.next());
            } else {
                builder.append("-");
            }
        }

        return builder.toString();

    }

}

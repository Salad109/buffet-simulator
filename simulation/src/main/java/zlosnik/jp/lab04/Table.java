package zlosnik.jp.lab04;

import java.util.*;

public class Table {
    Queue<Character> table;
    Queue<Character> tableQueue;
    Cafeteria cafeteria;
    int tableSeatCount;

    Table(int tableSeatCount, Cafeteria cafeteria) {
        table = new LinkedList<>();
        tableQueue = new LinkedList<>();
        this.tableSeatCount = tableSeatCount;
        this.cafeteria = cafeteria;
    }

    public synchronized void sit(char customer) throws InterruptedException {
        tableQueue.add(customer);
        cafeteria.alertUpdate(); // Alert cafeteria
        while (table.size() == tableSeatCount) {
            wait(); // Wait if table is full
        }
        tableQueue.remove(customer);
        table.add(customer);
        notifyAll(); // Notify customer that there is a customer
        cafeteria.alertUpdate(); // Alert cafeteria
    }

    public synchronized void leave(char customer) {
        table.remove(customer);
        cafeteria.alertUpdate(); // Alert cafeteria
        notifyAll(); // Notify customer that there is a free space
    }

    @Override
    public String toString() {
        // Print the table queue
        for (char c : tableQueue) {
            System.out.print(c);
        }
        System.out.println('\n');

        // Safely iterate over the first half of the table
        Iterator<Character> it = table.iterator();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tableSeatCount / 2; i++) {
            if (it.hasNext()) {
                builder.append(it.next());
            } else {
                builder.append("-");
            }
        }

        builder.append("\n");
        for (int i = 0; i < tableSeatCount / 2; i++) {
            builder.append("=");
        }

        builder.append("\n");

        // Safely iterate over the second half of the table
        for (int i = tableSeatCount / 2; i < tableSeatCount; i++) {
            if (it.hasNext()) {
                builder.append(it.next());
            } else {
                builder.append("-");
            }
        }

        return builder.toString();

    }

}

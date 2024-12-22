package zlosnik.jp.lab05;

import java.util.*;

public class Cafeteria {
    List<Character> outsideQueue = new LinkedList<>(); // todo
    List<Cook> cooks = new ArrayList<>();
    List<Cashier> cashiers = new ArrayList<>();
    Table table;
    GUI gui;

    Cafeteria(int cooksCount, int cashierCount, int tableSize, GUI gui) {
        this.gui = gui;
        for (int i = 0; i < cooksCount; i++) {
            Cook cook = new Cook(this);
            cooks.add(cook);
            Thread cookThread = new Thread(cook);
            cookThread.start();
        }
        for (int i = 0; i < cashierCount; i++) {
            Cashier cashier = new Cashier(this);
            cashiers.add(cashier);
            Thread cashierThread = new Thread(cashier);
            cashierThread.start();
        }
        table = new Table(tableSize, this);
    }

    synchronized Cook getReadyCook() {
        int smallestQueue = Integer.MAX_VALUE;
        Cook readyCook = null;
        for (Cook cook : cooks) {
            int queueSize = cook.getTotalCustomers();
            if (queueSize < smallestQueue) {
                smallestQueue = queueSize;
                readyCook = cook;
            }
        }
        return readyCook;
    }

    synchronized Cashier getReadyCashier() {
        int smallestQueue = Integer.MAX_VALUE;
        Cashier readyCashier = null;
        for (Cashier cashier : cashiers) {
            int queueSize = cashier.getTotalCustomers();
            if (queueSize < smallestQueue) {
                smallestQueue = queueSize;
                readyCashier = cashier;
            }
        }
        return readyCashier;
    }

    synchronized void alertUpdate() {
        System.out.println("=================================================================");

        for(int i = 0; i < cooks.size(); i++) {
            Cook cook = cooks.get(i);
            System.out.println(cook);
            gui.updateTableData(cook.toString(), i, 1);
        }

        System.out.print("\n");

        for(int i = 0; i < cashiers.size(); i++) {
            Cashier cashier = cashiers.get(i);
            System.out.println(cashier);
            gui.updateTableData(cashier.toString(), i, 2);
        }

        System.out.print("\n");

        System.out.println(table);
    }

    class Table {
        Queue<Character> tableSeats;
        Queue<Character> tableQueue;
        Cafeteria cafeteria;
        int tableSeatCount;

        Table(int tableSeatCount, Cafeteria cafeteria) {
            tableSeats = new LinkedList<>();
            tableQueue = new LinkedList<>();
            this.tableSeatCount = tableSeatCount;
            this.cafeteria = cafeteria;
        }

        public synchronized void sit(char customer) throws InterruptedException {
            tableQueue.add(customer);
            cafeteria.alertUpdate(); // Alert cafeteria
            while (tableSeats.size() == tableSeatCount) {
                wait(); // Wait if tableSeats is full
            }
            tableQueue.remove(customer);
            tableSeats.add(customer);
            notifyAll(); // Notify customer that there is a customer
            cafeteria.alertUpdate(); // Alert cafeteria
        }

        public synchronized void leave(char customer) {
            tableSeats.remove(customer);
            cafeteria.alertUpdate(); // Alert cafeteria
            notifyAll(); // Notify customer that there is a free space
        }

        @Override
        public String toString() {
            // Print the tableSeats queue
            for (char c : tableQueue) {
                System.out.print(c);
            }
            System.out.println('\n');

            // Safely iterate over the first half of the tableSeats
            Iterator<Character> it = tableSeats.iterator();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < tableSeatCount / 2; i++) {
                if (it.hasNext()) {
                    builder.append(it.next());
                } else {
                    builder.append("-");
                }
            }

            builder.append("\n");
            builder.append("=".repeat(tableSeatCount / 2));
            builder.append("\n");

            // Safely iterate over the second half of the tableSeats
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

}
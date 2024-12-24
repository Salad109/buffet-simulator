package zlosnik.jp.lab05.sim;


import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Table {
    ConcurrentLinkedQueue<Character> tableSeats;
    ConcurrentLinkedQueue<Character> tableQueues;
    Cafeteria cafeteria;
    final int tableLength;
    GUI gui;

    Table(int tableLength, Cafeteria cafeteria, GUI gui) {
        tableSeats = new CustomConcurrentLinkedQueue();
        tableQueues = new CustomConcurrentLinkedQueue();
        this.tableLength = tableLength;
        this.cafeteria = cafeteria;
        this.gui = gui;
    }

    public synchronized void sit(char customer) throws InterruptedException {
        tableQueues.add(customer);
        gui.update();
        while (tableSeats.size() == tableLength * 2) {
            wait(); // Wait if tableSeats is full
        }
        tableQueues.remove(customer);
        tableSeats.add(customer);
        gui.update();
        notifyAll(); // Notify customer that there is a customer
    }

    public synchronized void leave(char customer) {
        tableSeats.remove(customer);
        gui.update();
        notifyAll(); // Notify customer that there is a free space
    }

    public synchronized String getFirstHalfOfTableSeats() {
        Iterator<Character> it = tableSeats.iterator();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tableLength; i++) {
            if (it.hasNext()) {
                builder.append(it.next());
            } else {
                builder.append("-");
            }
        }
        return builder.toString();
    }

    public synchronized String getSeparator() {
        return "=".repeat(tableLength);
    }

    public synchronized String getSecondHalfOfTableSeats() {
        Iterator<Character> it = tableSeats.iterator();
        for (int i = 0; i < tableLength; i++) {
            if (it.hasNext()) {
                it.next();
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int i = tableLength; i < tableLength * 2; i++) {
            if (it.hasNext()) {
                builder.append(it.next());
            } else {
                builder.append("-");
            }
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return getFirstHalfOfTableSeats() + "\n" + getSeparator() + "\n" + getSecondHalfOfTableSeats();
    }

}
package zlosnik.jp.lab05.sim;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Cafeteria {
    ConcurrentLinkedQueue<Character> outsideQueue = new ConcurrentLinkedQueue<>();
    List<Cook> cooks = new ArrayList<>();
    List<Cashier> cashiers = new ArrayList<>();
    List<Table> tables = new ArrayList<>();
    GUI gui;

    Cafeteria(int cooksCount, int cashierCount, int tableSize, GUI gui) {
        this.gui = gui;
        for (int i = 0; i < cooksCount; i++) {
            Cook cook = new Cook();
            cooks.add(cook);
            Thread cookThread = new Thread(cook);
            cookThread.start();
        }
        for (int i = 0; i < cashierCount; i++) {
            Cashier cashier = new Cashier();
            cashiers.add(cashier);
            Thread cashierThread = new Thread(cashier);
            cashierThread.start();
        }

        tables.add(new Table(tableSize, this, gui));
        tables.add(new Table(tableSize, this, gui));
    }

    synchronized Cook getReadyCook() {
        int smallestQueue = Integer.MAX_VALUE;
        Cook readyCook = null;
        for (Cook cook : cooks) {
            int queueSize = cook.getTotalCustomers();
            if(cook.currentCustomer != null) {
                queueSize++;
            }
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
            if(cashier.currentCustomer != null) {
                queueSize++;
            }
            if (queueSize < smallestQueue) {
                smallestQueue = queueSize;
                readyCashier = cashier;
            }
        }
        return readyCashier;
    }

    synchronized Table getReadyTable() {
        int smallestQueue = Integer.MAX_VALUE;
        Table readyTable = null;
        for (Table table : tables) {
            int queueSize = table.tableSeats.size() + table.tableQueues.size();
            if (queueSize < smallestQueue) {
                smallestQueue = queueSize;
                readyTable = table;
            }
        }
        return readyTable;
    }


    public synchronized SimulationStatus getSimulationStatus() {
        SimulationStatus status = new SimulationStatus();
        status.outsideQueue = outsideQueue.toString();
        for (Cook cook : cooks) {
            status.cooks.add(cook.toString());
        }
        for (Cashier cashier : cashiers) {
            status.cashiers.add(cashier.toString());
        }
        for (Table table : tables) {
            status.tableQueue.add(table.tableQueues.toString());
            status.tableSeats.add(table.getFirstHalfOfTableSeats());
            status.tableSeats.add(table.getSeparator());
            status.tableSeats.add(table.getSecondHalfOfTableSeats());
        }
        return status;
    }


}
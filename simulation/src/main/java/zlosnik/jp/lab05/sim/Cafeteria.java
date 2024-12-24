package zlosnik.jp.lab05.sim;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Cafeteria {
    final ConcurrentLinkedQueue<Character> outsideQueue = new CustomConcurrentLinkedQueue();
    public final List<Cook> cooks = new ArrayList<>();
    public final List<Cashier> cashiers = new ArrayList<>();
    final List<Table> tables = new ArrayList<>();
    final GUI gui;

    public Cafeteria(int cooksCount, int cashierCount, int tableSize, GUI gui) {
        this.gui = gui;
        for (int i = 0; i < cooksCount; i++) {
            Cook cook = new Cook(gui);
            cooks.add(cook);
            Thread cookThread = new Thread(cook);
            cookThread.start();
        }
        for (int i = 0; i < cashierCount; i++) {
            Cashier cashier = new Cashier(gui);
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
    status.setOutsideQueue(outsideQueue.toString());

    List<String> cooksList = new ArrayList<>();
    for (Cook cook : cooks) {
        cooksList.add(cook.toString());
    }
    status.setCooks(cooksList);

    List<String> cashiersList = new ArrayList<>();
    for (Cashier cashier : cashiers) {
        cashiersList.add(cashier.toString());
    }
    status.setCashiers(cashiersList);

    List<String> tableQueueList = new ArrayList<>();
    List<String> tableSeatsList = new ArrayList<>();
    for (Table table : tables) {
        tableQueueList.add(table.tableQueues.toString());
        tableSeatsList.add(table.getFirstHalfOfTableSeats());
        tableSeatsList.add(table.getSeparator());
        tableSeatsList.add(table.getSecondHalfOfTableSeats());
    }
    status.setTableQueue(tableQueueList);
    status.setTableSeats(tableSeatsList);

    return status;
}


}
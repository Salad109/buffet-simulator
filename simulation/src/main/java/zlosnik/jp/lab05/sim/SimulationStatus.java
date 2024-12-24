package zlosnik.jp.lab05.sim;

import java.util.ArrayList;
import java.util.List;

public class SimulationStatus {
    private String outsideQueue;
    private List<String> cashiers;
    private List<String> cooks;
    private List<String> tableQueue;
    private List<String> tableSeats;

    public String getOutsideQueue() {
        return outsideQueue;
    }

    void setOutsideQueue(String outsideQueue) {
        this.outsideQueue = outsideQueue;
    }

    public List<String> getCashiers() {
        return cashiers;
    }

    void setCashiers(List<String> cashiers) {
        this.cashiers = cashiers;
    }

    public List<String> getCooks() {
        return cooks;
    }

    void setCooks(List<String> cooks) {
        this.cooks = cooks;
    }

    public List<String> getTableQueue() {
        return tableQueue;
    }

    void setTableQueue(List<String> tableQueue) {
        this.tableQueue = tableQueue;
    }

    public List<String> getTableSeats() {
        return tableSeats;
    }

    void setTableSeats(List<String> tableSeats) {
        this.tableSeats = tableSeats;
    }

    public SimulationStatus() {
        outsideQueue = "";
        cashiers = new ArrayList<>();
        cooks = new ArrayList<>();
        tableQueue = new ArrayList<>();
        tableSeats = new ArrayList<>();
    }
}

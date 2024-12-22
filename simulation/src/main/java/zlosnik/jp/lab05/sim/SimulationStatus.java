package zlosnik.jp.lab05.sim;

import java.util.ArrayList;
import java.util.List;

public class SimulationStatus {
    String outsideQueue;
    List<String> cashiers;
    List<String> cooks;
    List<String> tableQueue;
    List<String> tableSeats;

    public SimulationStatus() {
        outsideQueue = "";
        cashiers = new ArrayList<>();
        cooks = new ArrayList<>();
        tableQueue = new ArrayList<>();
        tableSeats = new ArrayList<>();
    }
}

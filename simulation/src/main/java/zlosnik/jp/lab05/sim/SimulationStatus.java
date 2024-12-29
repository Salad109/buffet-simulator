package zlosnik.jp.lab05.sim;

import java.util.List;

public record SimulationStatus(
        String outsideQueue,
        List<String> cashiers,
        List<String> cooks,
        List<String> tableQueue,
        List<String> tableSeats
) {
    public SimulationStatus() {
        this("", List.of(), List.of(), List.of(), List.of());
    }
}
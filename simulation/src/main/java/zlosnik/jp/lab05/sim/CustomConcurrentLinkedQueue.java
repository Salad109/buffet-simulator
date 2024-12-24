package zlosnik.jp.lab05.sim;

import java.util.concurrent.ConcurrentLinkedQueue;

public class CustomConcurrentLinkedQueue extends ConcurrentLinkedQueue<Character> {
    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (Character c : this) {
            builder.append(c);
            builder.append(" ");
        }
        return builder.toString();
    }
}

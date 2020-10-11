package nba;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count;

    public CASCount() {
        count = new AtomicReference<>(0);
    }

    public void increment() {
        int currentValue;
        int newVal;
        do {
            currentValue = count.get();
            newVal = currentValue + 1;
        } while (count.compareAndSet(currentValue, newVal));
    }

    public int get() {
        return count.get();
    }
}
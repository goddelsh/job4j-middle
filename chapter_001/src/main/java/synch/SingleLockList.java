package synch;

import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;



@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    final private DynamicContainer<T> container;

    public SingleLockList() {
        this.container = new DynamicContainer<T>();
    }

    public synchronized void add(T value) {
        this.container.add(value);
    }

    public synchronized T get(int index) {
        return this.container.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return this.copy(this.container).iterator();
    }

    private DynamicContainer<T> copy(DynamicContainer<T> container) {
        DynamicContainer<T> result = new DynamicContainer<T>();
        container.forEach(result::add);
        return result;
    }
}
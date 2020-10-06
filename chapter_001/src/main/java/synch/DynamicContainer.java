package synch;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class DynamicContainer<E> implements  Iterable<E> {
    private int modCount, expectedModCount;
    private Object[] container;
    private int defaultSize = 10;
    private int index = 0;


    public DynamicContainer() {
        container = new Object[defaultSize];
    }

    public DynamicContainer(int size) {
        this.defaultSize = size;
        container = new Object[defaultSize];
    }

    private void resizeAsNeeded() {
        if (index >= container.length) {
            container = Arrays.copyOf(container, container.length * 2);
        }
    }

    public void add(E value) {
        resizeAsNeeded();
        container[index++] = value;
        modCount++;
    }


    public int size() {
        return index;
    }

    public E get(int index) {
        return (E) container[index];
    }

    @Override
    public Iterator<E> iterator() {
        expectedModCount = modCount;
        return new DynamicContainerIterator();
    }


    private class DynamicContainerIterator implements Iterator<E> {
        private int iteratorIndex = 0;

        private void checkModCount() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            checkModCount();
            return iteratorIndex < index;
        }

        @Override
        public E next() {
            checkModCount();
            return get(iteratorIndex++);
        }
    }
}

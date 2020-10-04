package linked;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Node<T> {
    final private Node next;
    final private T value;

    public Node(Node next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }

}

package util;

import java.util.Collection;
import java.util.Vector;

public class PriorityList<E> extends Vector<E> {
    public PriorityList(int initialCapacity, int capacityIncrement) {
        super(initialCapacity, capacityIncrement);
    }

    public PriorityList(int initialCapacity) {
        super(initialCapacity);
    }

    public PriorityList() {
    }

    public PriorityList(Collection<? extends E> c) {
        super(c);
    }

    public void addLast(E e){
        addElement(e);
    }

    public void addWithPriority(int priority, E e){
        if (priority < 0)
            return;

        if (priority >= elementCount)
            addElement(e);
        else
            add(priority, e);
    }
}

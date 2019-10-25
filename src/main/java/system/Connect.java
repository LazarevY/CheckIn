package system;

import system.events.SystemEvent;
import util.PriorityList;


import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Vector;

/**
 * Класс, описывающий связь пользователей и системы
 * Каждому пользователю предоставляется соединение через которое он может
 * взаимодействовать с системой и отправлять запросы
 */
public class Connect {
    private PriorityQueue<SystemEvent> events;
    private PriorityQueue<SystemEvent> swapBuffer;

    public Connect(){
        events = new PriorityQueue<>(((o1, o2) -> Integer.compare(o1.getPriority(), o2.getPriority())));
        swapBuffer = new PriorityQueue<>(events.comparator());
    }
    public void addEvent(SystemEvent e){
            events.add(e);
    }

    public PriorityQueue<SystemEvent> getEvents() {
        PriorityQueue<SystemEvent> t = swapBuffer;
        swapBuffer = events;
        events = t;
        events.clear();
        return swapBuffer;
    }
}

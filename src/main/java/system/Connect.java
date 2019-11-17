package system;

import system.events.SystemEvent;


import java.util.ArrayList;
import java.util.List;

/**
 * Класс, описывающий связь пользователей и системы
 * Каждому пользователю предоставляется соединение через которое он может
 * взаимодействовать с системой и отправлять запросы
 */
public class Connect {
    public static final int USERS_LIMIT =  1000;
    private List<SystemEvent> events;
    private List<SystemEvent> swapBuffer;

    public Connect(){
        swapBuffer = new ArrayList<>();
        events = new ArrayList<>();
    }
    public void sendEvent(SystemEvent e){
            events.add(e);
    }

    public List<? extends SystemEvent> getEvents() {
        List<SystemEvent> t = swapBuffer;
        swapBuffer = events;
        events = t;
        events.clear();
        return swapBuffer;
    }
}

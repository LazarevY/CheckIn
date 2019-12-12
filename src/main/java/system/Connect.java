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
    private CheckInSystem system;

    public Connect(CheckInSystem system){
        this.system = system;
    }
    public synchronized void sendEvent(SystemEvent e){
        system.processEvent(e);
    }

}

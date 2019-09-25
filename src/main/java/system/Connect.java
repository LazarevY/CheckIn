package system;

import user.User;


import java.util.Vector;

/**
 * Класс, описывающий связь пользователей и системы
 * Каждому пользователю предоставляется соединение через которое он может
 * взаимодействовать с системой и отправлять запросы
 */
public class Connect {
    private Vector<Long> checkEvents;
    private Vector<Long> swapBuffer;

    public Connect(){
        checkEvents = new Vector<>();
    }
    public void addCheck(long userID){
            checkEvents.add(userID);
    }

    public Vector<Long> getCheckEvents() {
        Vector<Long> t = swapBuffer;
        swapBuffer = checkEvents;
        checkEvents = t;
        checkEvents.clear();
        return swapBuffer;
    }
}

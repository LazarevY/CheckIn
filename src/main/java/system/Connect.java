package system;

import user.User;


import java.util.Vector;

/**
 * Класс, описывающий связь пользователей и системы
 * Каждому пользователю предоставляется соединение через которое он может
 * взаимодействовать с системой и отправлять запросы
 */
public class Connect {
    private Vector<User> checkEvents;
    private Vector<User> swapBuffer;

    public Connect(){
        checkEvents = new Vector<>();
    }
    public void addCheck(User user){

    }

    public Vector<User> getCheckEvents() {
        Vector<User> t = swapBuffer;
        swapBuffer = checkEvents;
        checkEvents = t;
        return swapBuffer;
    }
}

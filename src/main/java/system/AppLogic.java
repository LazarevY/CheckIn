package system;

import map.Map;
import user.User;
import user.UserManagement;

import java.util.ArrayList;
import java.util.List;

public class AppLogic {

    private CheckInSystem checkInSystem;
    private Map map;
    private Connect connect;
    private List<User> usersList;
    private List<UserManagement> managements;
    private int currentUserIndex;

    public AppLogic(CheckInSystem system, Map map, List<User> usersList, List<UserManagement> managements){
        this.checkInSystem = system;
        this.map = map;
        this.managements = managements;
        connect = system.getConnect();
        system.setMap(map);
        this.usersList = usersList;
        currentUserIndex = 0;
    }

    public CheckInSystem getCheckInSystem() {
        return checkInSystem;
    }

    public User getCurrentUser(){
        return usersList.get(currentUserIndex);
    }

    public User nextUser(){
        if (++currentUserIndex >= usersList.size())
            currentUserIndex = 0;
        return getCurrentUser();
    }

    public User previousUser(){
        if (--currentUserIndex < 0)
            currentUserIndex = usersList.size() - 1;
        return getCurrentUser();
    }

    public void startUsersManagements(){
        for (UserManagement m : managements)
            m.start();
    }

    public Map getMap() {
        return map;
    }

    public List<User> getUsersList() {
        return usersList;
    }
}

package user;

import geometry.geojson.Point;
import system.Connect;
import map.objects.ObjectType;
import system.events.ChangeLocationEvent;
import system.events.CheckInEvent;
import util.PriorityList;

public class User {
    private UserData data;
    private Connect sourceConnect;
    private PriorityList<ObjectType> userPriority;
    private int userID;

    public User(UserData data, Connect sourceConnect, int userID) {
        this.data = data;
        this.sourceConnect = sourceConnect;
        this.userID = userID;
        userPriority = new PriorityList<>();
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public void checkIn(){
        sourceConnect.sendEvent(new CheckInEvent(userID));
    }

    public void setLocation(Point location){
        sourceConnect.sendEvent(new ChangeLocationEvent(userID, location));
    };

    public boolean havePriority(){
        return userPriority != null && userPriority.size() != 0;
    }

    public PriorityList<ObjectType> getUserPriority() {
        return userPriority;
    }

    public void addPriority(int priority, ObjectType type){
        userPriority.addWithPriority(priority, type);
    }


    public int getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        return String.format("ID - %d;\n", userID) + data.toString();
    }
}

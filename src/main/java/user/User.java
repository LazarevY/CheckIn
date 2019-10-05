package user;

import system.Connect;
import map.objects.ObjectType;
import util.PriorityList;

public class User {
    private UserData data;
    private Connect sourceConnect;
    private PriorityList<ObjectType> userPriority;
    private Long userID;

    public User(UserData data, Connect sourceConnect, long userID) {
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
        sourceConnect.addCheck(userID);
    }

    public boolean havePriority(){
        return userPriority != null && userPriority.size() != 0;
    }

    public PriorityList<ObjectType> getUserPriority() {
        return userPriority;
    }

    public void addPriority(int priority, ObjectType type){
        userPriority.addWithPriority(priority, type);
    }


    public Long getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        return String.format("ID - %d;\n", userID) + data.toString();
    }
}

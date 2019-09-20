package system;


import geometry.Point;
import map.*;
import user.User;
import user.UserData;
import util.PriorityList;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckInSystem {

    public static final int radiusError = 50;

    private HashMap<Long, User> usersBase;
    private HashMap<Long, GeoObj> relationUserObj;
    private HashMap<GeoObj, ArrayList<Long>> relationObjUsers;

    private Map map;

    private Connect connect;

    private static  PriorityList<ObjectType> systemPriority;

    public CheckInSystem() {
        usersBase = new HashMap<>();
        relationUserObj = new HashMap<>();
        relationObjUsers = new HashMap<>();
        systemPriority = new PriorityList<>();
        systemPriority.addLast(ObjectType.SC);
        systemPriority.addLast(ObjectType.MARKET);
        systemPriority.addLast(ObjectType.UNI);
        systemPriority.addLast(ObjectType.SCHOOL);
        systemPriority.addLast(ObjectType.PRIVATE);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setConnect(Connect connect) {
        this.connect = connect;
    }

    public static PriorityList<ObjectType> getSystemPriority() {
        return systemPriority;
    }

    private void appCycle(){
        while (true){
            for (long id :
                    connect.getCheckEvents()) {
                checkIn(id);
            }
        }
    }


    public void checkIn(long userID){
        Point loc = map.getUserLocation(userID);

        GeoObj old = relationUserObj.get(userID);
        if(old != null)
            if (old.contains(loc, radiusError)){
                System.out.printf("User %d in %s", userID, old.fullName());
                return;
            }

        GeoObj new_o = null;
        for (GeoObj o :
                map.getObjects()) {
            if(o.contains(loc, radiusError)){

                if (o instanceof CompositeObj) {
                    o = getChild((CompositeObj) o, loc, radiusError);
                }
                else if(o instanceof LineObj){
                    o = ((LineObj) o).intersectObj(loc, radiusError);

                }
                new_o = o;
                break;
            }
        }

        if(new_o != null)
            System.out.printf("User %d in %s", userID, new_o.fullName());

        relationUserObj.put(userID, new_o);

        ArrayList<Long> us = relationObjUsers.get(old);
        if(us != null)
            us.remove(userID);

        if (!relationObjUsers.containsKey(new_o)) {
            relationObjUsers.put(new_o, new ArrayList<>());
        }
        relationObjUsers.get(new_o).add(userID);
    }

    public long registerUser(UserData userData){
        long id = GeneratorID.geneateUserID();
        User nUs = new User(userData, connect, id);
        usersBase.put(id, nUs);
        return id;
    }

    public void unregisterUser(Long userID){
        GeoObj linked = relationUserObj.get(userID);
        if(linked != null){
            relationObjUsers.get(linked).remove(userID);
            relationUserObj.remove(userID);
        }
        usersBase.remove(userID);
    }

    public void setUserLocation(long userID, Point location){
        map.addUserLocation(userID, location);
    }

    public void addUserPriorityInOrder(long userID, ObjectType... priority){
        User u = usersBase.get(userID);
        for (ObjectType t :
                priority) {
            u.getUserPriority().addLast(t);
        }
    }

    private GeoObj getChild(CompositeObj parent, Point loc, int radius){
        for (GeoObj o :
                parent.getChilds()) {
            if(o.contains(loc, radius)){
                if(o instanceof LineObj){
                    return ((LineObj) o).intersectObj(loc, radius);
                }else if(o instanceof CompositeObj)
                    return getChild((CompositeObj)o, loc, radius);
                else return o;
            }
        }
        return parent;
    }

}

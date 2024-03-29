package system;

import geometry.geojson.Point;
import map.Map;
import map.objects.GeoObj;
import map.objects.ObjectType;
import system.events.ChangeLocationEvent;
import system.events.CheckInEvent;
import system.events.SystemEvent;
import user.User;
import user.UserData;
import util.PriorityList;

import java.util.*;

public class CheckInSystem {

    public static final int radiusError = 10;
    private HashMap<Integer, User> usersBase;
    private Map map;

    private volatile Connect connect;

    private final static PriorityList systemPriority = new PriorityList();

    private boolean launched = false;

    public CheckInSystem() {
        usersBase = new HashMap<>();
        systemPriority.addLast(ObjectType.SC);
        systemPriority.addLast(ObjectType.MARKET);
        systemPriority.addLast(ObjectType.CAFE);
        systemPriority.addLast(ObjectType.PARK);
        systemPriority.addLast(ObjectType.ALLEY);
        systemPriority.addLast(ObjectType.UNI);
        systemPriority.addLast(ObjectType.STADIUM);
        systemPriority.addLast(ObjectType.SCHOOL);
        systemPriority.addLast(ObjectType.PRIVATE);
        systemPriority.addLast(ObjectType.MONUMENT);
        systemPriority.addLast(ObjectType.ALLEY);
        connect = new Connect(this);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setConnect(Connect connect) {
        this.connect = connect;
    }

    public Connect getConnect() {
        return connect;
    }

    public static PriorityList getSystemPriority() {
        return systemPriority;
    }

    public GeoObj checkIn(int userID) {
        Point loc = map.getUserLocation(userID);
        User user = usersBase.get(userID);

        GeoObj concurrentObj = map.getNullObj();
        GeoObj concurrentParent = map.getNullObj();

        GeoObj old = map.getActualForUserGeoObj(userID);
        if (old != map.getNullObj() && old.getGeometry().intersects(loc, radiusError)) {
            concurrentObj = old;
            concurrentParent = old.getParent();
        }

        PriorityList priorityList = new PriorityList(user.getUserPriority());
        priorityList.tailMerdge(systemPriority);

        int concurrent_priority = priorityList.getPriority(concurrentObj);
        int parent_priority = priorityList.getPriority(concurrentParent);

        List<GeoObj> objs = map.getActualCheckInObjects(loc, radiusError);
        for (GeoObj current : objs) {
            int priority = priorityList.getPriority(current);
            if (current.checkParent(concurrentObj)) {
                parent_priority = concurrent_priority;
                concurrent_priority = priority;
                concurrentParent = concurrentObj;
                concurrentObj = current;
            } else if (current.checkParent(concurrentParent) && (priority < concurrent_priority
                    || priority == concurrent_priority && map.getCountUserInObj(current) > map.getCountUserInObj(concurrentObj))) {
                concurrent_priority = priority;
                concurrentObj = current;
            } else {
                int max_priority = Math.min(concurrent_priority, parent_priority);
                if (priority < max_priority || parent_priority == max_priority &&
                        map.getCountUserInObj(current) > map.getCountUserInObj(concurrentObj)) {
                    concurrent_priority = priority;
                    GeoObj newParent = current.getParent();
                    parent_priority = current.hasParent() ? priorityList.getPriority(newParent) : priority;
                    concurrentObj = current;
                    concurrentParent = newParent;
                }
            }
        }
        map.rewriteRelationUserAndObj(userID, concurrentObj);
        System.out.println("UserID - " + userID + ", place - " + (concurrentObj == Map.nullObj? "not founded" : concurrentObj.fullName()));
        return concurrentObj;

    }

    public int registerUser(UserData userData) {
        int id = GeneratorID.generateUserID();
        User nUs = new User(userData, connect, id);
        usersBase.put(id, nUs);
        map.addUserToMap(nUs);
        return id;
    }

    public int registerUser(User user){
        int id = GeneratorID.generateUserID();
        user.setUserID(id);
        user.setSourceConnect(connect);
        usersBase.put(id, user);
        map.addUserToMap(user);
        return id;
    }

    public User getUser(int id) {
        return usersBase.get(id);
    }

    public void unregisterUser(int userID) {
        usersBase.remove(userID);
    }

    public void setUserLocation(int userID, Point location) {
        map.addUserLocation(userID, location);
    }

    public void setUserLocation(User user, Point location) {
        map.addUserLocation(user.getUserID(), location);
    }


    public void processEvents(List<SystemEvent> events){
        for (SystemEvent event : events)
            event.process(this);
    }

    public void processEvent(SystemEvent event){
        event.process(this);
    }

    public void registerAll(List<GeoObj> objs) {
        for (GeoObj object : objs)
            registerGeoObj(object);
    }

    public void addUserPriorityInOrder(int userID, ObjectType... priority) {
        User u = usersBase.get(userID);
        for (ObjectType t :
                priority) {
            u.getUserPriority().addLast(t);
        }
    }

    public void registerGeoObj(GeoObj obj) {
        map.registerObj(obj);
    }
}
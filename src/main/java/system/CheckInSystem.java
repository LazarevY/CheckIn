package system;


import geometry.Point;
import map.Map;
import map.objects.CompositeObj;
import map.objects.GeoObj;
import map.objects.LineObj;
import map.objects.ObjectType;
import user.User;
import user.UserData;
import util.Pair;
import util.PriorityList;

import java.util.*;

public class CheckInSystem {

    public static final int radiusError = 10;

    private HashMap<Long, User> usersBase;
    private HashMap<Long, GeoObj> relationUserObj;
    private HashMap<GeoObj, ArrayList<Long>> relationObjUsers;

    private Map map;

    private Connect connect;

    private final static PriorityList<ObjectType> systemPriority = new PriorityList<>();

    public CheckInSystem() {
        usersBase = new HashMap<>();
        relationUserObj = new HashMap<>();
        relationObjUsers = new HashMap<>();
        systemPriority.addLast(ObjectType.SC);
        systemPriority.addLast(ObjectType.MARKET);
        systemPriority.addLast(ObjectType.CAFE);
        systemPriority.addLast(ObjectType.PARK);
        systemPriority.addLast(ObjectType.ALLEY);
        systemPriority.addLast(ObjectType.UNI);
        systemPriority.addLast(ObjectType.SCHOOL);
        systemPriority.addLast(ObjectType.PRIVATE);
        systemPriority.addLast(ObjectType.MONUMENT);
        systemPriority.addLast(ObjectType.ALLEY);
    }

    public void setMap(Map map) {
        this.map = map;
        addObjToBase(map.getNullObj());
    }

    public void setConnect(Connect connect) {
        this.connect = connect;
    }

    public static PriorityList<ObjectType> getSystemPriority() {
        return systemPriority;
    }

    private void appCycle() {
        while (true) {
            for (long id :
                    connect.getCheckEvents()) {
                checkIn(id);
            }
        }
    }

    public GeoObj checkIn(long userID) {
        Point loc = map.getUserLocation(userID);
        User user = usersBase.get(userID);

        GeoObj old = relationUserObj.get(userID);
        if (old != map.getNullObj() && old.contains(loc, radiusError))
            return old;

        PriorityList<ObjectType> priorityList = new PriorityList<>(user.getUserPriority());
        priorityList.tailMerdge(systemPriority);

        GeoObj concurrentObj = map.getNullObj();
        GeoObj concurrentParent = map.getNullObj();
        int concurrent_priority = priorityList.size();
        int parent_priority = concurrent_priority;

        ArrayList<GeoObj> objs = map.getActualCheckInObjects(loc, radiusError);
//        for (GeoObj current : objs) {
//            int priority = priorityList.indexOf(current.getObjectType());
//            if (current.checkParent(concurrentObj) && priority > concurrent_priority)
//                priority = concurrent_priority;
//            if(priority == -1 || priority > concurrent_priority)
//                continue;
//            if(priority < concurrent_priority || getCountUserInObj(concurrentObj) < getCountUserInObj(current)){
//                concurrentObj = current;
//                concurrent_priority = priority;
//            }
//        }

        for (GeoObj current : objs) {
            int priority = priorityList.indexOf(current.getObjectType());
            if (current.checkParent(concurrentObj)) {
                parent_priority = concurrent_priority;
                concurrent_priority = priority;
                concurrentParent = concurrentObj;
                concurrentObj = current;
            } else if (current.checkParent(concurrentParent) && (priority < concurrent_priority
            || priority == concurrent_priority && getCountUserInObj(current) > getCountUserInObj(concurrentObj))){
                concurrent_priority = priority;
                concurrentObj = current;
            }else{
                int max_priority = Math.min(concurrent_priority, parent_priority);
                if(priority < max_priority || parent_priority == max_priority &&
                        getCountUserInObj(current) > getCountUserInObj(concurrentObj)){
                    concurrent_priority = priority;
                    GeoObj newParent = current.getParent();
                    parent_priority = current.hasParent() ? priorityList.indexOf(newParent.getObjectType()) : priority;
                    concurrentObj = current;
                    concurrentParent = newParent;
                }
            }
        }
        rewriteRelationUserAndObj(userID, concurrentObj);
        return concurrentObj;
    }

    private void rewriteRelationUserAndObj(long userID, GeoObj newObj) {
        GeoObj old = relationUserObj.get(userID);
        relationObjUsers.get(old).remove(userID);
        relationUserObj.put(userID, newObj);
        relationObjUsers.get(newObj).add(userID);
    }

    private int getCountUserInObj(GeoObj obj) {
        ArrayList l = relationObjUsers.get(obj);
        return l == null ? 0 : l.size();
    }

    public long registerUser(UserData userData) {
        long id = GeneratorID.generateUserID();
        User nUs = new User(userData, connect, id);
        usersBase.put(id, nUs);
        relationUserObj.put(id, map.getNullObj());
        relationObjUsers.get(map.getNullObj()).add(id);
        return id;
    }

    public void unregisterUser(Long userID) {
        GeoObj linked = relationUserObj.get(userID);
        if (linked != null) {
            relationObjUsers.get(linked).remove(userID);
            relationUserObj.remove(userID);
        }
        usersBase.remove(userID);
    }

    public void setUserLocation(long userID, Point location) {
        map.addUserLocation(userID, location);
    }

    public void addUserPriorityInOrder(long userID, ObjectType... priority) {
        User u = usersBase.get(userID);
        for (ObjectType t :
                priority) {
            u.getUserPriority().addLast(t);
        }
    }

    public void registerGeoObj(GeoObj obj) {
        if (obj.unnamed())
            return;
        map.addObj(obj);
        addObjToBase(obj);
    }

    private void addObjToBase(GeoObj obj) {
        if (relationObjUsers.containsKey(obj))
            return;
        relationObjUsers.put(obj, new ArrayList<>());
        ArrayList<GeoObj> child = (ArrayList<GeoObj>) obj.getActualChildren();
        if (child != null)
            for (GeoObj ch : child)
                addObjToBase(ch);
    }

}
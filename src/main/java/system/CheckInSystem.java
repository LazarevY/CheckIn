package system;


import geometry.Point;
import map.*;
import map.Map;
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
        systemPriority.addLast(ObjectType.UNI);
        systemPriority.addLast(ObjectType.SCHOOL);
        systemPriority.addLast(ObjectType.PRIVATE);
        systemPriority.addLast(ObjectType.MONUMENT);
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

    private void appCycle() {
        while (true) {
            for (long id :
                    connect.getCheckEvents()) {
                checkIn(id);
            }
        }
    }

    public void checkIn(long userID) {
        Point loc = map.getUserLocation(userID);
        User user = usersBase.get(userID);

        GeoObj old = relationUserObj.get(userID);
        if (old != null)
            if (old.contains(loc, radiusError)) {
                System.out.printf("User %d in %s", userID, old.fullName());
                return;
            }

        PriorityList<ObjectType> priorityList = new PriorityList<>(user.getUserPriority());
        priorityList.tailMerdge(systemPriority);
        
        GeoObj concurrentObj = null;
        int c_pr = priorityList.size();

        ArrayDeque<Pair<GeoObj, Integer>> objs_pair = new ArrayDeque<>();
        for (GeoObj o : map.getObjects())
            objs_pair.addLast(new Pair<>(o, priorityList.indexOf(o.getObjectType())));
        while (!objs_pair.isEmpty()){
            Pair<GeoObj, Integer> pair = objs_pair.pop();
            GeoObj obj = pair.getFirst();
            int pr = pair.getSecond();
            if(pr == -1 || pr > c_pr || obj.unnamed() || !obj.contains(loc, radiusError)){

            }else if(pr < c_pr || getCountUserInObj(concurrentObj) <= getCountUserInObj(obj)){
                concurrentObj = obj;
                c_pr = pr;
            }
            if(obj instanceof CompositeObj)
                for (GeoObj d :
                        ((CompositeObj) obj).getChilds())
                    objs_pair.addLast(new Pair<>(d, Math.min(pr, priorityList.indexOf(d.getObjectType()))));
            else if(obj instanceof LineObj)
                for (GeoObj d :
                        ((LineObj) obj).getPoints())
                    objs_pair.addLast(new Pair<>(d, Math.min(pr, priorityList.indexOf(d.getObjectType()))));
        }

        if(concurrentObj == null)
            System.out.println("Object didn't founded");
        else
            System.out.printf("User %d in %s", userID, concurrentObj.fullName());
    }

    private void rewriteRelationUserAndObj(long userID, GeoObj newObj){
        GeoObj old = relationUserObj.get(userID);
        relationObjUsers.get(old).remove(userID);

    }

    private int getCountUserInObj(GeoObj obj){
        ArrayList l = relationObjUsers.get(obj);
        return l == null? 0 : l.size();
    }

    public long registerUser(UserData userData) {
        long id = GeneratorID.generateUserID();
        User nUs = new User(userData, connect, id);
        usersBase.put(id, nUs);
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
        relationObjUsers.put(obj, new ArrayList<>());
        if (obj instanceof CompositeObj) {
            for (GeoObj o :
                    ((CompositeObj) obj).getChilds()) {
                addObjToBase(o);
            }
        } else if (obj instanceof LineObj) {
            for (GeoObj o :
                    ((LineObj) obj).getPoints()) {
                addObjToBase(o);
            }
        }
    }


    private GeoObj getChild(CompositeObj parent, Point loc, int radius) {
        for (GeoObj o :
                parent.getChilds()) {
            if (o.contains(loc, radius)) {
                if (o instanceof LineObj) {
                    return ((LineObj) o).intersectObj(loc, radius);
                } else if (o instanceof CompositeObj)
                    return getChild((CompositeObj) o, loc, radius);
                else return o;
            }
        }
        return parent;
    }

    private ArrayList<GeoObj> getChildrenList(final CompositeObj parent, final Point loc, int radius) {
        ArrayDeque<GeoObj> queue = new ArrayDeque<>(parent.getChilds());
        ArrayList<GeoObj> result = new ArrayList<>();
        result.add(parent);
        //GeoObj m = parent;
        while (!queue.isEmpty()) {
            GeoObj o = queue.pop();
            if (o instanceof LineObj) {
                o = ((LineObj) o).intersectObj(loc, radius);
                if (o != null && !o.unnamed())
                    result.add(o);
            } else if (o instanceof CompositeObj && o.contains(loc, radius)) {
                queue.addAll(((CompositeObj) o).getChilds());
                result.add(o);
                //m = o;
            } else {
                if (!o.unnamed() && o.contains(loc, radius))
                    result.add(o);
            }
        }
//        if(result.isEmpty() && !m.unnamed())
//            result.add(m);
        return result;
    }
}
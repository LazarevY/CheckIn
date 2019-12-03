package util;

import map.objects.GeoObj;
import map.objects.ObjectType;

import java.util.ArrayList;
import java.util.List;


public class PriorityList {
    private List<ObjectType> priorityList;
    public PriorityList() {
        priorityList = new ArrayList<>();
    }

    public PriorityList(PriorityList other){
        priorityList = new ArrayList<>(other.priorityList);
    }

    public void addLast(ObjectType e){
       priorityList.add(e);
    }

    public int getPriority(GeoObj obj){
        return obj == null? priorityList.size() : getPriority(obj.getObjectType());
    }

    public int getPriority(ObjectType type){
        return type == ObjectType.NONE ? priorityList.size() : priorityList.indexOf(type);
    }

    public void addWithPriority(int priority, ObjectType e){
        if (priority < 0)
            return;

        if (priority >= priorityList.size())
            priorityList.add(e);
        else
            priorityList.add(priority, e);
    }

    public void tailMerdge (PriorityList other){
        for (ObjectType c :
                other.priorityList) {
            if (!priorityList.contains(c))
                addLast(c);
        }
    }

    public List<ObjectType> getPriorityList() {
        return priorityList;
    }
}

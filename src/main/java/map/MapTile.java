package map;

import geometry.Point;
import map.objects.GeoObj;

import java.util.ArrayDeque;
import java.util.ArrayList;

public  class MapTile {
    private ArrayList<GeoObj> geoObjArrayList;

    {
        geoObjArrayList = new ArrayList<>();
    }
    public MapTile() {
    }

    public void addGeoObj(GeoObj geoObj){
        geoObjArrayList.add(geoObj);
    }

    public ArrayList<GeoObj> getActualGeoObjects(Point location, int radius){
        ArrayDeque<GeoObj> objQueue = new ArrayDeque<>(geoObjArrayList);

        ArrayList<GeoObj> actualObjects = new ArrayList<>();
        while (!objQueue.isEmpty()) {
            GeoObj current = objQueue.pop();
            if (!current.unnamed() && current.contains(location, radius)) {
                actualObjects.add(current);
                objQueue.addAll(current.getActualChildren());
            }
        }
        return actualObjects;
    }

}

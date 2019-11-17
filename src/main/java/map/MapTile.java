package map;

import geometry.geojson.Point;
import map.objects.GeoObj;

import java.util.ArrayDeque;
import java.util.ArrayList;

public  class MapTile {
    private ArrayList<GeoObj> geoObjArrayList;

    public MapTile() {
        geoObjArrayList = new ArrayList<>();
    }

    public void addGeoObj(GeoObj geoObj){
        geoObjArrayList.add(geoObj);
    }

    public ArrayList<GeoObj> getActualGeoObjects(Point location, int radius){
        ArrayDeque<GeoObj> objQueue = new ArrayDeque<>(geoObjArrayList);

        ArrayList<GeoObj> actualObjects = new ArrayList<>();
        while (!objQueue.isEmpty()) {
            GeoObj current = objQueue.pop();
            if (!current.unnamed() && current.getGeometry().intersects(location, radius)) {
                actualObjects.add(current);
                objQueue.addAll(current.getActualChildren());
            }
        }
        return actualObjects;
    }

}

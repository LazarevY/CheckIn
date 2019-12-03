package map;

import geometry.geojson.Point;
import map.objects.GeoObj;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public  class MapTile {
    private ArrayList<GeoObj> geoObjArrayList;

    public MapTile() {
        geoObjArrayList = new ArrayList<>();
    }

    public void addGeoObj(GeoObj geoObj){
        geoObjArrayList.add(geoObj);
    }

    public List<GeoObj> getActualGeoObjects(Point location, int radius){
//        ArrayDeque<GeoObj> objQueue = new ArrayDeque<>(geoObjArrayList);
////
////        ArrayList<GeoObj> actualObjects = new ArrayList<>();
////        while (!objQueue.isEmpty()) {
////            GeoObj current = objQueue.pop();
////            if (!current.unnamed() && current.getGeometry().intersects(location, radius)) {
////                actualObjects.add(current);
////                objQueue.addAll(current.getActualChildren());
////            }
////        }
////        return actualObjects;
        return geoObjArrayList.stream()
                .filter(e -> (!e.unnamed() && e.getGeometry().intersects(location, radius)))
                .collect(Collectors.toList());
    }

}

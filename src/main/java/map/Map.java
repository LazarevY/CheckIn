package map;

import geometry.geojson.Circle;
import geometry.geojson.Geometry;
import geometry.geojson.Point;
import map.objects.GeoObj;
import map.objects.ObjectType;
import map.objects.PointObj;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {
    private double width;
    private double height;
    private MapTile[][] tiles;
    private java.util.Map<Integer, GeoObj> relationUserObj;
    private java.util.Map<Integer, List<Integer>> relationObjUsers;

    public java.util.Map<Integer, GeoObj> getObjectBase() {
        return objectBase;
    }

    private java.util.Map<Integer, GeoObj> objectBase;

    private java.util.Map<Integer, Point> usersLocation;

    public static final GeoObj nullObj = new PointObj("__NULL__", null, null, ObjectType.NONE);
    public static final ArrayList<GeoObj> emptyList = new ArrayList<>(0);
    public static final int nullObjId = -1;

    public Map(int width, int height, int countXTiles, int countYTiles, java.util.Map<Integer, GeoObj> objMap) {
        this.width = width;
        this.height = height;
        usersLocation = new HashMap<>();

        relationObjUsers = new HashMap<>();
        relationObjUsers.put(-1, new ArrayList<>());

        relationUserObj = new HashMap<>();
        objectBase = new HashMap<>();
        objectBase.put(-1, nullObj);

        tiles = new MapTile[countXTiles][countYTiles];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new MapTile();
            }
        }

        if (objMap != null)
            readObjects(objMap);
    }

    private void readObjects(java.util.Map<Integer, GeoObj> objMap){
        for (GeoObj obj : objMap.values())
            registerObj(obj);

    }


    private void addObj(GeoObj obj) {
        for (MapTile t : getActualMapTiles(obj.getGeometry().getBoundPoints()))
            t.addGeoObj(obj);
    }

    public void addUserToMap(User user) {
        relationUserObj.put(user.getUserID(), nullObj);
        relationObjUsers.get(nullObjId).add(user.getUserID());
    }

    public void deleteUser(int userId) {
        GeoObj linked = relationUserObj.get(userId);
        relationObjUsers.get(linked.getId()).remove(userId);
        relationUserObj.remove(userId);
    }

    public void registerObj(GeoObj obj){
        if (objectBase.containsKey(obj.getId()))
            return;
        objectBase.put(obj.getId(), obj);
        relationObjUsers.put(obj.getId(), new ArrayList<>());
        addObj(obj);
    }

    public void rewriteRelationUserAndObj(int userID, GeoObj newObj) {
        int oldId = relationUserObj.get(userID).getId();
        relationObjUsers.get(oldId).remove(((Object) userID));
        relationUserObj.put(userID, newObj);
        relationObjUsers.get(newObj.getId()).add(userID);
    }

    public int getCountUserInObj(GeoObj obj) {
        List l = relationObjUsers.get(obj.getId());
        return l == null ? 0 : l.size();
    }

    public GeoObj getActualForUserGeoObj(int userId){
        return relationUserObj.get(userId);
    }

    public GeoObj getNullObj() {
        return nullObj;
    }

    public void addUserLocation(int userID, Point location) {
        usersLocation.put(userID, location);
    }

    public Point getUserLocation(int userID) {
        return usersLocation.get(userID);
    }

    public List<GeoObj> getActualCheckInObjects(Point loc, int radius) {
        ArrayList<GeoObj> objList = new ArrayList<>();
        for (MapTile tile : getActualMapTiles(new Circle(loc, radius).getBoundPoints())) {
            objList.addAll(tile.getActualGeoObjects(loc, radius));
        }
        return objList;
    }

    private ArrayList<MapTile> getActualMapTiles(List<Point> points) {
        ArrayList<MapTile> mapTiles = new ArrayList<>();
        double tileWidth = width / tiles.length;
        double tileHeight = height / tiles[0].length;
        for (Point p : points) {
            MapTile tile = tiles[(int) (p.getY() / tileHeight)][(int) (p.getX() / tileWidth)];
            if (!mapTiles.contains(tile))
                mapTiles.add(tile);
        }
        return mapTiles;
    }

}

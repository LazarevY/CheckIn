package map;

import geometry.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Map {
    private int width, height;
    private ArrayList<GeoObj> objMap;
    private HashMap<Long, Point> usersLocation;

    public Map(int width, int height, ArrayList<GeoObj> objMap) {
        this.width = width;
        this.height = height;
        this.objMap = objMap;
        usersLocation = new HashMap<>();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ArrayList<GeoObj> getObjects(){
        return objMap;
    }

    public void addUserLocation(Long userID, Point location){
        usersLocation.put(userID, location);
    }

    public Point getUserLocation(Long userID){
        return usersLocation.get(userID);
    }
}

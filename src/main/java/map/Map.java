package map;

import geometry.Circle;
import geometry.Point;
import map.objects.GeoObj;
import map.objects.PointObj;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {
    private int widthTail;
    private int heightTail;
    private int countXTiles;
    private int countYTiles;

    private MapTile[][] tiles;

    private HashMap<Long, Point> usersLocation;

    private static final GeoObj nullObj = new PointObj("Not founded object",null, null);
    public static final ArrayList<GeoObj> emptyList = new ArrayList<>(0);

    public Map(int widthTail, int heightTail, int countXTiles, int countYTiles) {
        this.widthTail = widthTail;
        this.heightTail = heightTail;
        this.countXTiles = countXTiles;
        this.countYTiles = countYTiles;
        usersLocation = new HashMap<>();
        tiles = new MapTile[countXTiles][countYTiles];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new MapTile();
            }
        }
    }


    public void addObj(GeoObj obj) {
        for (MapTile t : getActualMapTiles(obj.getBoundsPoints()))
                t.addGeoObj(obj);
    }

    public GeoObj getNullObj() {
        return nullObj;
    }

    public void addUserLocation(Long userID, Point location) {
        usersLocation.put(userID, location);
    }

    public Point getUserLocation(Long userID) {
        return usersLocation.get(userID);
    }

    public ArrayList<GeoObj> getActualCheckInObjects(Point loc, int radius) {
        ArrayList<GeoObj> objList = new ArrayList<>();
        for(MapTile tile : getActualMapTiles(new Circle(loc, radius).getBoundsPoints())){
            objList.addAll(tile.getActualGeoObjects(loc, radius));
        }
        return objList;
    }

    private ArrayList<MapTile> getActualMapTiles(ArrayList<Point> points){
        ArrayList<MapTile> mapTiles = new ArrayList<>();
        for (Point p : points) {
            MapTile tile = tiles[p.getX() / widthTail][p.getY() / heightTail];
            if (!mapTiles.contains(tile))
                mapTiles.add(tile);
        }
        return mapTiles;
    }

}

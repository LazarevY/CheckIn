package map.objects;

import geometry.Point;
import geometry.Shape;

import java.util.ArrayList;

import static map.Map.emptyList;

public class UsualObj extends GeoObj{
    private Shape bounds;

    public UsualObj(Shape bounds, ObjectType type) {
        this(null, bounds, null, type);
    }

    public UsualObj(String name, Shape bounds, GeoObj parent, ObjectType type) {
        super(name, parent, type);
        this.bounds = bounds;
    }

    public Shape getBounds() {
        return bounds;
    }


    @Override
    public boolean contains(Point location, int radius) {
        return bounds.contains(location, radius);
    }

    @Override
    public ArrayList<Point> getBoundsPoints() {
        return bounds.getBoundsPoints();
    }

    @Override
    public ArrayList<GeoObj> getActualChildren() {
        return emptyList;
    }
}

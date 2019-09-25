package map;

import geometry.Point;
import geometry.Shape;

import java.util.ArrayList;

public class UsualObj extends GeoObj{
    private Shape bounds;

    public UsualObj(Shape bounds, GeoObj parent, ObjectType type) {
        super(null, parent, type);
        this.bounds = bounds;
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
}

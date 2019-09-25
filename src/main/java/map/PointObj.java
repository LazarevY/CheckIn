package map;

import geometry.Point;

public class PointObj extends GeoObj {
    private Point location;

    public PointObj(Point location, GeoObj parent, ObjectType type) {
        super(null, parent, type);
        this.location = location;
    }

    public PointObj(String name, Point location, GeoObj parent, ObjectType type) {
        super(name, parent, type);
        this.location = location;
    }

    public Point getLocation() {
        return location;
    }

    @Override
    public boolean contains(Point location, int radius) {
        return Point.distanceSqr(location, this.location) < radius * radius;
    }

}

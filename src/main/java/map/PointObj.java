package map;

import geometry.Point;

public class PointObj extends GeoObj {
    private Point location;

    public PointObj(Point location, GeoObj parent) {
        this.location = location;
        this.parent = parent;
    }

    public PointObj(String name, Point location, GeoObj parent) {
        super(name);
        this.location = location;
        this.parent = parent;
    }

    public Point getLocation() {
        return location;
    }

    @Override
    public boolean contains(Point location, int radius) {
        return Point.distanceSqr(location, this.location) < radius * radius;
    }

    @Override
    public String fullName() {
        return parent != null?  parent.fullName()+", " + name : name;
    }
}

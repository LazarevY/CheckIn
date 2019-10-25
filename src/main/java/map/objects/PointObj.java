package map.objects;

import geometry.Point;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import static map.Map.emptyList;

import java.util.ArrayList;
import java.util.Collections;

public class PointObj extends GeoObj {
    private Point location;

    public PointObj(Point location, ObjectType type) {
        super(null, null, type);
        this.location = location;
    }

    public PointObj(String name, Point location, GeoObj parent , ObjectType type) {
        super(name, parent, type);
        this.location = location;
    }
    @JsonCreator
    public PointObj(@JsonProperty("name") String name, @JsonProperty("location") Point location ,
                    @JsonProperty("type")ObjectType type) {
        super(name, null, type);
        this.location = location;
    }

    public Point getLocation() {
        return location;
    }

    @Override
    public boolean contains(Point location, int radius) {
        return Point.distanceSqr(location, this.location) < radius * radius;
    }

    @Override
    public ArrayList<Point> getBoundsPoints() {
        ArrayList<Point> res = new ArrayList<>();
        res.add(new Point(location));
        return res;
    }

    @Override
    public ArrayList<GeoObj> getActualChildren() {
        return emptyList;
    }
}

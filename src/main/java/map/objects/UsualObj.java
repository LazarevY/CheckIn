package map.objects;

import geometry.Circle;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSubTypes;

import java.util.ArrayList;

import static map.Map.emptyList;

public class UsualObj extends GeoObj{

    private Shape bounds;

    @JsonCreator
    public UsualObj (@JsonProperty("name") String name, @JsonProperty("bounds")Shape bounds,
                     @JsonProperty("objectType")ObjectType type) {
        this(name, bounds, null, type);
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

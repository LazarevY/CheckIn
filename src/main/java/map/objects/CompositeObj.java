package map.objects;

import geometry.Point;
import geometry.Shape;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Класс компизитного объекта. Имеет дочерние объекты и произовильные границы<b></b>
 * описываемые классом <code>geometry.Shape</code>
 */
public class CompositeObj extends GeoObj implements IHaveChildren {
    private ArrayList<GeoObj> children;
    private Shape bounds;

    public CompositeObj(Shape bounds, ObjectType type){
        this(null, null, bounds, null, type);
    }
    public CompositeObj(String name, Shape bounds, GeoObj parent, ObjectType type) {
        this(name, null, bounds, parent, type);
    }

    @JsonCreator
    public CompositeObj(@JsonProperty("name")String name, @JsonProperty("bounds")Shape bounds,
                        @JsonProperty("objectType")ObjectType type) {
        this(name, null, bounds, null, type);
    }

    public CompositeObj(String name, ArrayList<GeoObj> children, Shape bounds, GeoObj parent, ObjectType type) {
        super(name, parent, type);
        this.bounds = bounds;
        this.children = children;
    }

    public ArrayList<GeoObj> getChildren() {
        return children;
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
        ArrayList<GeoObj> result = new ArrayList<>();
        for (GeoObj o :
                children) {
            if (!o.unnamed())
                result.add(o);
            result.addAll(o.getActualChildren());
        }
        return result;
    }

    @Override
    public void addChild(GeoObj child) {
        if (children == null)
            children = new ArrayList<>();
        children.add(child);
        child.setParent(this);
    }
}

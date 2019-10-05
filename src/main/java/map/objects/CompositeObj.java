package map.objects;

import geometry.Point;
import geometry.Shape;

import java.util.ArrayList;

/**
 * Класс компизитного объекта. Имеет дочерние объекты и произовильные границы<b></b>
 * описываемые классом <code>geometry.Shape</code>
 */
public class CompositeObj extends GeoObj {
    private ArrayList<GeoObj> childs;
    private Shape bounds;

    public CompositeObj(Shape bounds, ObjectType type){
        this(null, null, bounds, null, type);
    }
    public CompositeObj(String name, Shape bounds, GeoObj parent, ObjectType type) {
        this(name, null, bounds, parent, type);
    }

    public CompositeObj(String name, Shape bounds, ObjectType type) {
        this(name, null, bounds, null, type);
    }

    public CompositeObj(String name, ArrayList<GeoObj> childs, Shape bounds, GeoObj parent, ObjectType type) {
        super(name, parent, type);
        this.bounds = bounds;
        this.childs = childs;
    }

    public void addDaughterObj(GeoObj o){
        if (childs == null)
            childs = new ArrayList<>();
        childs.add(o);
        o.setParent(this);
    }

    public ArrayList<GeoObj> getChilds() {
        return childs;
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
                childs) {
            if (!o.unnamed())
                result.add(o);
            result.addAll(o.getActualChildren());
        }
        return result;
    }
}

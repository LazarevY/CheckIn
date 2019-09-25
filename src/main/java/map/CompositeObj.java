package map;

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

    public CompositeObj(Shape bounds, GeoObj parent, ObjectType type){
        super(null, parent, type);
        this.bounds = bounds;
        childs = null;
    }
    public CompositeObj(ArrayList<GeoObj> childs, Shape bounds, GeoObj parent, ObjectType type) {
        super(null, parent, type);
        this.bounds = bounds;
        this.childs = childs;
    }

    public CompositeObj(String name, ArrayList<GeoObj> childs, Shape bounds, GeoObj parent, ObjectType type) {
        super(name, parent, type);
        this.bounds = bounds;
        this.childs = childs;
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
}

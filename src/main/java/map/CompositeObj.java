package map;

import geometry.Point;
import geometry.Shape;

import java.util.ArrayList;

public class CompositeObj extends GeoObj {
    private ArrayList<GeoObj> childs;
    private Shape bounds;

    public CompositeObj(Shape bounds){
        this.bounds = bounds;
        childs = null;
    }
    public CompositeObj(ArrayList<GeoObj> childs, Shape bounds) {
        this.bounds = bounds;
        this.childs = childs;
    }

    public CompositeObj(String name, ArrayList<GeoObj> childs, Shape bounds) {
        super(name);
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
    public String fullName() {
        return name;
    }

    @Override
    public boolean contains(Point location, int radius) {
        return bounds.contains(location, radius);
    }
}

package map;


import geometry.Point;

public abstract class GeoObj{
    protected String name;
    protected GeoObj parent;
    public boolean unnamed(){
        return name == null;
    }

    public GeoObj(){
        name = null;
    }
    public GeoObj(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String fullName(){
        return parent == null || parent.name == null ? name : parent.name + ", " + name;
    }

    public abstract boolean contains(Point location, int radius);
}

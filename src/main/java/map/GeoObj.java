package map;


import geometry.Point;

public abstract class GeoObj{
    protected String name;
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
    public abstract String fullName();

    public abstract boolean contains(Point location, int radius);
}

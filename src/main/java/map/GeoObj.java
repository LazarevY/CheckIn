package map;


import geometry.Point;

/**
 * Класс, описывающий основные свойства геообъекта.
 */
public abstract class GeoObj{
    protected String name;
    private GeoObj parent;
    private ObjectType objectType;

    public boolean unnamed(){
        return name == null;
    }

    public GeoObj(GeoObj parent, ObjectType type, long objID){
        name = null;
        this.objectType = type;
        this.parent = parent;

    }
    public GeoObj(String name, GeoObj parent , ObjectType type){
        this.name = name;
        this.parent = parent;
        this.objectType = type;
    }

    public void setParent(GeoObj parent) {
        this.parent = parent;
    }

    public String getName() {
        return name == null? "" : name;
    }
    public String fullName(){
        return parent == null? getName() : parent.fullName() + ", " + getName();
    }

    public ObjectType getObjectType() {
        return objectType;
    }


    public abstract boolean contains(Point location, int radius);
}


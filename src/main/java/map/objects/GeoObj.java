package map.objects;

import geometry.Point;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.util.ArrayList;

/**
 * Класс, описывающий основные свойства геообъекта.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PointObj.class, name = "point"),
        @JsonSubTypes.Type(value = CompositeObj.class, name = "composite"),
        @JsonSubTypes.Type(value = UsualObj.class, name = "usual"),
        @JsonSubTypes.Type(value = LineObj.class, name = "line"),
})
public abstract class GeoObj {
    protected String name;
    private GeoObj parent;
    private ObjectType objectType;

    public boolean unnamed() {
        return name == null;
    }

    public GeoObj(GeoObj parent, ObjectType type, long objID) {
        name = null;
        this.objectType = type;
        this.parent = parent;

    }

    public GeoObj(String name, GeoObj parent, ObjectType type) {
        this.name = name;
        this.parent = parent;
        this.objectType = type;
    }

    public void setParent(GeoObj parent) {
        this.parent = parent;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public String fullName() {
        return parent == null ? getName() : parent.fullName() + ", " + getName();
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public boolean checkParent(GeoObj other) {
        return parent != null && parent == other;
    }

    public boolean hasParent(){
        return parent != null;
    }

    public GeoObj getParent(){
        return parent;
    }

    public abstract boolean contains(Point location, int radius);

    public abstract ArrayList<Point> getBoundsPoints();

    /**
     * @return список потомков, которые имеют имя. Если объект не имеет потомков, то вернется ссылка на пустой лист
     */
    public abstract ArrayList<? extends GeoObj> getActualChildren();
}


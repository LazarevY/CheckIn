package map.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import geometry.geojson.Geometry;
import map.Map;

import java.util.List;

/**
 * Класс, описывающий основные свойства геообъекта.
 */
@JsonTypeInfo(property = "type", include = JsonTypeInfo.As.PROPERTY,  use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({@Type(LineObj.class), @Type(PointObj.class), @Type(CompositeObj.class), @Type(UsualObj.class)})
public abstract class GeoObj {
    private int id = Map.nullObjId;
    protected String name;
    private GeoObj parent;
    private ObjectType objectType;
    private Geometry geometry;

    public boolean unnamed() {
        return name == null;
    }

    public GeoObj(){

    }

    public GeoObj(String name, GeoObj parent, Geometry geometry,  ObjectType type) {
        this.name = name;
        this.parent = parent;
        this.geometry = geometry;
        this.objectType = type;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
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

        /**
     * @return список потомков, которые имеют имя. Если объект не имеет потомков, то вернется ссылка на пустой лист
     */
    @JsonIgnore
    public abstract List<? extends GeoObj> getActualChildren();

    public boolean hasChildren(){
        return this instanceof IHaveChildren;
    }

    public int getId() {
        return id;
    }
}


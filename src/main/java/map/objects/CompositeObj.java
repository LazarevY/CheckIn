package map.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import geometry.geojson.Geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс компизитного объекта. Имеет дочерние объекты и произовильные границы<b></b>
 * описываемые классом <code>geometry.Shape</code>
 */
public class CompositeObj extends GeoObj implements IHaveChildren {
    private ArrayList<GeoObj> childrenObjects;

    public CompositeObj(){
        super();
    }

    public CompositeObj(String name, GeoObj parent, Geometry geometry, ObjectType type) {
        super(name, parent, geometry, type);
    }

    public ArrayList<GeoObj> getChildrenObjects() {
        return childrenObjects;
    }


    @JsonIgnore
    @Override
    public List<? extends GeoObj> getActualChildren() {
        ArrayList<GeoObj> result = new ArrayList<>();
        for (GeoObj o :
                childrenObjects) {
            if (!o.unnamed())
                result.add(o);
            result.addAll(o.getActualChildren());
        }
        return result;
    }

    @Override
    public void addChild(GeoObj child) {
        if (childrenObjects == null)
            childrenObjects = new ArrayList<>();
        childrenObjects.add(child);
        child.setParent(this);
    }
}

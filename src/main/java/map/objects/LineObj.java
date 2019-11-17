package map.objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import geometry.geojson.Geometry;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LineObj extends GeoObj implements IHaveChildren{
    @JsonInclude
    private List<PointObj> childrenObjects;

    public LineObj(){
        super();
    }

    public LineObj(String name, GeoObj parent, Geometry geometry, ObjectType type) {
        super(name,parent, geometry, type);
        childrenObjects = new ArrayList<>();
    }

    @Override
    public List<? extends GeoObj> getActualChildren() {
        ArrayList<PointObj> result = new ArrayList<>();
        for (PointObj o : childrenObjects)
            if (!o.unnamed())
                result.add(o);

        return result;
    }

    @Override
    public void addChild(GeoObj child) {
        if(!(child instanceof PointObj))
            return;
        if (childrenObjects == null) {
            childrenObjects = new ArrayList<>();
        }
        childrenObjects.add(((PointObj) child));
        child.setParent(this);
    }
}

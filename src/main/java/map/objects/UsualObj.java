package map.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import geometry.geojson.Geometry;

import java.util.List;

import static map.Map.emptyList;

public class UsualObj extends GeoObj{

    public UsualObj(){
        super();
    }

    public UsualObj(String name, GeoObj parent, Geometry geometry, ObjectType type) {
        super(name, parent, geometry, type);
    }

    @JsonIgnore
    @Override
    public List<? extends GeoObj> getActualChildren() {
        return emptyList;
    }
}

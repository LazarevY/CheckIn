package map.objects;

import geometry.geojson.Geometry;

import static map.Map.emptyList;

import java.util.List;

public class PointObj extends GeoObj {

    public PointObj(){
        super();
    }
    public PointObj(String name, GeoObj parent, Geometry geometry,  ObjectType type) {
        super(name, parent, geometry,  type);
    }

    @Override
    public List<? extends GeoObj> getActualChildren() {
        return emptyList;
    }
}

package fileinteracting.reading;

import com.fasterxml.jackson.databind.ObjectMapper;
import geometry.geojson.Geometry;
import geometry.geojson.GeometryConverter;
import org.geojson.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GeometryLoader {
    private static ObjectMapper mapper = new ObjectMapper();

    public static Map<Integer, Geometry> loadGeometryFromFeatureCollection(String path) throws IOException {

        FeatureCollection aCollect = mapper.readValue(new File(path), FeatureCollection.class);

        Map<Integer, Geometry> geometryMap = new HashMap<>();

        for (Feature feature : aCollect.getFeatures()){
            GeoJsonObject geom = feature.getGeometry();
            Object t = (feature.getProperties().get("id"));
            if (t == null)
                return new HashMap<>();
            int id = (int)t;

            Geometry g = null;
            if (geom instanceof Polygon)
                g = GeometryConverter.convert(((Polygon) geom));
            else if (geom instanceof LineString)
                g = GeometryConverter.convert(((LineString) geom));
            else if (geom instanceof Point)
                g = GeometryConverter.convert(((Point)geom));

            if (g != null)
                geometryMap.put(id, g);

        }
        return geometryMap;
    }

}

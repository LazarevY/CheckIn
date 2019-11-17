package system;

import com.fasterxml.jackson.databind.ObjectMapper;
import fileinteracting.reading.GeoObjectsLoader;
import fileinteracting.reading.GeometryLoader;
import fileinteracting.topology.GeoObjectsTopology;
import geometry.geojson.Geometry;
import gui.main.MainFrame;
import map.objects.GeoObj;
import map.objects.LineObj;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
//        System.out.println(UsualObj.class);
//        CHSysRun run = new CHSysRun();
//        run.checkInStart();
       // MainFrame.appStart(args);

       // List<Geometry> list = GeometryLoader.loadGeometryFromFeatureCollection("src/main/resources/objects/objs.geojson");
       // System.out.println();

        Map<Integer, Geometry> geometryMap = GeometryLoader.loadGeometryFromFeatureCollection("src/main/resources/objects/geom.geojson");
        List<GeoObjectsTopology> topologies = GeoObjectsLoader.loadTopology("src/main/resources/objectsTopology.tpg");
        Map<Integer, GeoObj> objMap = GeoObjectsLoader.loadGeoObjectsInMap("src/main/resources/objects/objectsList.json");

        GeoObjectsLoader.applyGeometryByGeoObjects(objMap, geometryMap);
        GeoObjectsLoader.applyTopology(objMap, topologies);

        map.Map map = new map.Map(200,200, 20,20, objMap);
        System.out.println();
    }
}

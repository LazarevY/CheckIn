package fileinteracting.reading;

import com.fasterxml.jackson.databind.ObjectMapper;
import fileinteracting.topology.GeoObjectsTopology;
import fileinteracting.topology.TopologyType;
import geometry.geojson.Geometry;
import map.objects.GeoObj;
import map.objects.IHaveChildren;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeoObjectsLoader {
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static ArrayList<GeoObj> loadGeoObjects(String sourceObjectsFileName){
        GeoObj[] scalar;
        try {
            scalar =  objectMapper.readValue(new File(sourceObjectsFileName), GeoObj[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new ArrayList<>(Arrays.asList(scalar));
    }

    public static Map<Integer, GeoObj> loadGeoObjectsInMap(String sourceObjectsFileName){
        GeoObj[] scalar;
        try {
            scalar =  objectMapper.readValue(new File(sourceObjectsFileName), GeoObj[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Map<Integer, GeoObj> objMap = new HashMap<>();

        for (GeoObj obj : scalar)
            objMap.put(obj.getId(), obj);

        return objMap;
    }

    public static Map<Integer, GeoObj> applyTopology(Map<Integer, GeoObj> objects, List<GeoObjectsTopology> topologyList) {

        for (GeoObjectsTopology topology : topologyList){
            GeoObj parent = objects.get(topology.getParentId());
            if (parent == null)
                break;
            IHaveChildren parentHasChildren = ((IHaveChildren) parent);

            for (int childId : topology.getDaughterIds()){
                GeoObj child = objects.get(childId);
                if (child == null)
                    break;
                parentHasChildren.addChild(child);
            }
        }
        return objects;
    }

    public static Map<Integer, GeoObj> applyGeometryByGeoObjects(Map<Integer, GeoObj> objMap, Map<Integer, Geometry> geometryMap){
        for (Map.Entry<Integer, GeoObj> tuple : objMap.entrySet()){
            Geometry geometry = geometryMap.get(tuple.getKey());
            if (geometry == null)
                break;
            tuple.getValue().setGeometry(geometry);
        }
        return objMap;
    }

    public static List<GeoObjectsTopology> loadTopology(String path) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(path));
        List<GeoObjectsTopology> topologies = new ArrayList<>();
        for (String line : strings){
            GeoObjectsTopology topology = parseTopology(line);
            if (topology == null)
                return null;
            topologies.add(topology);
        }
        return topologies;
    }


    public static GeoObjectsTopology parseTopology(String source){
        try {
            Pattern pattern = Pattern.compile("(\\d)+:\\s+((\\d+,\\s?)*\\d+)");
            Matcher matcher = pattern.matcher(source);
            if (!matcher.matches())
                return null;
            MatchResult result = matcher.toMatchResult();
            if (result.groupCount() != 3)
                return null;
            int parentIndex = Integer.parseInt(result.group(1));
            List<Integer> dIndices = new ArrayList<>();
            Scanner sc = new Scanner(result.group(2));
            sc.useDelimiter("(\\s|[,])+");
            while (sc.hasNext())
                dIndices.add(sc.nextInt());
            if (dIndices.contains(parentIndex))
                return null;
            return new GeoObjectsTopology(parentIndex, dIndices);
        }
        catch (Exception ignored){
            return null;
        }
    }

}

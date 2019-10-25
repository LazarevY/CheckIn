package fileinteracting.reading;

import fileinteracting.topology.GeoObjectsTopology;
import fileinteracting.topology.TopologyType;
import map.objects.CompositeObj;
import map.objects.GeoObj;
import map.objects.IHaveChildren;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeoObjectsLoader {
    private ObjectMapper objectMapper;
    public GeoObjectsLoader() {
        objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping();
    }

    public ArrayList<GeoObj> loadGeoObjects(String sourceObjectsFileName){
        GeoObj[] scalar;
        try {
            scalar =  objectMapper.readValue(new File(sourceObjectsFileName), GeoObj[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new ArrayList<>(Arrays.asList(scalar));
    }

    public  void setTopology(List<GeoObj> scalarList, String topologyFilePath){
        List<String> topologyLines;
        try {
            topologyLines = Files.readAllLines(Paths.get(topologyFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (String line : topologyLines){
            GeoObjectsTopology topology = parseTopology(line);
            if (topology == null)
                return;
            IHaveChildren parent = scalarList.get(topology.getParentIndex()) instanceof  IHaveChildren ?
                    ((IHaveChildren) scalarList.get(topology.getParentIndex())) : null;
            if (parent == null)
                return;

            for (int childIndex : topology.getDaughterIndices())
                parent.addChild(scalarList.get(childIndex));
        }
    }

    public GeoObjectsTopology parseTopology(String source){
        try {
            Pattern pattern = Pattern.compile("(\\w)\\s+(\\d)\\s*:\\s*([0-9/]*)");
            Matcher matcher = pattern.matcher(source);
            if (!matcher.matches())
                return null;
            MatchResult result = matcher.toMatchResult();
            if (result.groupCount() != 3)
                return null;
            String type = result.group(1);
            TopologyType topologyType;
            if ("h".equals(type))
                topologyType = TopologyType.HIGHEST_TYPE;
            else if ("l".equals(type))
                topologyType = TopologyType.LOWER_TYPE;
            else return null;

            int parentIndex = Integer.parseInt(result.group(2));

            List<Integer> dIndices = new ArrayList<>();

            Scanner sc = new Scanner(result.group(3));
            sc.useDelimiter("/");
            while (sc.hasNext())
                dIndices.add(sc.nextInt());
            if (dIndices.contains(parentIndex))
                return null;
            return new GeoObjectsTopology(parentIndex, dIndices, topologyType);
        }
        catch (Exception ignored){
            return null;
        }
    }

}

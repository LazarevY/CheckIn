package fileinteracting.topology;

import java.util.List;

public class GeoObjectsTopology {
    private int parentIndex;
    private List<Integer> daughterIndices;
    private TopologyType topologyType;

    public GeoObjectsTopology(int parentIndex, List<Integer> daughterIndices, TopologyType topologyType) {
        this.parentIndex = parentIndex;
        this.daughterIndices = daughterIndices;
        this.topologyType = topologyType;
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public List<Integer> getDaughterIndices() {
        return daughterIndices;
    }

    public TopologyType getTopologyType() {
        return topologyType;
    }
}

package fileinteracting.topology;

import java.util.List;

public class GeoObjectsTopology {
    private int parentId;
    private List<Integer> daughterIds;

    public GeoObjectsTopology(int parentId, List<Integer> daughterIds) {
        this.parentId = parentId;
        this.daughterIds = daughterIds;
    }

    public int getParentId() {
        return parentId;
    }

    public List<Integer> getDaughterIds() {
        return daughterIds;
    }
}

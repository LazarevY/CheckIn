package system;

import java.util.ArrayList;
import java.util.List;

public class ConnectionManager {
    private List<Connect> connections;

    public ConnectionManager() {
        connections = new ArrayList<>();
    }

    void addNewConnection(Connect connect){
        connections.add(connect);
    }
}

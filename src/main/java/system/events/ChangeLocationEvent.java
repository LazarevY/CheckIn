package system.events;

import geometry.geojson.Point;
import system.CheckInSystem;

public class ChangeLocationEvent implements SystemEvent {
    private int userId;
    private Point location;

    public ChangeLocationEvent(int userId, Point location) {
        this.userId = userId;
        this.location = location;
    }

    public int getUserId() {
        return userId;
    }

    public Point getLocation() {
        return location;
    }

    @Override
    public void process(CheckInSystem system) {
        system.setUserLocation(userId, location);
    }
}

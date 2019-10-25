package system.events;

import geometry.Point;

public class ChangeLocationEvent implements SystemEvent {
    private long userId;
    private Point location;

    public ChangeLocationEvent(long userId, Point location) {
        this.userId = userId;
        this.location = location;
    }

    public long getUserId() {
        return userId;
    }

    public Point getLocation() {
        return location;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    private static final int  PRIORITY = 1;
}

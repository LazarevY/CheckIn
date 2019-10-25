package system.events;

public class CheckInEvent implements SystemEvent {
    private long userId;

    public CheckInEvent(long userId) {
        this.userId = userId;
    }

    public long getUserId(){
        return userId;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    private static final int  PRIORITY = 0;
}

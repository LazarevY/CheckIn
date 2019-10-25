package system.events;


public class ChangeUserNameEvent implements SystemEvent {
    private long userId;
    private String name;

    public ChangeUserNameEvent(long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    private static final int  PRIORITY = 2;
}

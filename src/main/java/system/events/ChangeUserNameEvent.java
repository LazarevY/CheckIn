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

}

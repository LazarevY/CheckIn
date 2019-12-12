package system.events;


import system.CheckInSystem;
import user.User;

public class ChangeUserNameEvent implements SystemEvent {
    private int userId;
    private String name;

    public ChangeUserNameEvent(int userId, String name) {
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
    public void process(CheckInSystem system) {
        User u = system.getUser(userId);
        u.getData().setName(name);
    }
}

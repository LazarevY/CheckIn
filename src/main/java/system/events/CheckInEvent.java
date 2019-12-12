package system.events;

import system.CheckInSystem;

public class CheckInEvent implements SystemEvent {
    private int userId;

    public CheckInEvent(int userId) {
        this.userId = userId;
    }

    public int getUserId(){
        return userId;
    }

    @Override
    public void process(CheckInSystem system) {
        system.checkIn(userId);
    }
}

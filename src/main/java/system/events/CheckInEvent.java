package system.events;

public class CheckInEvent implements SystemEvent {
    private int userId;

    public CheckInEvent(int userId) {
        this.userId = userId;
    }

    public int getUserId(){
        return userId;
    }
}

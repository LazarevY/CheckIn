package system.events;

import system.CheckInSystem;

public interface SystemEvent {
    void process(CheckInSystem system);
}

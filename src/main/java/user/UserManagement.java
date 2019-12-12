package user;

import util.Randomizer;

public class UserManagement extends Thread {

    private UserInterface userInterface;
    private Randomizer randomizer;
    private boolean stopped = false;
    private long deltaTime = 0;

    public UserManagement(UserInterface userInterface){
        this.userInterface = userInterface;
        randomizer = userInterface.getRandomizer();
    }

    @Override
    public void run() {
        while (!stopped){
            try {
                Thread.sleep(randomizer.getIntFromTo(200, 1000));
                userInterface.doStep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

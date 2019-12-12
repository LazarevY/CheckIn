package user;

import ai.RandomAI;
import geometry.geojson.Point;
import geometry.Rectangle;
import util.Randomizer;
import util.Vector2D;

public class UserInterface implements RandomAI {

    private static final int ALL = 10000000;
    private static final int PERCENT = ALL / 100;

    private User user;
    private Point userLocation;
    private Vector2D dir;
    private Randomizer randomizer;
    private Rectangle area;


    private float doStepPercent;
    private float changeDirectionPercent;
    private float checkInPercent;

    public UserInterface(User user, Randomizer randomizer, float doStepPercent, float changeDirectionPercent, float checkInPercent,
                         Rectangle area) {
        this.user = user;
        this.randomizer = randomizer;
        this.doStepPercent = doStepPercent;
        this.changeDirectionPercent = changeDirectionPercent;
        this.checkInPercent = checkInPercent;
        this.area = area;
        this.userLocation = new Point(randomizer.getDoubleFromTo(area.getMin().getX(), area.getMax().getX()),
                randomizer.getDoubleFromTo(area.getMin().getY(), area.getMax().getY()));
        user.setLocation(userLocation);
        this.dir = new Vector2D(randomizer.getDoubleFromZeroTo(1), randomizer.getDoubleFromZeroTo(1));
        dir.normalize();
    }

    @Override
    public void doStep() {
        if (percentTest(doStepPercent))
            step();
        if (percentTest(changeDirectionPercent))
            changeDirection();
        if (percentTest(checkInPercent))
            user.checkIn();
    }

    @Override
    public void doStepWithDeltaTime(long mills) {
        doStep();
    }

    private boolean percentTest(float percent) {
        if (percent <= 0)
            return false;
        if (percent >= 100)
            return true;
        return randomizer.getIntFromZeroTo(ALL) < percent * PERCENT;
    }

    private void step() {
        Point newLoc = Point.sum(userLocation, Vector2D.mul(dir, randomizer.getDoubleFromTo(1, 8)));
        while (!area.inside(newLoc)) {
            dir.invert();
            newLoc = Point.sum(userLocation, Vector2D.mul(dir, randomizer.getDoubleFromTo(1, 8)));
        }
        userLocation = newLoc;
        user.setLocation(userLocation);
    }

    private void changeDirection() {
        dir.setX(randomizer.getDoubleFromZeroTo(1) * randomizer.doubleSign(50));
        dir.setY(randomizer.getDoubleFromZeroTo(1) * randomizer.doubleSign(50));
        dir.normalize();
    }

    public User getUser() {
        return user;
    }

    public Randomizer getRandomizer() {
        return randomizer;
    }
}

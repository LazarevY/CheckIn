package geometry;

import geometry.geojson.Point;

public class Rectangle {
    private Point min;
    private Point max;

    public Rectangle(Point min, Point max) {
        this.min = min;
        this.max = max;
    }

    public boolean inside(Point point){
        return min.getX() <= point.getX() && min.getY() <= point.getY() &&
                max.getX() >= point.getX() && max.getY() >= point.getY();
    }

    public Point getMin() {
        return min;
    }

    public Point getMax() {
        return max;
    }
}

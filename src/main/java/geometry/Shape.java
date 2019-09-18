package geometry;

import geometry.Point;

public interface Shape {
    boolean contains(int x, int y);
    boolean contains(Point point);
    boolean contains(Point p, int radius);
}

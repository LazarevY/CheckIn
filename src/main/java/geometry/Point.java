package geometry;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import util.SimpleMath;

import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class Point {

    private int x;
    private int y;

    @JsonCreator
    public Point(@JsonProperty("x")int x,
                 @JsonProperty("y")int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p){
        x = p.x;
        y = p.y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static int distanceSqr(Point point1, Point point2){
        return SimpleMath.sqr(point1.x - point2.x) + SimpleMath.sqr(point1.y - point2.y);
    }

    public Point sub(Point other){
        return new Point(x - other.x, y - other.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

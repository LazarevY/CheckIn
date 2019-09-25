package map;

import geometry.Point;
import util.SimpleMath;

import java.util.ArrayList;
import java.util.Iterator;

public class LineObj extends GeoObj{
    private ArrayList<PointObj> objs;
    public LineObj(ObjectType type) {
        super(null, type, 12);
        objs = null;
    }

    public LineObj(String name, GeoObj parent, ObjectType type) {
        super(name,parent, type);
        objs = null;
    }
    public void addPoint(PointObj o){
        if (objs == null) {
            objs = new ArrayList<>();
        }
        objs.add(o);
    }

    public ArrayList<PointObj> getPoints(){
        return objs;
    }

    public void setObjs(ArrayList<PointObj> objs) {
        this.objs = objs;
    }

    @Override
    public boolean contains(Point location, int radius) {
        if (objs.size() == 1) {
            Point p = objs.get(0).getLocation();
            return Point.distanceSqr(p, location) < SimpleMath.sqr(radius);
        }

        Iterator<PointObj> it = objs.iterator();
        Point curr = it.next().getLocation();
        Point next;
        int r2 = radius * radius;
        while (it.hasNext()){

            if(Point.distanceSqr(curr, location) < r2)
                return true;

            next = it.next().getLocation();

            int x_a = location.getX() - next.getX();
            int y_a = location.getY() - next.getY();

            int x_b = curr.getX() - next.getX();
            int y_b = curr.getY() - next.getY();

            if(SimpleMath.sqr(x_a * x_b + y_a * y_b) > (x_b * x_b + y_b * y_b) * (x_a * x_a + y_a*y_a - r2))
                return true;

            curr = next;
        }

        return false;

    }

    public GeoObj intersectObj(Point location, int radius){

        if (objs.size() == 1) {
            Point p = objs.get(0).getLocation();
            if(Point.distanceSqr(p, location) < SimpleMath.sqr(radius))
                return objs.get(0);
        }

        Iterator<PointObj> it = objs.iterator();

        PointObj curr_obj = it.next();
        Point curr = curr_obj.getLocation();
        Point next;
        int r2 = radius * radius;
        boolean lineIntersects = false;
        while (it.hasNext()){

            if(Point.distanceSqr(curr, location) < r2)
                return curr_obj;

            next = it.next().getLocation();

            int x_a = location.getX() - next.getX();
            int y_a = location.getY() - next.getY();

            int x_b = curr.getX() - next.getX();
            int y_b = curr.getY() - next.getY();

            if (SimpleMath.sqr(x_a * x_b + y_a * y_b) > (x_b * x_b + y_b * y_b) * (x_a * x_a + y_a * y_a - r2)) {
                lineIntersects = true;
                break;
            }
            curr = next;
        }

        while (it.hasNext()) {
            curr_obj = it.next();
            if(Point.distanceSqr(curr_obj.getLocation(), location) < r2)
                return curr_obj;
        }

        return lineIntersects? this : null;
    }
}

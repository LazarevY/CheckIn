package map.objects;

import geometry.Point;
import util.SimpleMath;

import java.util.ArrayList;
import java.util.HashSet;
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

    public LineObj(String name, ObjectType type) {
        super(name,null, type);
        objs = null;
    }
    public void addPoint(PointObj o){
        if (objs == null) {
            objs = new ArrayList<>();
        }
        objs.add(o);
        o.setParent(this);
    }

    public ArrayList<PointObj> getPoints(){
        return objs;
    }

    //TODO : need fix this algorithm!!
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

            int minX = Math.min(location.getX(), next.getX());
            int minY = Math.min(location.getY(), next.getY());
            int maxX = Math.max(location.getX(), next.getX());
            int maxY = Math.max(location.getY(), next.getY());

            if ((minX < location.getX() && maxX > location.getX() || minY < location.getY() && maxY > location.getY())) {


                int x_a = location.getX() - curr.getX();
                int y_a = location.getY() - curr.getY();

                int x_b = next.getX() - curr.getX();
                int y_b = next.getY() - curr.getY();

                if (SimpleMath.sqr(x_a * x_b + y_a * y_b) > (x_b * x_b + y_b * y_b) * (x_a * x_a + y_a * y_a - r2))
                    return true;
            }
            curr = next;
        }

        return false;

    }

    @Override
    public ArrayList<Point> getBoundsPoints() {
        Point maxX = new Point(objs.get(0).getLocation());
        Point minX = new Point(objs.get(0).getLocation());
        Point maxY = new Point(objs.get(0).getLocation());
        Point minY = new Point(objs.get(0).getLocation());

        for (PointObj o : objs) {
            Point p = o.getLocation();
            if(p.getX() < minX.getX())
                minX = p;
            else if (p.getX() > maxX.getX())
                maxX = p;
            if(p.getY() < minY.getY())
                minY = p;
            else if (p.getY() > maxY.getX())
                maxX = p;
        }

        HashSet<Point> points = new HashSet<>();
        points.add(maxX);
        points.add(maxY);
        points.add(minX);
        points.add(minY);
        return new ArrayList<>(points);
    }


    @Override
    public ArrayList<PointObj> getActualChildren() {
        ArrayList<PointObj> result = new ArrayList<>();
        for (PointObj o : objs)
            if (!o.unnamed())
                result.add(o);

        return result;
    }
}

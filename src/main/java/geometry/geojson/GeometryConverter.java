package geometry.geojson;

import org.geojson.LngLatAlt;

import java.util.ArrayList;
import java.util.List;

public class GeometryConverter {

    public static Polygon convert(org.geojson.Polygon polygon) {
        return new Polygon(convert(polygon.getExteriorRing()));
    }

    public static  LineString convert(org.geojson.LineString lineString){
        return new LineString(convert(lineString.getCoordinates()));
    }

    public static List<Point> convert(List<LngLatAlt> coords) {

        ArrayList<Point> points = new ArrayList<>();
        for (LngLatAlt crd : coords)
            points.add(convert(crd));
        return points;
    }

    public static Point convert(LngLatAlt coordinate) {
        return new Point(coordinate.getLongitude(), coordinate.getLatitude());
    }

    public static Point convert(org.geojson.Point point){
        return convert(point.getCoordinates());
    }

}

package system;

import geometry.Circle;
import geometry.Point;
import geometry.Rectangle;
import map.*;
import user.Sex;
import user.UserData;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Connect connection = new Connect();

        LineObj lineObj1 = new LineObj("Line1", null, ObjectType.MONUMENT);
        PointObj pointObj1 = new PointObj("Monument1", new Point(20,20), lineObj1,ObjectType.MONUMENT);
        PointObj pointObj2 = new PointObj(new Point(40,30), lineObj1, ObjectType.MONUMENT);
        PointObj pointObj3 = new PointObj("Monument2", new Point(48,48), lineObj1, ObjectType.MONUMENT);
        PointObj pointObj4 = new PointObj("Monument3", new Point(70,60), null, ObjectType.MONUMENT);

        UsualObj usualObj1 = new UsualObj("Tony", new Rectangle(50, 50, 10, 10), null,
                ObjectType.MARKET);

        lineObj1.addPoint(pointObj1);
        lineObj1.addPoint(pointObj2);
        lineObj1.addPoint(pointObj3);

        CompositeObj compositeObj1;

        ArrayList<GeoObj> objs = new ArrayList<>();
        objs.add(lineObj1);
        compositeObj1 = new CompositeObj("Home1", objs, new Circle(new Point(30,30), 70), null,
                ObjectType.SC);
        lineObj1.setParent(compositeObj1);

        Map map = new Map(400, 300, new ArrayList<>());

        CheckInSystem checkInSystem = new CheckInSystem();
        checkInSystem.setMap(map);

        checkInSystem.setConnect(connection);
        checkInSystem.registerGeoObj(compositeObj1);
        checkInSystem.registerGeoObj(pointObj4);
        checkInSystem.registerGeoObj(usualObj1);


         long id1 = checkInSystem.registerUser(new UserData("Andrey", 19, Sex.MALE));
         checkInSystem.setUserLocation(id1, new Point(48,48));
         checkInSystem.addUserPriorityInOrder(id1, ObjectType.SC, ObjectType.MARKET);

         ArrayList<PointObj> objs1 = new ArrayList<>(5);
         objs1.add(pointObj1);
         objs1.add(pointObj2);
         objs1.add(pointObj3);
         lineObj1.setObjs(objs1);

         checkInSystem.checkIn(id1);
        System.out.println();

    }
}

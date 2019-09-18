package system;

import geometry.Circle;
import geometry.Point;
import map.*;
import user.Sex;
import user.User;
import user.UserData;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Connect connection = new Connect();

        LineObj lineObj1 = new LineObj("Line1", null);
        PointObj pointObj1 = new PointObj("Monument1", new Point(20,20), lineObj1);
        PointObj pointObj2 = new PointObj(new Point(40,30), lineObj1);
        PointObj pointObj3 = new PointObj("Monument2", new Point(50,60), lineObj1);
        PointObj pointObj4 = new PointObj("Monument3", new Point(70,60), null);

        CompositeObj compositeObj1;

        ArrayList<GeoObj> objs = new ArrayList<>();
        objs.add(lineObj1);
        compositeObj1 = new CompositeObj("Home1", objs, new Circle(new Point(30,30), 70));
        lineObj1.setParent(compositeObj1);

        ArrayList<GeoObj> objs2 = new ArrayList<>();
        objs2.add(compositeObj1);
        objs2.add(pointObj4);
        Map map = new Map(400, 300, objs2);

        CheckInSystem checkInSystem = new CheckInSystem();
        checkInSystem.setMap(map);
        checkInSystem.setConnect(connection);


         long id1 = checkInSystem.registerUser(new UserData("Andrey", 19, Sex.MALE));
         checkInSystem.setUserLocation(id1, new Point(80,60));

         ArrayList<PointObj> objs1 = new ArrayList<>(5);
         objs1.add(pointObj1);
         objs1.add(pointObj2);
         objs1.add(pointObj3);
         lineObj1.setObjs(objs1);

         checkInSystem.checkIn(id1);
        System.out.println();


    }
}

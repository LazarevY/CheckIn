package system;

import fileinteracting.reading.GeoObjectsLoader;
import geometry.Circle;
import geometry.Point;
import geometry.Rectangle;
import map.Map;
import map.objects.*;
import user.Sex;
import user.User;
import user.UserData;

import java.util.List;

public class CHSysRun {
    private Thread checkInThread;
    private CheckInSystem checkInSystem;
    private long[] users;

    public CHSysRun() {
        users = new long[10];
        initSystem();
        checkInThread = new Thread(checkInSystem);
    }

    public void checkInStart(){
        checkInSystem.startSystem();
        checkInThread.start();
        User user1 = checkInSystem.getUser(users[0]);
        User user2 = checkInSystem.getUser(users[1]);
        User user3 = checkInSystem.getUser(users[2]);
        User user4 = checkInSystem.getUser(users[3]);
        user1.checkIn();
        user2.checkIn();
        user2.setLocation(new Point(200,100));
        user2.checkIn();
        user3.checkIn();
        user4.checkIn();
    }

    private void initSystem(){
        checkInSystem = new CheckInSystem();

//        //---OBJECTS----\\
//
//        LineObj wayHonor = new LineObj("Honor way", ObjectType.ALLEY);
//        wayHonor.addChild(new PointObj("F monument", new Point(10,15), ObjectType.MONUMENT));
//        wayHonor.addChild(new PointObj("CSF monument", new Point(20,42),ObjectType.MONUMENT));
//        wayHonor.addChild(new PointObj("24 respects", new Point(5, 55), ObjectType.MONUMENT));
//        wayHonor.addChild(new PointObj("The end of way", new Point(30,70), ObjectType.MONUMENT));
//
//        PointObj mon_1 = new PointObj("Monument_1", new Point(40,81), null, ObjectType.MONUMENT);
//        PointObj mon_2 = new PointObj("Infinity fire", new Point(109, 95), null, ObjectType.MONUMENT);
//
//        CompositeObj park_1 = new CompositeObj("Park_1", new Circle(new Point(120, 67), 20),
//                null, ObjectType.PARK);
//        LineObj alley_1 = new LineObj("Summer Alley", ObjectType.ALLEY);
//        alley_1.addChild(new PointObj("Statue_1", new Point(118, 20),ObjectType.MONUMENT));
//        alley_1.addChild(new PointObj("Statue_2", new Point(118, 35),ObjectType.MONUMENT));
//        alley_1.addChild(new PointObj("Statue_3", new Point(118, 53),ObjectType.MONUMENT));
//
//        park_1.addChild(alley_1);
//        park_1.addChild(new UsualObj("Moon", new Rectangle(102, 55, 10,15),
//                null, ObjectType.CAFE));
//
//        UsualObj uni_1 = new UsualObj("VSU", new Rectangle(200, 120, 30, 40), null, ObjectType.UNI);
//
//        UsualObj private_1 = new UsualObj("Home_1", new Rectangle(200, 55, 10, 20), null, ObjectType.PRIVATE);

        checkInSystem.setConnect(new Connect());
        checkInSystem.setMap(new Map(100,100, 10, 20));

        GeoObjectsLoader loader = new GeoObjectsLoader();
        List<GeoObj> objs = loader.loadGeoObjects("src/main/resources/objects.json");
        loader.setTopology(objs,"src/main/resources/objectsTopology.tpg");
        checkInSystem.registerAll(objs);

//        checkInSystem.registerGeoObjOnDeepMode(wayHonor);
//        checkInSystem.registerGeoObjOnDeepMode(park_1);
//        checkInSystem.registerGeoObjOnDeepMode(mon_1);
//        checkInSystem.registerGeoObjOnDeepMode(mon_2);
//        checkInSystem.registerGeoObjOnDeepMode(uni_1);
//        checkInSystem.registerGeoObjOnDeepMode(private_1);

        users[0] = checkInSystem.registerUser(new UserData("Andrey", 19, Sex.MALE));
        checkInSystem.setUserLocation(users[0], new Point(5,11));
        checkInSystem.addUserPriorityInOrder(users[0], ObjectType.MONUMENT, ObjectType.MARKET);

        users[1] = checkInSystem.registerUser(new UserData("Alex", 24, Sex.MALE));
        checkInSystem.setUserLocation(users[1], new Point(44,84));
        checkInSystem.addUserPriorityInOrder(users[1], ObjectType.UNI, ObjectType.PRIVATE, ObjectType.CAFE);

        users[2] = checkInSystem.registerUser(new UserData("Anna", 21, Sex.FEMALE));
        checkInSystem.setUserLocation(users[2], new Point(103,54));
        checkInSystem.addUserPriorityInOrder(users[2], ObjectType.ALLEY, ObjectType.PARK, ObjectType.CAFE);

        users[3] = checkInSystem.registerUser(new UserData("Boris", 17, Sex.MALE));
        checkInSystem.setUserLocation(users[3], new Point(10,50));
        checkInSystem.addUserPriorityInOrder(users[3], ObjectType.PARK, ObjectType.UNI, ObjectType.PRIVATE);

        users[4] = checkInSystem.registerUser(new UserData("Tomas", 27, Sex.MALE));
        checkInSystem.setUserLocation(users[4], new Point(210,76));
        checkInSystem.addUserPriorityInOrder(users[4], ObjectType.PRIVATE, ObjectType.UNI, ObjectType.SCHOOL);

        users[5] = checkInSystem.registerUser(new UserData("Tomas", 27, Sex.MALE));
        checkInSystem.setUserLocation(users[5], new Point(213,76));
        checkInSystem.addUserPriorityInOrder(users[5], ObjectType.UNI, ObjectType.PRIVATE, ObjectType.SCHOOL);

    }
}

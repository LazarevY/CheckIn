package system;

import fileinteracting.reading.GeoObjectsLoader;
import geometry.geojson.Point;
import map.Map;
import map.objects.*;
import user.Sex;
import user.User;
import user.UserData;

import java.util.List;

public class CHSysRun {
    private Thread checkInThread;
    private CheckInSystem checkInSystem;
    private int[] users;

    public CHSysRun() {
        users = new int[10];
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

        checkInSystem.setConnect(new Connect());
        checkInSystem.setMap(new Map(100,100, 10, 20, null));

        GeoObjectsLoader loader = new GeoObjectsLoader();
        List<GeoObj> objs = loader.loadGeoObjects("src/main/resources/objects.json");
        //loader.setTopology(objs,"src/main/resources/objectsTopology.tpg");
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

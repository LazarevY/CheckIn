package system;

import com.fasterxml.jackson.databind.ObjectMapper;
import fileinteracting.reading.GeoObjectsLoader;
import fileinteracting.reading.GeometryLoader;
import fileinteracting.topology.GeoObjectsTopology;
import geometry.geojson.Geometry;
import gui.main.MainFrame;
import map.objects.GeoObj;
import map.objects.LineObj;
import map.objects.ObjectType;
import user.Sex;
import user.User;
import user.UserData;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
//        System.out.println(UsualObj.class);
//        CHSysRun run = new CHSysRun();
//        run.checkInStart();
       // MainFrame.appStart(args);

       // List<Geometry> list = GeometryLoader.loadGeometryFromFeatureCollection("src/main/resources/objects/objs.geojson");
       // System.out.println();
        ObjectMapper mapper = new ObjectMapper();
        User u = new User(new UserData("ASASAZ LALKA", 20, Sex.MALE), null, 1);
        u.addPriority(0, ObjectType.UNI);
        u.addPriority(1, ObjectType.CAFE);
        u.addPriority(2, ObjectType.PARK);

        User u1 = new User(new UserData("Vasily", 20, Sex.MALE), null, 1);
        u1.addPriority(0, ObjectType.PRIVATE);
        u1.addPriority(1, ObjectType.ALLEY);
        u1.addPriority(2, ObjectType.MONUMENT);

        User u2 = new User(new UserData("Alex", 18, Sex.FEMALE), null, 1);
        u2.addPriority(0, ObjectType.MONUMENT);
        u2.addPriority(1, ObjectType.SCHOOL);
        u2.addPriority(2, ObjectType.PARK);

        List<User> users = Arrays.asList(u,u1,u2);
        mapper.writeValue(new File("src/main/resources/users/users.json"), users);
    }
}

package gui.main;

import fileinteracting.reading.GeoObjectsLoader;
import fileinteracting.reading.GeometryLoader;
import fileinteracting.topology.GeoObjectsTopology;
import geometry.geojson.Geometry;
import geometry.geojson.Point;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import map.Map;
import map.objects.GeoObj;
import system.CheckInSystem;
import system.Connect;
import user.Sex;
import user.User;
import user.UserData;

import java.io.IOException;
import java.util.List;

public class MainFrame extends Application {

    private static MainFrame currentFrame;
    private Map map;
    private CheckInSystem system;
    private Connect connect;
    private User user;

    public static MainFrame getCurrentFrame() {
        return currentFrame;
    }

    public MainFrame() {
        currentFrame = this;
        initMap();
        system = new CheckInSystem();
        connect = new Connect();
        system.setMap(map);
        system.setConnect(connect);
        user = system.getUser(system.registerUser(new UserData("ASASAZ LALKA", 20, Sex.MALE)));
        system.setUserLocation(user.getUserID(), new Point(11,11));
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 640, 465));
        primaryStage.show();
    }

    public static void appStart(String[] args) {
        launch(args);
    }

    private void initMap() {
        java.util.Map<Integer, Geometry> geometryMap = null;
        try {
            geometryMap = GeometryLoader.loadGeometryFromFeatureCollection("src/main/resources/objects/map/geom.geojson");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<GeoObjectsTopology> topologies = null;
        try {
            topologies = GeoObjectsLoader.loadTopology("src/main/resources/objects/map/objectsTopology.tpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        java.util.Map<Integer, GeoObj> objMap = GeoObjectsLoader.loadGeoObjectsInMap("src/main/resources/objects/map/objectsList.json");

        GeoObjectsLoader.applyGeometryByGeoObjects(objMap, geometryMap);
        GeoObjectsLoader.applyTopology(objMap, topologies);

        map = new map.Map(520, 520, 20, 20, objMap);
    }

    public void checkIn(){
        System.out.println(system.checkIn(user.getUserID()).fullName());
    }

    public Map getMap() {
        return map;
    }

    public CheckInSystem getSystem() {
        return system;
    }

    public User getUser() {
        return user;
    }
}

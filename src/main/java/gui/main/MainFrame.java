package gui.main;

import fileinteracting.reading.GeoObjectsLoader;
import fileinteracting.reading.GeometryLoader;
import fileinteracting.reading.UserLoader;
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
import system.AppLogic;
import system.CheckInSystem;
import user.User;
import user.UserInterface;
import user.UserManagement;
import util.Randomizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainFrame extends Application {

    private static MainFrame currentFrame;
    private AppLogic appLogic;
    private Randomizer randomizer = new Randomizer();

    public static MainFrame getCurrentFrame() {
        return currentFrame;
    }

    public MainFrame() {
        currentFrame = this;
        loadResources();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 640, 465));
        primaryStage.show();
    }

    public static void appStart(String[] args) {
        launch(args);
    }

    private Map initMap() {
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

        return new map.Map(500, 500, 10, 10, objMap);
    }

    private void loadResources(){
        CheckInSystem system = new CheckInSystem();
        Map map = initMap();
        List<User> users = UserLoader.loadUsersData("src/main/resources/users/users.json");
        List<UserManagement> userManagements = new ArrayList<>();
        this.appLogic = new AppLogic(system, map, users, userManagements);
             for (User u: users){
            system.registerUser(u);
            float stepFactor = randomizer.getFloatFromTo(60, 90);
            float directionFactor = randomizer.getFloatFromTo(1, 5);
            float checkInFactor = randomizer.getFloatFromTo(1, 10);
            userManagements.add(new UserManagement(new UserInterface(u, new Randomizer(),
                    stepFactor, directionFactor, checkInFactor, map.getActionArea())));
        }

    }

    public String checkIn(){
        return appLogic.getCheckInSystem().checkIn(appLogic.getCurrentUser().getUserID()).fullName();
    }

    public AppLogic getAppLogic() {
        return appLogic;
    }

    public Map getMap() {
        return appLogic.getMap();
    }

    public CheckInSystem getSystem() {
        return appLogic.getCheckInSystem();
    }

}

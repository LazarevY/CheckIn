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

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class MainFrame extends Application {

    private static MainFrame currentFrame;
    private AppLogic appLogic;

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

        return new map.Map(520, 520, 10, 10, objMap);
    }

    private void loadResources(){
        CheckInSystem system = new CheckInSystem();
        Map map = initMap();
        List<User> users = UserLoader.loadUsersData("src/main/resources/users/users.json");
        this.appLogic = new AppLogic(system, map, users);
        Random r = new Random();

        for (User u: users){
            system.registerUser(u);
            system.setUserLocation(u, new Point(r.nextInt() % 500, r.nextInt() % 500));
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

package gui.main;

;
import java.net.URL;
import java.util.*;

import geometry.geojson.Circle;
import geometry.geojson.Point;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import map.objects.GeoObj;
import system.AppLogic;
import system.CheckInSystem;
import user.User;

public class MainController {
    private MainFrame frame;
    private AppLogic logic;

    public Canvas mapCanvas;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public Label objectLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userAgeLabel;

    @FXML
    void initialize() {
        frame = MainFrame.getCurrentFrame();
        logic = frame.getAppLogic();
        setUserData(logic.getCurrentUser());
        repaint();
    }

    private void repaint() {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTSLATEGRAY);
        gc.setGlobalAlpha(1);
        mapCanvas.getGraphicsContext2D().clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
        gc.fillRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
        drawMap();
        drawUser();
    }

    private void drawMap() {
        Map<Integer, GeoObj> list = MainFrame.getCurrentFrame().getMap().getObjectBase();
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        for (GeoObj o : list.values()) {
            if (o.getId() == map.Map.nullObjId)
                continue;
            o.getGeometry().draw(gc);
        }
    }

    private void drawUser() {

        Circle c = new Circle(frame.getMap().getUserLocation(logic.getCurrentUser().getUserID()), CheckInSystem.radiusError);
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.setGlobalAlpha(0.5);
        gc.setFill(Color.RED);
        c.draw(gc);

    }

    public void closeApp(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void checkInAction() {
        objectLabel.setText(frame.checkIn());
    }

    public void mapMouseClickAction(MouseEvent mouseEvent) {
        int id = logic.getCurrentUser().getUserID();
        frame.getSystem().setUserLocation(id, new Point(mouseEvent.getX(), mouseEvent.getY()));
        System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
        repaint();
    }

    private void setUserData(User user){
        userNameLabel.setText(user.getData().getName());
        userAgeLabel.setText(String.valueOf(user.getData().getAge()));
        repaint();
    }

    public void setPreviousUser() {
        setUserData(logic.previousUser());
       // checkInAction();
    }

    public void setNextUser() {
        setUserData(logic.nextUser());
        //checkInAction();
    }
}

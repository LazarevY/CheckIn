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
import system.CheckInSystem;
import user.User;

public class MainController {
    private MainFrame frame;

    public Canvas mapCanvas;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userAgeLabel;

    @FXML
    void initialize() {
        frame = MainFrame.getCurrentFrame();
        User u = frame.getUser();
        repaint();
        userNameLabel.setText(u.getData().getName());
        userAgeLabel.setText(String.valueOf(u.getData().getAge()));
        frame.checkIn();
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

        Circle c = new Circle(frame.getMap().getUserLocation(frame.getUser().getUserID()), CheckInSystem.radiusError);
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.setGlobalAlpha(0.5);
        gc.setFill(Color.RED);
        c.draw(gc);

    }

    public void closeApp(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void checkInAction(ActionEvent actionEvent) {
        frame.checkIn();
    }

    public void mapMouseClickAction(MouseEvent mouseEvent) {
        int id = frame.getUser().getUserID();
        frame.getSystem().setUserLocation(id, new Point(mouseEvent.getX(), mouseEvent.getY()));
        System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
        repaint();
    }
}

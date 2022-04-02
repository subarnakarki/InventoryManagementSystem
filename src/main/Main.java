package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setTitle("Main Form");
        stage.setScene(new Scene(root, 915, 380));
        stage.show();
    }

    public static void main(String[] args) {
        InHousePart brakes = new InHousePart(DataProvider.generateId(DataProvider.getAllParts()), "brakes", 12.99, 10, 1, 10, 16541);
        DataProvider.addPart(brakes);
        InHousePart wheel = new InHousePart(DataProvider.generateId(DataProvider.getAllParts()), "wheel", 12.99, 16, 1, 10, 651);
        DataProvider.addPart(wheel);
        InHousePart seat = new InHousePart(DataProvider.generateId(DataProvider.getAllParts()), "seat", 12.99, 10, 1, 10, 6511);
        DataProvider.addPart(seat);

        launch(args);
    }
}


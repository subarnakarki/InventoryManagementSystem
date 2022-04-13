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
        Inventory.addPart(new InHouse(Inventory.generatePartId(), "brakes", 12.99, 10, 1, 10, 16541));
        Inventory.addPart(new InHouse(Inventory.generatePartId(), "wheel", 12.99, 16, 1, 10, 651));
        Inventory.addPart(new InHouse(Inventory.generatePartId(), "seat", 12.99, 10, 1, 10, 6511));
        Inventory.addPart(new Outsourced(Inventory.generatePartId(), "door", 45.99, 10, 1, 10, "company one"));

        Inventory.addProduct(new Product(Inventory.generateProductId(), "car", 1, 1, 1,1 ,null));
        Inventory.addProduct(new Product(Inventory.generateProductId(), "bike", 1, 1, 1,1 ,null));
        Inventory.addProduct(new Product(Inventory.generateProductId(), "truck", 1, 1, 1,1 ,null));
        launch(args);
    }
}


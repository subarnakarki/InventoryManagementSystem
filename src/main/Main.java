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
        InHouse brakes = new InHouse(Inventory.generatePartId(), "brakes", 12.99, 10, 1, 10, 16541);
        Inventory.PartData.addPart(brakes);
        InHouse wheel = new InHouse(Inventory.generatePartId(), "wheel", 12.99, 16, 1, 10, 651);
        Inventory.PartData.addPart(wheel);
        InHouse seat = new InHouse(Inventory.generatePartId(), "seat", 12.99, 10, 1, 10, 6511);
        Inventory.PartData.addPart(seat);
        Outsourced door = new Outsourced(Inventory.generatePartId(), "door", 45.99, 10, 1, 10, "company one");
        Inventory.PartData.addPart(door);

        Inventory.ProductData.addProduct(new Product(1, "car", 1, 1, 1,1 ,null));
        Inventory.ProductData.addProduct(new Product(2, "bike", 1, 1, 1,1 ,null));
        Inventory.ProductData.addProduct(new Product(3, "truck", 1, 1, 1,1 ,null));
        launch(args);
    }
}

